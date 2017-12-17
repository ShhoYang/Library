package com.yl.library.base.mvp;

import android.app.Activity;

import com.yl.library.rx.RxManager;


/**
 * @author Yang Shihao
 */
public abstract class APresenter<V extends IView> {

    public Activity mContext;
    protected V mView;
    protected RxManager mRxManager;

    public APresenter(V view) {
        mView = view;
        onStart();
    }

    protected void onStart() {
        mRxManager = new RxManager();
    }

    public void onDestroy() {
        mRxManager.clear();
        mView = null;
    }

    public RxManager getRxManager() {
        return mRxManager;
    }
}
