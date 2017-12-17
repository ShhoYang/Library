package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;

/**
 * @author Yang Shihao
 */
public interface ChangePasswordContract {

    interface View extends IView {

        String getPhone();

        String getOldPwd();

        String getNewPwd();

        String getConfirmPwd();
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void submit();
    }
}
