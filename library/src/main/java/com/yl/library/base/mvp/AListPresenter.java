package com.yl.library.base.mvp;

import android.app.Activity;

import com.yl.library.rx.RxManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang Shihao
 */
public abstract class AListPresenter<V extends IListView, D> {

    private static final int PAGE_SIZE = 20;

    public Activity mContext;
    protected V mView;
    protected RxManager mRxManager;
    protected List<D> mDataList = new ArrayList<>();
    protected int mPage = 1;
    protected boolean mIsRefresh = false;

    public AListPresenter(V view) {
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

    public void getPageData(boolean isRefresh) {
        mIsRefresh = isRefresh;
    }

    public List<D> getDataList() {
        return mDataList;
    }

    public void setDataList(final List<D> list) {
        if (mView == null) {
            return;
        }
        if (mDataList.size() == 0 && (list == null || list.size() == 0)) {
            mView.noData();
        } else {
            setPage();
            mDataList.addAll(list);
            mView.updateList();
            if (list.size() < PAGE_SIZE) {
                mView.noMoreData();
            }
        }
    }

    protected void clear() {
        if (mView == null) {
            return;
        }
        mDataList.clear();
        mView.updateList();
        mView.noData();
    }

    protected String getPage() {
        if (mIsRefresh) {
            return "1";
        } else {
            return (mPage + 1) + "";
        }
    }

    protected void setPage() {
        if (mIsRefresh) {
            mDataList.clear();
            mPage = 1;
        } else {
            mPage++;
        }
    }

    public static String getPageSize() {
        return PAGE_SIZE + "";
    }

    public RxManager getRxManager() {
        return mRxManager;
    }
}
