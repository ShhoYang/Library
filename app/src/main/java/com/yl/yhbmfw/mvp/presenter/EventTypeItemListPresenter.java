package com.yl.yhbmfw.mvp.presenter;


import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.mvp.contract.EventTypeItemListContract;
import com.yl.yhbmfw.api.Api;
import com.yl.library.rx.RxSubscriber;

import java.util.List;


/**
 * @author Yang Shihao
 */
public class EventTypeItemListPresenter extends EventTypeItemListContract.Presenter {

    private String mType = "";


    public EventTypeItemListPresenter(EventTypeItemListContract.View view) {
        super(view);
        mType = mView.getType();
    }

    @Override
    public void getPageData(boolean isRefresh) {
        super.getPageData(isRefresh);
        addRx2Destroy(new RxSubscriber<List<EventTypeItem>>(Api.getEventTypeList(mType, null)) {

            @Override
            protected void _onNext(List<EventTypeItem> eventTypeItems) {
                setDataList(eventTypeItems);
            }

            @Override
            protected void _onError(String code) {
                super._onError(code);
                mView.loadError();
            }
        });
    }
}
