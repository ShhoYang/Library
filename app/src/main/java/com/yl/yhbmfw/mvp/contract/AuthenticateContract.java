package com.yl.yhbmfw.mvp.contract;


import android.content.Intent;

import com.yl.library.base.mvp.APresenter;
import com.yl.library.base.mvp.IView;
import com.yl.yhbmfw.bean.RegionNode;

import java.util.List;

/**
 * @author Yang Shihao
 */
public interface AuthenticateContract {

    interface View extends IView {

        String getName();

        String getSex();

        String getIdCard();

        void setName(String s);

        void setIdCard(String s);

        void setStatus(String s);

        void setCommunity(String s);

        void setFailReason(String s);

        void setIdCardFrontImage(String path);

        void setIdCardAfterImage(String path);

        void setHeadImage(String path);

        void showCommunityDialog(List<RegionNode> street, List<List<RegionNode>> community);

        void setEnableEdit(boolean enable);

        void showUserInfo(int visibility);
    }

    abstract class Presenter extends APresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void getAuthInfo();

        public abstract List<String> getSex();

        public abstract void getRegionList();

        public abstract String getRegionName(int index1, int index2);

        public abstract void getIdCardFrontImage(Intent data);

        public abstract void getIdCardAfterImage(Intent data);

        public abstract void getHeadImage(Intent data);

        public abstract void submit();
    }
}
