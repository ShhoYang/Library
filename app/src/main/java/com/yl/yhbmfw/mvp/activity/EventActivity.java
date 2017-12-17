package com.yl.yhbmfw.mvp.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.EventAdapter;
import com.yl.library.base.activity.BaseListActivity;
import com.yl.yhbmfw.mvp.contract.EventContract;
import com.yl.yhbmfw.mvp.presenter.EventPresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

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
        EventAdapter eventAdapter = new EventAdapter(this, R.layout.item_event, mPresenter.getDataList());
        eventAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mPresenter.getRejectEventData(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return eventAdapter;
    }

    @Override
    protected void initMVP() {
        mPresenter = new EventPresenter(this);
    }
}