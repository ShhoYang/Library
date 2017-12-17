package com.yl.yhbmfw.mvp.contract;


import android.content.Intent;

import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;
import com.yl.yhbmfw.bean.EventDetails;

import java.util.List;

/**
 * @author Yang Shihao
 */
public interface EventDetailsContract {

    interface View extends IView {

        void setEventInfo(String name, String submitTime, String serial);

        void setPostalInfo(String postalName, String postalNum);

        void setEventProgress(int cur, int total, String[] contents);

        void updateMaterial();

        void noMaterial();

        void noPostalInfo(String s);

        void noFinished();
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract List<EventDetails.Material> getMaterialList();

        public abstract void getDetails(Intent intent);

        public abstract void searchByEventName(String name);
    }
}
