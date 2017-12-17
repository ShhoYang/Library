package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.luck.picture.lib.tools.PictureFileUtils;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Config;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.bean.AuthInfo;
import com.yl.yhbmfw.bean.RegionNode;
import com.yl.yhbmfw.event.EventRefresh;
import com.yl.yhbmfw.mvp.contract.AuthenticateContract;
import com.yl.yhbmfw.api.Api;
import com.yl.library.rx.RxBus;
import com.yl.library.rx.RxSubscriber;
import com.yl.library.utils.RegexUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.luck.picture.lib.PictureSelector.obtainMultipleResult;


/**
 * @author Yang Shihao
 */
public class AuthenticatePresenter extends AuthenticateContract.Presenter {

    private String mHeadPath;
    private String mIdCardFrontPath;
    private String mIdCardAfterPath;
    private RegionNode mSelectedCommunity;
    private List<RegionNode> mStreetList = new ArrayList<>();
    private List<List<RegionNode>> mCommunityList = new ArrayList<>();
    private String[] mSex = {"男", "女"};

    public AuthenticatePresenter(AuthenticateContract.View view) {
        super(view);
    }

    @Override
    public void getAuthInfo() {
        mRxManager.add(new RxSubscriber<AuthInfo>(Api.authenticateResult()) {
            @Override
            protected void _onNext(AuthInfo authInfo) {
                arrange(authInfo);
            }

            @Override
            protected void _onError(String code) {
                super._onError(code);
                mView.setEnableEdit(true);
            }
        });
    }

    private void arrange(AuthInfo info) {
        if (!info.getStatus().equals(App.getInstance().getConfig().getAuthInfo().getStatus())) {
            App.getInstance().getConfig().setAuthInfo(info);
            RxBus.getInstance().send(new EventRefresh(EventRefresh.REFRESH_AUTH_INFO));
        }
        if (info == null) {
            mView.setEnableEdit(true);
        } else if (info.authPass() || info.authing()) {
            mView.setName(info.getReal_name());
            mView.setStatus(info.getStatus());
            String idCard = info.getCard();
            if (RegexUtils.isIdCardPwd(idCard)) {
                mView.setIdCard(idCard.substring(0, 6) + "********" + idCard.substring(14, idCard.length()));
            }
            mView.setHeadImage(info.getMini_pics());
            getRegionList(info.getRegion_id());
            mView.showUserInfo(View.GONE);
            mView.setEnableEdit(false);
        } else if (info.authFail()) {
            mView.setName(info.getReal_name());
            mView.setStatus(info.getStatus());
            mView.setIdCard(info.getCard());
            mView.setFailReason("认证失败:" + info.getFail_reason());
            getRegionList(info.getRegion_id());
            mView.showUserInfo(View.VISIBLE);
            mView.setEnableEdit(true);
        } else {
            mView.showUserInfo(View.VISIBLE);
            mView.setEnableEdit(true);
        }
    }

    @Override
    public List<String> getSex() {
        return Arrays.asList(mSex);
    }

    /**
     * 通过用户绑定的社区id获取到地名
     *
     * @param regionId
     */
    private void getRegionList(final String regionId) {
        if (mStreetList.size() == 0) {
            mRxManager.add(new RxSubscriber<List<RegionNode>>(Api.getRegion(Constant.YU_HANG_CODE)) {
                @Override
                protected void _onNext(List<RegionNode> regionNodes) {
                    arrange(regionNodes);
                    if (mStreetList.size() != 0) {
                        getRegion(regionId);
                    }
                }
            });
        } else {
            getRegion(regionId);
        }
    }

    private void arrange(List<RegionNode> regionNodes) {
        if (regionNodes.size() == 0) {
            mView.toast("获取社区列表为空");
            return;
        }
        mStreetList.clear();
        mCommunityList.clear();
        //余杭区
        RegionNode district = regionNodes.get(0);
        //街道集合
        mStreetList.addAll(district.getChildren());
        for (RegionNode street : mStreetList) {
            mCommunityList.add(street.getChildren());
        }
    }

    private void getRegion(String regionId) {
        try {
            for (RegionNode s : mStreetList) {
                for (RegionNode c : s.getChildren()) {
                    if (regionId.equals(c.getCode())) {
                        mSelectedCommunity = c;
                        mView.setCommunity(s.getText() + c.getText());
                        return;
                    }
                }
            }
        } catch (RuntimeException e) {

        }
    }

    /**
     * 获取社区列表，显示到选择器
     */
    @Override
    public void getRegionList() {
        if (mStreetList.size() == 0) {
            mRxManager.add(new RxSubscriber<List<RegionNode>>(Api.getRegion(Constant.YU_HANG_CODE)) {
                @Override
                protected void _onNext(List<RegionNode> regionNodes) {
                    arrange(regionNodes);
                    if (mStreetList.size() != 0) {
                        mView.showCommunityDialog(mStreetList, mCommunityList);
                    }
                }
            });
        } else {
            mView.showCommunityDialog(mStreetList, mCommunityList);
        }
    }

    /**
     * 选择器选择结果
     */
    @Override
    public String getRegionName(int index1, int index2) {
        mSelectedCommunity = mCommunityList.get(index1).get(index2);
        return mStreetList.get(index1).getText() + mSelectedCommunity.getText();
    }

    @Override
    public void getIdCardFrontImage(Intent data) {
        mIdCardFrontPath = obtainMultipleResult(data).get(0).getCompressPath();
        mView.setIdCardFrontImage(mIdCardFrontPath);
    }

    @Override
    public void getIdCardAfterImage(Intent data) {
        mIdCardAfterPath = obtainMultipleResult(data).get(0).getCompressPath();
        mView.setIdCardAfterImage(mIdCardAfterPath);
    }

    @Override
    public void getHeadImage(Intent data) {
        mHeadPath = obtainMultipleResult(data).get(0).getCompressPath();
        mView.setHeadImage(mHeadPath);
    }

    @Override
    public void submit() {
        String name = mView.getName();
        if (TextUtils.isEmpty(name)) {
            mView.toast("姓名不能为空");
            return;
        }

        String idCard = mView.getIdCard();
        if (TextUtils.isEmpty(idCard)) {
            mView.toast("身份证不能为空");
            return;
        }

        if (!RegexUtils.isIdCardPwd(idCard)) {
            mView.toast("请输入正确的身份证号");
            return;
        }

        if (mSelectedCommunity == null) {
            mView.toast("所在社区不能为空");
            return;
        }

        if (TextUtils.isEmpty(mHeadPath)) {
            mView.toast("请上传一寸照片");
            return;
        }

        if (TextUtils.isEmpty(mIdCardFrontPath)) {
            mView.toast("请上传身份证正面照片");
            return;
        }

        if (TextUtils.isEmpty(mIdCardAfterPath)) {
            mView.toast("请上传身份证反面照片");
            return;
        }

        mView.showDialog("正在提交...");
        Config config = App.getInstance().getConfig();
        mRxManager.add(new RxSubscriber<String>(Api.authenticate(config.getPhone(), name, idCard,
                mSelectedCommunity.getCode(), mHeadPath, mIdCardFrontPath, mIdCardAfterPath), mView) {

            @Override
            protected void _onNext(String s) {
                mView.toast("提交成功");
                mView.setEnableEdit(false);
                mView.setFailReason("");
                mView.setStatus("未认证");
                PictureFileUtils.deleteCacheDirFile(mContext);
                RxBus.getInstance().send(new EventRefresh(EventRefresh.REFRESH_AUTH_INFO_NET));
            }
        });
    }
}
