package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.AListPresenter;
import com.yl.library.base.mvp.IListView;
import com.yl.yhbmfw.bean.MessageItem;

/**
 * @author Yang Shihao
 */
public interface FgMessageContract {

    interface View extends IListView {
    }

    abstract class Presenter extends AListPresenter<View, MessageItem> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void itemClick(int position);
    }
}
