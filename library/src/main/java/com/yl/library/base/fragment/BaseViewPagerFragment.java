package com.yl.library.base.fragment;

import android.annotation.TargetApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.library.R;
import com.yl.library.adapter.FragmentWithTitleAdapter;
import com.yl.library.base.mvp.APresenter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Shihao
 */
public abstract class BaseViewPagerFragment<P extends APresenter> extends BaseFragment
        implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LinearLayout mLlEmpty;

    protected P mPresenter;

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        super.onDestroyView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initUI() {
        super.initUI();
        if (mPresenter != null) {
            mPresenter.mContext = mActivity;
        }
        mTabLayout = (TabLayout) $(R.id.tab);
        mViewPager = (ViewPager) $(R.id.vp);
        mLlEmpty = (LinearLayout) $(R.id.ll_empty);
        ImageView ivEmpty = (ImageView) $(R.id.iv_empty);
        TextView tvEmpty = (TextView) $(R.id.tv_empty);
        if (ivEmpty != null) {
            ivEmpty.setOnClickListener(this);
        }
        if (tvEmpty != null) {
            tvEmpty.setOnClickListener(this);
        }
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initData() {
        setViewPagerData(getTitles(), getFragments());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_empty || view.getId() == R.id.tv_empty) {
            onEmptyViewClicked();
        }
    }

    protected void onEmptyViewClicked() {

    }


    protected void setPageLimit(int limit) {
        mViewPager.setOffscreenPageLimit(limit);
    }

    public void setViewPagerData(String[] titles, Fragment[] fragments) {
        setViewPagerData(Arrays.asList(titles), Arrays.asList(fragments));
    }

    @TargetApi(17)
    public void setViewPagerData(List<String> titles, List<Fragment> fragments) {
        if (titles == null || fragments == null) {
            return;
        }
        int fragmentSize = fragments.size();
        int titleSize = titles.size();
        if (fragmentSize == 0 || titleSize == 0 || fragmentSize != titleSize) {
            return;
        }
        mViewPager.setAdapter(new FragmentWithTitleAdapter(getChildFragmentManager(), titles, fragments));
        if (fragmentSize == 1) {
            mTabLayout.setVisibility(View.GONE);
        } else if (fragmentSize < 5) {
            mTabLayout.setVisibility(View.VISIBLE);
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mTabLayout.setVisibility(View.VISIBLE);
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    public abstract String[] getTitles();

    public abstract Fragment[] getFragments();
}
