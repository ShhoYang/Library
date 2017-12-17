package com.yl.yhbmfw.mvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.socks.library.KLog;
import com.yl.library.base.fragment.BaseListFragment;
import com.yl.library.utils.ImageManager;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.EventTypeMenuAdapter;
import com.yl.yhbmfw.mvp.activity.SearchActivity;
import com.yl.yhbmfw.mvp.contract.FgMainContract;
import com.yl.yhbmfw.mvp.presenter.FgMainPresenter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Yang Shihao
 *         <p>
 *         首页
 */
public class FragmentMain extends BaseListFragment<FgMainContract.Presenter>
        implements FgMainContract.View {

    private static final String TAG = "FragmentMain";

    @BindView(R.id.banner)
    Banner mBanner;

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initMVP() {
        mPresenter = new FgMainPresenter(this);
    }

    @Override
    protected void initView() {
        setLoadMoreEnabled(false);
        setLayoutManager(new GridLayoutManager(mActivity, 4));
        initBanner();
    }

    @Override
    protected void initData() {
        mPresenter.getPageData(true);
    }

    @Override
    protected CommonAdapter getAdapter() {
        return new EventTypeMenuAdapter(mActivity, R.layout.item_menu_event_type, mPresenter.getDataList());
    }

    private void initBanner() {
        mBanner.setImages(mPresenter.getTempBannerImg())
                .setBannerTitles(mPresenter.getTempBannerTitle())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        ImageManager.getInstance().loadImage(context, path, imageView);
                    }
                })
                .setBannerAnimation(Transformer.DepthPage)
                .setDelayTime(3000)
                .start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                mPresenter.bannerClick(position);
                KLog.d(TAG, "点击了banner " + position);
            }
        });
    }


    @OnClick(R.id.iv_search)
    public void onIvSearchClicked() {
        startActivity(new Intent(mActivity, SearchActivity.class));
    }

    @Override
    public void updateBanner(List<String> images, List<String> titles) {
        mBanner.update(images, titles);
    }
}
