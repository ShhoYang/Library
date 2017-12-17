package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;
import com.yl.yhbmfw.bean.EventTypeItem;

import java.util.List;


/**
 * @author Yang Shihao
 */
public interface SearchContract {

    interface View extends IView {

        void updateList();
    }

    abstract class Presenter extends APresenter<View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract List<EventTypeItem> getDataList();

        public abstract void search(String keyWord);
    }
}
