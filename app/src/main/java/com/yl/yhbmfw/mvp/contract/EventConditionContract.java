package com.yl.yhbmfw.mvp.contract;


import android.content.Intent;
import android.support.v4.app.Fragment;

import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;

/**
 * @author Yang Shihao
 */
public interface EventConditionContract {

    interface View extends IView {

        void handleVisibility(int visibility);

        void setViewPagerData(String[] titles, Fragment[] fragments);

        void setPhoneNum(String consulPhoneNum, String complaintPhoneNum);
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void createFragment(Intent intent);

        public abstract void getAuthInfo();

        public abstract void clickDeclare();

        public abstract void getPhoneNum();
    }
}
