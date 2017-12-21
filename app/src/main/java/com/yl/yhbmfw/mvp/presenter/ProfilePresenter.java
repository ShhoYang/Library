package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.yl.library.rx.RxBus;
import com.yl.library.rx.RxSubscriber;
import com.yl.library.utils.AppUtils;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Config;
import com.yl.yhbmfw.api.Api;
import com.yl.yhbmfw.bean.User;
import com.yl.yhbmfw.event.EventRefresh;
import com.yl.yhbmfw.mvp.contract.ProfileContract;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Shihao
 */
public class ProfilePresenter extends ProfileContract.Presenter {

    private String mHeadPath;
    private String[] mSex = {"男", "女"};


    public ProfilePresenter(ProfileContract.View view) {
        super(view);
    }

    @Override
    public void setHeadImage(Intent data) {
        List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
        if (localMedias != null && localMedias.size() != 0) {
            mHeadPath = localMedias.get(0).getCompressPath();
            mView.setHeadImage(mHeadPath);
        }
    }

    @Override
    public List<String> getSex() {
        return Arrays.asList(mSex);
    }

    @Override
    public void submit() {
        mView.showDialog("正在提交...");
        addRx2Destroy(new RxSubscriber<String>(Api.updateUserInfo(mHeadPath, mView.getNickname(), mView.getSex(), mView.getBirthday(), mView.getEmail())) {

            @Override
            protected void _onNext(String s) {
                getUserInfo();
            }

            @Override
            protected void _onError(String code) {
                super._onError(code);
                mView.dismissDialog();
            }
        });
    }

    private void getUserInfo() {
        final Config config = App.getInstance().getConfig();
        addRx2Destroy(new RxSubscriber<User>(Api.login(config.getPhone(), config.getPassword(),
                AppUtils.getIMEI(mContext, config.getPhone()), ""), mView) {
            @Override
            protected void _onNext(User user) {
                config.setToken(user.getToken());
                config.setUser(user);
                mView.toast("提交成功");
                RxBus.getInstance().send(new EventRefresh(EventRefresh.REFRESH_USER_INFO));
                mView.finishActivity();
            }
        });
    }
}
