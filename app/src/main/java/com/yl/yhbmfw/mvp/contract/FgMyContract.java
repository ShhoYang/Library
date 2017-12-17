package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;

/**
 * @author Yang Shihao
 */
public interface FgMyContract {

    interface View extends IView {

        void setName(String s);

        void setPhone(String s);

        void setAuthStatus(String s);

        void setHead(Object path);

        void setNoLoginShow(boolean show);
    }

    abstract class Presenter extends APresenter<View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void getUserInfo();

        public abstract void getAuthInfo();

        public abstract void clickProfile();

        public abstract void clickMyEvent();

        public abstract void clickAuth();

        public abstract void clickRecAddress();
    }
}
