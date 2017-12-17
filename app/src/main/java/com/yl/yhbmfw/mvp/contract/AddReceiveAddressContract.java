package com.yl.yhbmfw.mvp.contract;


import android.content.Intent;

import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;
import com.yl.yhbmfw.bean.RegionNode;

import java.util.List;

/**
 * @author Yang Shihao
 */
public interface AddReceiveAddressContract {

    interface View extends IView {

        void setTitleText(String s);

        void setName(String s);

        void setPhone(String s);

        void setArea(String s);

        void setStreet(String s);

        void setDetailsAddress(String s);

        void isDefault(boolean def);

        void showAreaDialog(List<RegionNode> list1, List<List<RegionNode>> list2, List<List<List<RegionNode>>> list3);

        void showStreetDialog(List<RegionNode> list1);

        String getName();

        String getPhone();

        String getDetailsAddress();

        boolean isDefault();

        void showDefaultAddressView(int visibility);
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void initData(Intent intent);

        public abstract void getAreaList();

        public abstract void getStreetList();

        public abstract void setSelectedArea(int index1, int index2, int index3);

        public abstract void setSelectedStreet(int index1);

        public abstract void save();
    }
}
