package com.yl.yhbmfw.api;

import com.socks.library.KLog;
import com.yl.library.rx.porxy.ProxyHandler;

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
            //TODO 调用登录,更新token
            if (true) {
                refreshTokenSuccess();
            } else {
                refreshTokenFail();
            }
            return Observable.just(true);
        }
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
                                // args[i] = App.getInstance().getConfig().getToken();
                                //KLog.d("TOKEN", App.getInstance().getConfig().getToken());
                            }
                        } else if (annotation instanceof Part) {
                            if ("TOKEN".equals(((Part) annotation).value())) {
                                KLog.d(TAG, "替换token成功");
                                // args[i] = App.getInstance().getConfig().getToken();
                                // KLog.d("TOKEN", App.getInstance().getConfig().getToken());
                            }
                        }
                    }
                }
            }
            mIsTokenNeedRefresh = false;
        }
    }
}
