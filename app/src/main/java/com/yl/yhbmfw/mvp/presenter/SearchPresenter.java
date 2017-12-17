package com.yl.yhbmfw.mvp.presenter;


import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.mvp.contract.SearchContract;
import com.yl.yhbmfw.api.Api;
import com.yl.library.rx.RxSubscriber;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Yang Shihao
 */
public class SearchPresenter extends SearchContract.Presenter {
    private List<EventTypeItem> mDatas = new ArrayList<>();

    public SearchPresenter(SearchContract.View view) {
        super(view);
    }

    @Override
    public List<EventTypeItem> getDataList() {
        return mDatas;
    }

    @Override
    public void search(String keyWord) {
        mView.showDialog("正在搜索...");
        mRxManager.add(new RxSubscriber<List<EventTypeItem>>(Api.getEventTypeList(null, keyWord), mView) {


            @Override
            protected void _onNext(List<EventTypeItem> eventTypeItems) {
                if (eventTypeItems.size() == 0) {
                    mView.toast("未搜索到");
                } else {
                    mDatas.clear();
                    mDatas.addAll(eventTypeItems);
                    mView.updateList();
                }
            }

            @Override
            protected void _onError(String code) {
                mView.toast("搜索失败");
            }
        });
    }
}
