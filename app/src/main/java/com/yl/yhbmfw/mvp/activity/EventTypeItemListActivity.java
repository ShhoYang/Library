package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;
import android.text.TextUtils;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.EventTypeItemAdapter;
import com.yl.library.base.activity.BaseListActivity;
import com.yl.yhbmfw.mvp.contract.EventTypeItemListContract;
import com.yl.yhbmfw.mvp.presenter.EventTypeItemListPresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;

public class EventTypeItemListActivity extends BaseListActivity<EventTypeItemListContract.Presenter>
        implements EventTypeItemListContract.View {

    private String mType = "";

    @Override
    protected void initMVP() {
        Intent intent = getIntent();
        if (intent != null) {
            String temp = intent.getStringExtra(Constant.KEY_STRING_1);
            if (!TextUtils.isEmpty(temp)) {
                mType = temp;
            }
        }
        mPresenter = new EventTypeItemListPresenter(this);
    }

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(mType)) {
            setTitle("事件列表");
        } else {
            setTitle(mType);
        }
        setDefaultItemDecoration();
        setLoadMoreEnabled(false);
    }

    @Override
    protected CommonAdapter getAdapter() {
        return new EventTypeItemAdapter(this, R.layout.item_event_type_item, mPresenter.getDataList());
    }

    @Override
    public String getType() {
        return mType;
    }
}
