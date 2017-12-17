package com.yl.yhbmfw;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yl.library.Library;
import com.yl.library.ui.CrashActivity;
import com.yl.yhbmfw.mvp.activity.WelcomeActivity;

import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * @author Yang Shihao
 */

public class App extends MultiDexApplication {

    private static final String TAG = "yuhang";

    private static App app;
    private Config mConfig;

    public boolean mToMessage = false;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Library.getInstance().init(this, Constant.getBaseUrl() + "/Interfaces/", BuildConfig.API_DEBUG);
        //异常
        CaocConfig.Builder.create().errorActivity(CrashActivity.class).restartActivity(WelcomeActivity.class).apply();
    }

    public static App getInstance() {
        return app;
    }


    public Config getConfig() {
        if (mConfig == null) {
            mConfig = new Config(app);
        }
        return mConfig;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
    }
}