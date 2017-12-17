package com.yl.yhbmfw.mvp.contract;


import android.os.Bundle;

import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;


/**
 * @author Yang Shihao
 */
public interface FgEventConditionProcessContract {

    interface View extends IView {

        void setEventProcess(int total, String[] contents);
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void getEventConditionProcess(Bundle bundle);
    }
}
