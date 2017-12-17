package com.yl.yhbmfw.api;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.socks.library.KLog;
import com.yl.library.rx.RxSubscriber;
import com.yl.library.rx.porxy.ProxyHandler;
import com.yl.library.utils.AppUtils;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Config;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.bean.User;
import com.yl.yhbmfw.mvp.activity.LoginActivity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.Part;

/**
 * @author Yang Shihao
 */

public class MyProxyHandler extends ProxyHandler {

    private static final String TAG = "MyProxyHandler";

    @Override
    protected synchronized Observable<?> refreshTokenWhenTokenInvalid() {
        synchronized (ProxyHandler.class) {
            final Config config = App.getInstance().getConfig();
            String phone = config.getPhone();
            String pwd = config.getPassword();
            if (!config.isLogin() || TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
                refreshTokenFail();
                logout();
            } else {
                mDisposable = new RxSubscriber<User>(Api.loginNoSubscribe(phone, pwd, AppUtils.getIMEI(App.getInstance(), phone), "")) {
                    @Override
                    protected void _onNext(User user) {
                        KLog.d(TAG, "获取新的token成功");
                        config.setToken(user.getToken());
                        config.setUser(user);
                        mIsTokenNeedRefresh = true;
                        refreshTokenSuccess();
                    }

                    @Override
                    protected void _onError(String code) {
                        KLog.d(TAG, "获取新的token失败");
                        //登陆失败,跳转其他页面,关闭其他所有Activity
                        refreshTokenFail();
                        logout();
                    }
                }.getSubscribe();
            }
            return Observable.just(true);
        }
    }

    private void logout() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                App.getInstance().getConfig().exit();
                Intent intent = new Intent(App.getInstance(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constant.KEY_BOOLEAN_1, true);
                App.getInstance().startActivity(intent);
            }
        });
    }

    @Override
    protected void updateMethodToken(Method method, Object[] args) {
        if (mIsTokenNeedRefresh) {
            Annotation[][] annotationsArray = method.getParameterAnnotations();
            Annotation[] annotations;
            if (annotationsArray != null && annotationsArray.length > 0) {
                for (int i = 0; i < annotationsArray.length; i++) {
                    annotations = annotationsArray[i];
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Field) {
                            if ("TOKEN".equals(((Field) annotation).value())) {
                                KLog.d(TAG, "替换token成功");
                                args[i] = App.getInstance().getConfig().getToken();
                                KLog.d("TOKEN", App.getInstance().getConfig().getToken());
                            }
                        } else if (annotation instanceof Part) {
                            if ("TOKEN".equals(((Part) annotation).value())) {
                                KLog.d(TAG, "替换token成功");
                                args[i] = App.getInstance().getConfig().getToken();
                                KLog.d("TOKEN", App.getInstance().getConfig().getToken());
                            }
                        }
                    }
                }
            }
            mIsTokenNeedRefresh = false;
        }
    }
}
