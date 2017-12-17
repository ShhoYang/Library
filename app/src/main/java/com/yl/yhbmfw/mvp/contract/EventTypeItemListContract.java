package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.AListPresenter;
import com.yl.library.base.mvp.IListView;
import com.yl.yhbmfw.bean.EventTypeItem;


/**
 * @author Yang Shihao
 */
public interface EventTypeItemListContract {

    interface View extends IListView {

        String getType();
    }

    abstract class Presenter extends AListPresenter<View,EventTypeItem> {
        public Presenter(View view) {
            super(view);
        }
    }
}
