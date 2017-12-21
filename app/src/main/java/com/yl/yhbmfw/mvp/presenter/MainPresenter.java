package com.yl.yhbmfw.mvp.presenter;


import android.text.TextUtils;

import com.luck.picture.lib.tools.PictureFileUtils;
import com.yl.library.rx.RxSubscriber;
import com.yl.library.utils.AppUtils;
import com.yl.library.utils.FileLocalUtils;
import com.yl.library.utils.FileUtils;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.api.Api;
import com.yl.yhbmfw.bean.VersionInfo;
import com.yl.yhbmfw.mvp.contract.MainContract;

/**
 * @author Yang Shihao
 */
public class MainPresenter extends MainContract.Presenter {

    private VersionInfo mVersionInfo;

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void deleteFile() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        FileUtils.deleteDir(FileLocalUtils.getAudioDir());
                        FileUtils.deleteDir(FileLocalUtils.getImageDir());
                        FileUtils.deleteDir(FileLocalUtils.getDownDir());
                        FileUtils.deleteDir(FileLocalUtils.getMonitorDir());
                        PictureFileUtils.deleteCacheDirFile(mContext);
                    }
                }
        ).start();
    }

    @Override
    public void checkVersion() {
        addRx2Destroy(new RxSubscriber<VersionInfo>(Api.checkApkVersion()) {
            @Override
            protected void _onNext(VersionInfo versionInfo) {
                try {
                    if (Integer.parseInt(versionInfo.getApk_version()) > AppUtils.getVersionCode(mContext)) {
                        mVersionInfo = versionInfo;
                        mView.showUpdateDialog(mVersionInfo.getApk_desc());
                    }
                } catch (RuntimeException e) {
                }
            }

            @Override
            protected void _onError(String code) {

            }
        });
    }

    @Override
    public String getDownUrl() {
        return Constant.getBaseUrl() + mVersionInfo.getApk_path();
    }

    @Override
    public void getUnreadMsgCount() {
        if (!App.getInstance().getConfig().isLogin() || TextUtils.isEmpty(App.getInstance().getConfig().getToken())) {
            return;
        }
        addRx2Destroy(new RxSubscriber<String>(Api.getUnreadMsgNum()) {

            @Override
            protected void _onNext(String s) {
                mView.setUnreadMsgNum(s);
            }

            @Override
            protected void _onError(String code) {
                mView.setUnreadMsgNum(null);
            }
        });
    }
}
