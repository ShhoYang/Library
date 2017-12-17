package com.yl.yhbmfw.mvp.presenter;


import android.text.TextUtils;

import com.yl.library.rx.RxSubscriber;
import com.yl.library.utils.AppUtils;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Config;
import com.yl.yhbmfw.api.Api;
import com.yl.yhbmfw.bean.User;
import com.yl.yhbmfw.mvp.contract.WelcomeContract;

/**
 * @author Yang Shihao
 */
public class WelcomePresenter extends WelcomeContract.Presenter {

    public WelcomePresenter(WelcomeContract.View view) {
        super(view);
        login();
    }

    private void login() {
        final Config config = App.getInstance().getConfig();
        String phone = config.getPhone();
        String pwd = config.getPassword();
        if (config.isLogin() && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd)) {
            mRxManager.add(new RxSubscriber<User>(Api.login(phone, pwd, AppUtils.getIMEI(mContext, phone), "")) {
                @Override
                protected void _onNext(User user) {
                    config.setToken(user.getToken());
                    config.setUser(user);
                }

                @Override
                protected void _onError(String code) {
                    config.setLogin(false);
                    config.setToken("");
                }
            });
        }
    }
}
