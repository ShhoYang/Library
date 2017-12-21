package com.yl.library.base.mvp;

import android.app.Activity;

import com.yl.library.rx.RxManager;
import com.yl.library.rx.RxSubscriber;

import io.reactivex.disposables.Disposable;

/**
 * @author Yang Shihao
 */

public class BasePresenter {

    public Activity mContext;
    private RxManager mRxManager2Stop;
    private RxManager mRxManager2Destroy;

    /**
     * 网络请求控制在生命周期OnStop
     */
    public void addRx2Stop(RxSubscriber rxSubscriber) {
        if (mRxManager2Stop == null) {
            mRxManager2Stop = new RxManager();
        }
        mRxManager2Stop.add(rxSubscriber);
    }

    public void addRx2Stop(Disposable disposable) {
        if (mRxManager2Stop == null) {
            mRxManager2Stop = new RxManager();
        }
        mRxManager2Stop.add(disposable);
    }

    public void onStop() {
        if (mRxManager2Stop != null) {
            mRxManager2Stop.clear();
            mRxManager2Stop = null;
        }
    }

    /**
     * 网络请求控制在生命周期onDestroy
     */
    public void addRx2Destroy(RxSubscriber rxSubscriber) {
        if (mRxManager2Destroy == null) {
            mRxManager2Destroy = new RxManager();
        }
        mRxManager2Destroy.add(rxSubscriber);
    }

    public void addRx2Destroy(Disposable disposable) {
        if (mRxManager2Destroy == null) {
            mRxManager2Destroy = new RxManager();
        }
        mRxManager2Destroy.add(disposable);
    }

    public void onDestroy() {
        if (mRxManager2Destroy != null) {
            mRxManager2Destroy.clear();
            mRxManager2Destroy = null;
        }
    }

    public RxManager getRxManager2Destroy() {
        if (mRxManager2Destroy == null) {
            mRxManager2Destroy = new RxManager();
        }
        return mRxManager2Destroy;
    }
}
