package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.bean.EventList;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.mvp.activity.EventHandleActivity;
import com.yl.yhbmfw.mvp.contract.EventContract;
import com.yl.yhbmfw.api.Api;
import com.yl.library.rx.RxSubscriber;

/**
 * @author Yang Shihao
 *         我的事件
 */
public class EventPresenter extends EventContract.Presenter {

    public EventPresenter(EventContract.View view) {
        super(view);
    }

    @Override
    public void getRejectEventData(int position) {
        final EventList.EventItem eventItem = mDataList.get(position);
        mRxManager.add(new RxSubscriber<EventTypeItem>(Api.getRejectEventData(eventItem.getId())) {
            @Override
            protected void _onNext(EventTypeItem eventTypeItem) {
                eventTypeItem.setName(eventItem.getName());
                Intent intent = new Intent(mContext, EventHandleActivity.class);
                intent.putExtra(Constant.KEY_BEAN, eventTypeItem);
                intent.putExtra(Constant.KEY_STRING_1, eventItem.getId());
                mView.gotoActivity(intent);
            }
        });
    }

    @Override
    public void getPageData(boolean isRefresh) {
        super.getPageData(isRefresh);
        mRxManager.add(new RxSubscriber<EventList>(Api.getEventList()) {
            @Override
            protected void _onNext(EventList eventList) {
                setDataList(eventList.getRows());
            }
        });
    }
}
