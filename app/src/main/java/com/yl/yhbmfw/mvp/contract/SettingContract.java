package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;

/**
 * @author Yang Shihao
 */
public interface SettingContract {

    interface View extends IView {

        void setCacheSize(String text);

        void showUpdateDialog(String msg);
    }

    abstract class Presenter extends APresenter<View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void getCacheSize();

        public abstract void clearCache();

        public abstract void checkVersion();

        public abstract String getDownUrl();

        public abstract void signOut();
    }
}
