package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.AListPresenter;
import com.yl.library.base.mvp.IListView;
import com.yl.yhbmfw.bean.RecAddress;

/**
 * @author Yang Shihao
 */
public interface ReceiveAddressContract {

    interface View extends IListView {

    }

    abstract class Presenter extends AListPresenter<View, RecAddress> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void deleteRecAddress(RecAddress recAddress);

        public abstract void setDefaultRecAddress(RecAddress recAddress);

        public abstract RecAddress getDefaultAddress();
    }
}
