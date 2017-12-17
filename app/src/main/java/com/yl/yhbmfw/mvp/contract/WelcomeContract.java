package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;

/**
 * @author Yang Shihao
 */
public interface WelcomeContract {

    interface View extends IView {

    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }
    }
}
