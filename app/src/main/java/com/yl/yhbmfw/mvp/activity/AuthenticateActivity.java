package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yl.library.base.activity.BaseActivity;
import com.yl.library.rx.RxBus;
import com.yl.library.widget.EditTextGroup;
import com.yl.library.widget.TextViewGroup;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.RegionNode;
import com.yl.yhbmfw.event.EventRefresh;
import com.yl.yhbmfw.mvp.contract.AuthenticateContract;
import com.yl.yhbmfw.mvp.presenter.AuthenticatePresenter;
import com.yl.library.utils.ImageManager;
import com.yl.library.utils.KeyBoardUtils;
import com.yl.library.utils.PictureSelectorUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 实名认证
 */
public class AuthenticateActivity extends BaseActivity<AuthenticateContract.Presenter>
        implements AuthenticateContract.View {

    private static final int REQUEST_CODE_ID_CARD_FRONT = 101;
    private static final int REQUEST_CODE_ID_CARD_AFTER = 102;
    private static final int REQUEST_CODE_HEAD = 103;

    //姓名
    @BindView(R.id.et_name)
    EditText mEtName;
    //性别
    @BindView(R.id.tv_sex)
    TextViewGroup mTvSex;
    //身份证号
    @BindView(R.id.et_id_card)
    EditTextGroup mEtIdCard;
    //地址
    @BindView(R.id.tv_addr)
    TextViewGroup mTvAddr;
    //认证状态
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    //认证失败原因
    @BindView(R.id.tv_fail_reason)
    TextView mTvFailReason;
    //一寸照片
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    //身份证正面照片
    @BindView(R.id.iv_id_card_front)
    ImageView mIvIdCardFront;
    //身份证背面照片
    @BindView(R.id.iv_id_card_after)
    ImageView mIvIdCardAfter;
    //选择地区
    private OptionsPickerView mAddrOptionsPickerView;
    //选择性别
    private OptionsPickerView mSexOptionsPickerView;

    @Override
    protected void onDestroy() {
        RxBus.getInstance().send(new EventRefresh(EventRefresh.REFRESH_AUTH_INFO));
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authenticate;
    }

    @Override
    protected void initMVP() {
        mPresenter = new AuthenticatePresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("实名认证");
        setTextRight("提交");
    }

    @Override
    protected void initData() {
        mPresenter.getAuthInfo();
    }

    @Override
    public void onTextViewRightClicked() {
        mPresenter.submit();
    }

    @OnClick(R.id.iv_id_card_front)
    public void onIvIdCardFrontClicked() {
        PictureSelectorUtils.getImageSingleCropOption(this, 3, 2, REQUEST_CODE_ID_CARD_FRONT);
    }

    @OnClick(R.id.iv_id_card_after)
    public void onIvIdCardAfterClicked() {
        PictureSelectorUtils.getImageSingleCropOption(this, 3, 2, REQUEST_CODE_ID_CARD_AFTER);
    }

    @OnClick(R.id.iv_head)
    public void onIvHeadClicked() {
        PictureSelectorUtils.getImageSingleCropOption(this, 5, 7, REQUEST_CODE_HEAD);
    }

    @Override
    public String getName() {
        return mEtName.getText().toString();
    }

    @Override
    public String getSex() {
        return mTvSex.getTextRight();
    }

    @Override
    public String getIdCard() {
        return mEtIdCard.getTextRight();
    }

    @Override
    public void setName(String s) {
        mEtName.setText(s);
    }

    @Override
    public void setIdCard(String s) {
        mEtIdCard.setTextRight(s);
    }

    @Override
    public void setStatus(String s) {
        mTvStatus.setText(s);
    }

    @Override
    public void setCommunity(String s) {
        mTvAddr.setTextRight(s);
    }

    @Override
    public void setFailReason(String s) {
        mTvFailReason.setText(s);
    }

    @OnClick(R.id.tv_sex)
    public void onTvSexClicked() {
        if (mSexOptionsPickerView == null) {
            mSexOptionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    mTvSex.setTextRight(mPresenter.getSex().get(options1));
                }
            }).build();
        }
        mSexOptionsPickerView.setPicker(mPresenter.getSex());
        if (!mSexOptionsPickerView.isShowing()) {
            mSexOptionsPickerView.show();
        }
    }

    @OnClick(R.id.tv_addr)
    public void onTvAddrClicked() {
        KeyBoardUtils.closeSoftInput(this);
        mPresenter.getRegionList();
    }

    @Override
    public void setIdCardFrontImage(String path) {
        ImageManager.getInstance().loadImage(this, path, mIvIdCardFront);
    }

    @Override
    public void setIdCardAfterImage(String path) {
        ImageManager.getInstance().loadImage(this, path, mIvIdCardAfter);
    }

    @Override
    public void setHeadImage(String path) {
        ImageManager.getInstance().loadImage(this, path, mIvHead);
    }

    @Override
    public void showCommunityDialog(List<RegionNode> street, List<List<RegionNode>> community) {
        if (mAddrOptionsPickerView == null) {
            mAddrOptionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    mTvAddr.setTextRight(mPresenter.getRegionName(options1, options2));
                }
            }).build();
        }
        mAddrOptionsPickerView.setPicker(street, community);
        if (!mAddrOptionsPickerView.isShowing()) {
            mAddrOptionsPickerView.show();
        }
    }

    /**
     * 是否可编辑
     */
    @Override
    public void setEnableEdit(boolean enable) {
        mEtName.setEnabled(enable);
        mEtIdCard.setEnableEidt(enable);
        mTvSex.setFocusable(enable);
        mTvSex.setClickable(enable);
        mIvHead.setEnabled(enable);
        mTvAddr.setEnabled(enable);
        mIvIdCardFront.setEnabled(enable);
        mIvIdCardAfter.setEnabled(enable);

        if (enable) {
            setRightTextVisibility(View.VISIBLE);
        } else {
            setRightTextVisibility(View.GONE);
        }
    }

    /**
     * 隐藏用户的信息
     */
    @Override
    public void showUserInfo(int visibility) {
        mIvIdCardFront.setVisibility(visibility);
        mIvIdCardAfter.setVisibility(visibility);
        mTvFailReason.setVisibility(visibility);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_ID_CARD_FRONT:
                mPresenter.getIdCardFrontImage(data);
                break;
            case REQUEST_CODE_ID_CARD_AFTER:
                mPresenter.getIdCardAfterImage(data);
                break;
            case REQUEST_CODE_HEAD:
                mPresenter.getHeadImage(data);
                break;
        }
    }
}
