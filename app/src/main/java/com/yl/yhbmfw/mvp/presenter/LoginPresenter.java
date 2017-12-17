package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;
import android.text.TextUtils;

import com.yl.library.rx.RxBus;
import com.yl.library.rx.RxSubscriber;
import com.yl.library.utils.AppUtils;
import com.yl.library.utils.RegexUtils;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Config;
import com.yl.yhbmfw.api.Api;
import com.yl.yhbmfw.bean.User;
import com.yl.yhbmfw.event.EventRefresh;
import com.yl.yhbmfw.mvp.activity.MainActivity;
import com.yl.yhbmfw.mvp.contract.LoginContract;

/**
 * @author Yang Shihao
 */
public class LoginPresenter extends LoginContract.Presenter {

    private Config mConfig = App.getInstance().getConfig();

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void getAccount() {
        mView.setAccount(mConfig.getPhone(), mConfig.getPassword());
    }

    @Override
    public void login() {
        final String phone = mView.getPhone();
        final String pwd = mView.getPwd();
        if (TextUtils.isEmpty(phone)) {
            mView.toast("手机号不能为空");
            return;
        }

        if (!RegexUtils.isMobilePhoneNumber(phone)) {
            mView.toast("请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            mView.toast("密码不能为空");
            return;
        }

        mView.showDialog("正在登录...");

        mRxManager.add(new RxSubscriber<User>(Api.login(phone, pwd, AppUtils.getIMEI(mContext, phone),
                ""), mView) {

            @Override
            protected void _onNext(User userHttpResult) {
                User user = userHttpResult;
                mConfig.setPhone(phone);
                mConfig.setPassword(pwd);
                mConfig.setToken(user.getToken());
                mConfig.setLogin(true);
                mConfig.setUser(user);
                RxBus.getInstance().send(new EventRefresh(EventRefresh.REFRESH_USER_INFO));
                mView.gotoActivityAndFinish(new Intent(mContext, MainActivity.class));

            }
        });
    }
}
