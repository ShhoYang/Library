package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;

import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Config;
import com.yl.yhbmfw.bean.AuthInfo;
import com.yl.yhbmfw.bean.User;
import com.yl.yhbmfw.mvp.activity.AuthenticateActivity;
import com.yl.yhbmfw.mvp.activity.EventActivity;
import com.yl.yhbmfw.mvp.activity.LoginActivity;
import com.yl.yhbmfw.mvp.activity.ProfileActivity;
import com.yl.yhbmfw.mvp.activity.ReceiveAddressActivity;
import com.yl.yhbmfw.mvp.contract.FgMyContract;
import com.yl.yhbmfw.api.Api;
import com.yl.library.rx.RxSubscriber;

/**
 * @author Yang Shihao
 */
public class FgMyPresenter extends FgMyContract.Presenter {


    private Config mConfig = App.getInstance().getConfig();
    private User mUser;

    public FgMyPresenter(FgMyContract.View view) {
        super(view);
    }

    @Override
    public void getUserInfo() {
        mUser = mConfig.getUser();
        if (mUser == null) {
            mView.setNoLoginShow(true);
        } else {
            mView.setNoLoginShow(false);
            String phone = mConfig.getPhone();
            mView.setPhone(phone.substring(0, 3) + "****" + phone.substring(7, phone.length()));
            mView.setHead(mUser.getPhoto());
        }
    }

    /**
     * 状态,0认证成功,1认证资料正在审核中,2认证失败,3未提交认证
     */
    @Override
    public void getAuthInfo() {
        mUser = mConfig.getUser();
        if (mUser == null) {
            return;
        }
        addRx2Destroy(new RxSubscriber<AuthInfo>(Api.authenticateResult()) {
            @Override
            protected void _onNext(AuthInfo authInfo) {
                mConfig.setAuthInfo(authInfo);
                mView.setName(authInfo.getName());
                mView.setAuthStatus(authInfo.getStatus());
            }
        });
    }

    @Override
    public void clickProfile() {
        if (mConfig.isLogin()) {
            mView.gotoActivity(new Intent(mContext, ProfileActivity.class));
        } else {
            mView.gotoActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    @Override
    public void clickMyEvent() {
        if (mConfig.isLogin()) {
            mView.gotoActivity(new Intent(mContext, EventActivity.class));
        } else {
            mView.gotoActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    @Override
    public void clickAuth() {
        if (mConfig.isLogin()) {
            mView.gotoActivity(new Intent(mContext, AuthenticateActivity.class));
        } else {
            mView.gotoActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    @Override
    public void clickRecAddress() {
        if (mConfig.isLogin()) {
            mView.gotoActivity(new Intent(mContext, ReceiveAddressActivity.class));
        } else {
            mView.gotoActivity(new Intent(mContext, LoginActivity.class));
        }
    }
}
