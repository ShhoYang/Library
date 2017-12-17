package com.yl.yhbmfw.mvp.contract;


import android.content.Intent;

import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;
import com.yl.yhbmfw.bean.RecAddress;

/**
 * @author Yang Shihao
 */
public interface EventHandleContract {

    interface View extends IView {

        void setEventName(String s);

        void addView(android.view.View view);

        void clear();

        void showObtainMode(int visibility);

        void showRlRecAddress(int visibility);

        void showRecAddressInfo(int visibility);

        void showSetRecAddress(int visibility);

        void setRecAddress(RecAddress recAddress);

        boolean isSelf();
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void createForm(Intent intent);

        public abstract void addImage(int requestCode, Intent data);

        public abstract void submit();

        public abstract void getDefaultRecAddress();
    }
}
