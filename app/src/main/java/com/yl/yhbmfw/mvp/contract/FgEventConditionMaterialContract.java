package com.yl.yhbmfw.mvp.contract;


import android.os.Bundle;

import com.yl.library.base.mvp.AListPresenter;
import com.yl.library.base.mvp.IListView;
import com.yl.yhbmfw.bean.EventTypeItem;

/**
 * @author Yang Shihao
 */
public interface FgEventConditionMaterialContract {

    interface View extends IListView {

    }

    abstract class Presenter extends AListPresenter<View,EventTypeItem.EventMaterial> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void getEventMaterial(Bundle bundle);
    }
}
