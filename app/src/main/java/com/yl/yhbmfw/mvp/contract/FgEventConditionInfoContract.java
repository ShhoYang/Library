package com.yl.yhbmfw.mvp.contract;


import android.os.Bundle;

import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;

/**
 * @author Yang Shihao
 */
public interface FgEventConditionInfoContract {

    interface View extends IView {
        void setEventName(String s);

        void setEventCost(String s);

        void setEventTimeLimit(String s);

        void setEventLegalTime(String s);

        void setEventObject(String s);

        void setEventDepart(String s);

        void setEventHandleTime(String s);

        void setEventHandleAddr(String s);

        void setEventDesc(String s);
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void getEventInfo(Bundle bundle);
    }
}
