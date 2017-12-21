package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.AListPresenter;
import com.yl.library.base.mvp.IListView;
import com.yl.yhbmfw.bean.EventList;

/**
 * @author Yang Shihao
 */
public interface EventContract {

    interface View extends IListView {

    }

    abstract class Presenter extends AListPresenter<View, EventList.EventItem> {

        public Presenter(View view) {
            super(view);
        }
    }
}
