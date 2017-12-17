package com.yl.yhbmfw.mvp.contract;


import com.yl.library.base.mvp.AListPresenter;
import com.yl.library.base.mvp.IListView;
import com.yl.yhbmfw.bean.Menu;

import java.util.List;

/**
 * @author Yang Shihao
 */
public interface FgMainContract {

    interface View extends IListView {

        void updateBanner(List<String> images, List<String> titles);
    }

    abstract class Presenter extends AListPresenter<View, Menu> {
        public Presenter(View view) {
            super(view);
        }

        public abstract List<Integer> getTempBannerImg();

        public abstract List<String> getTempBannerTitle();

        public abstract void bannerClick(int position);
    }
}
