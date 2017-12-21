package com.yl.yhbmfw.mvp.activity;

import com.yl.library.base.activity.BaseListActivity;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.EventAdapter;
import com.yl.yhbmfw.mvp.contract.EventContract;
import com.yl.yhbmfw.mvp.presenter.EventPresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;

/**
 * 我的事件
 */
public class EventActivity extends BaseListActivity<EventContract.Presenter>
        implements EventContract.View {

    @Override
    protected void initView() {
        setTitle("我的事件");
        setDefaultItemDecoration();
    }

    @Override
    protected CommonAdapter getAdapter() {
        return new EventAdapter(this, R.layout.item_event, mPresenter.getDataList());
    }

    @Override
    protected void initMVP() {
        mPresenter = new EventPresenter(this);
    }
}