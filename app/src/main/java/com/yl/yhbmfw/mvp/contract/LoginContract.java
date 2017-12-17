package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;

/**
 * @author Yang Shihao
 */
public interface LoginContract {

    interface View extends IView {

        String getPhone();

        String getPwd();

        void setAccount(String phone, String pwd);
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void getAccount();

        public abstract void login();
    }
}
