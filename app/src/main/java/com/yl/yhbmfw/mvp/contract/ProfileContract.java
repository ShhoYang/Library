package com.yl.yhbmfw.mvp.contract;


import android.content.Intent;

import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;

import java.util.List;

/**
 * @author Yang Shihao
 */
public interface ProfileContract {

    interface View extends IView {

        String getNickname();

        String getSex();

        String getBirthday();

        String getEmail();

        void setHeadImage(String path);
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void setHeadImage(Intent data);

        public abstract List<String> getSex();

        public abstract void submit();
    }
}
