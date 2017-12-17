package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;

/**
 * @author Yang Shihao
 */
public interface MainContract {

    interface View extends IView {

        void setUnreadMsgNum(String count);

        void showUpdateDialog(String msg);
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void deleteFile();

        public abstract void checkVersion();

        public abstract String getDownUrl();

        public abstract void getUnreadMsgCount();
    }
}
