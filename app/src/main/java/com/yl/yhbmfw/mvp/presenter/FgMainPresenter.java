package com.yl.yhbmfw.mvp.presenter;


import android.text.TextUtils;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.BannerItem;
import com.yl.yhbmfw.bean.Menu;
import com.yl.yhbmfw.mvp.contract.FgMainContract;
import com.yl.yhbmfw.api.Api;
import com.yl.library.rx.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang Shihao
 */
public class FgMainPresenter extends FgMainContract.Presenter {

    private List<BannerItem> mBannerData = new ArrayList<>();

    public FgMainPresenter(FgMainContract.View view) {
        super(view);
    }

    @Override
    public List<Integer> getTempBannerImg() {
        List<Integer> temp = new ArrayList<>();
        temp.add(R.drawable.banner0);
        return temp;
    }

    @Override
    public List<String> getTempBannerTitle() {
        List<String> temp = new ArrayList<>();
        temp.add("");
        return temp;
    }

    @Override
    public void bannerClick(int position) {

    }

    @Override
    public List<Menu> getDataList() {
        int[] icons = {R.drawable.index_1, R.drawable.index_2, R.drawable.index_3, R.drawable.index_4,
                R.drawable.index_5, R.drawable.index_6, R.drawable.index_7, R.drawable.index_8,
                R.drawable.index_9, R.drawable.index_10, R.drawable.index_11, R.drawable.index_12,
                R.drawable.index_13, R.drawable.index_14, R.drawable.index_15, R.drawable.index_16};
        String[] texts = {"福利救助", "婚育服务", "证件办理", "司法公正", "教育培训", "求职执业", "纳税缴费", "就医保健", "社会保险", "房屋租售",
                "交通旅游", "场馆设施", "公共安全", "环境气象", "三农服务", "小企业服务"};
        int size = texts.length;
        for (int i = 0; i < size; i++) {
            mDataList.add(new Menu(icons[i], texts[i]));
        }
        return mDataList;
    }

    /**
     * 婚育服务、教育培训、求职执业、纳税缴费、就医保健、社会保险、福利救助、房屋租售、交通旅游、证件办理、
     * 场馆设施、公共安全、司法公正、环境气象、三农服务、小企业服务
     */
    @Override
    public void getPageData(boolean isRefresh) {
        mRxManager.add(new RxSubscriber<List<BannerItem>>(Api.getBanner()) {
            @Override
            protected void _onNext(List<BannerItem> bannerItems) {
                arrangeBannerDate(bannerItems);
                mView.finishRefresh();
            }

            @Override
            protected void _onError(String code) {
                mView.finishRefresh();
            }
        });
    }

    private void arrangeBannerDate(List<BannerItem> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        mBannerData.clear();
        mBannerData.addAll(list);
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        String temp;
        for (BannerItem item : mBannerData) {
            temp = item.getIcon();
            if (TextUtils.isEmpty(temp)) {
                continue;
            }
            images.add(Constant.getBaseUrl() + temp);
            temp = item.getTitle();
            titles.add(temp == null ? "" : temp);
        }
        mView.updateBanner(images, titles);
    }
}
