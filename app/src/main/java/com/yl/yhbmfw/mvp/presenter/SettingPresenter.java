package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;

import com.yl.library.rx.HttpCode;
import com.yl.library.rx.RetrofitUtils;
import com.yl.library.rx.RxSchedulers;
import com.yl.library.rx.RxSubscriber;
import com.yl.library.utils.AppUtils;
import com.yl.library.utils.CacheManager;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.api.Api;
import com.yl.yhbmfw.bean.VersionInfo;
import com.yl.yhbmfw.mvp.activity.LoginActivity;
import com.yl.yhbmfw.mvp.contract.SettingContract;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * @author Yang Shihao
 */
public class SettingPresenter extends SettingContract.Presenter {

    private VersionInfo mVersionInfo;


    public SettingPresenter(SettingContract.View view) {
        super(view);
    }

    @Override
    public void getCacheSize() {
        mRxManager.add(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(CacheManager.getCacheSize(mContext));
            }
        }).compose(RxSchedulers.<String>io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mView.setCacheSize(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void clearCache() {
        mRxManager.add(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(CacheManager.clearCache(mContext));
            }
        }).compose(RxSchedulers.<Boolean>io_main())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            mView.toast("清除缓存成功");
                            mView.setCacheSize("0KB");
                        } else {
                            mView.toast("清除缓存失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void checkVersion() {
        mView.showDialog("正在检查新版本...");
        mRxManager.add(new RxSubscriber<VersionInfo>(Api.checkApkVersion(), mView) {
            @Override
            protected void _onNext(VersionInfo versionInfo) {
                try {
                    if (Integer.parseInt(versionInfo.getApk_version()) > AppUtils.getVersionCode(mContext)) {
                        mVersionInfo = versionInfo;
                        mView.showUpdateDialog(mVersionInfo.getApk_desc());
                    } else {
                        mView.toast("已是最新版本");
                    }
                } catch (RuntimeException e) {
                    mView.toast("已是最新版本");
                }
            }

            @Override
            protected void _onError(String code) {
                if (code.equals(HttpCode.CODE_20001.getCode())) {
                    mView.toast("已是最新版本");
                } else {
                    super._onError(code);
                }
            }
        });
    }

    @Override
    public String getDownUrl() {
        return Constant.getBaseUrl() + mVersionInfo.getApk_path();
    }

    @Override
    public void signOut() {
        mView.showDialog("正在退出...");
        mRxManager.add(new RxSubscriber<String>(Api.signOut(), mView) {
            @Override
            protected void _onNext(String s) {

            }

            @Override
            protected void _onError(String code) {
                RetrofitUtils.getInstance().cookieClear();
                App.getInstance().getConfig().exit();
                mView.gotoActivityAndFinish(new Intent(mContext, LoginActivity.class));
            }
        });
    }
}
