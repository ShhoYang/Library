package com.yl.yhbmfw.mvp.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.yl.library.base.activity.BaseActivity;
import com.yl.library.utils.KeyBoardUtils;
import com.yl.library.widget.ClearEditViewSingle;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.EventTypeItemAdapter;
import com.yl.yhbmfw.mvp.contract.SearchContract;
import com.yl.yhbmfw.mvp.presenter.SearchPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity<SearchContract.Presenter>
        implements SearchContract.View {

    @BindView(R.id.et_search)
    ClearEditViewSingle mEtSearch;

    @BindView(R.id.rv_result)
    RecyclerView mRvResult;

    private EventTypeItemAdapter mAdapter;

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initMVP() {
        mPresenter = new SearchPresenter(this);
    }

    @Override
    protected void initView() {
        mEtSearch.getFocus();
        mRvResult.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EventTypeItemAdapter(this, R.layout.item_event_type_item, mPresenter.getDataList());
        mRvResult.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        KeyBoardUtils.closeSoftInput(this);
        finish();
    }

    @OnClick(R.id.tv_search)
    public void onTvSearchClicked() {
        String keyword = mEtSearch.getText();
        if (!TextUtils.isEmpty(keyword)) {
            mPresenter.search(keyword);
            KeyBoardUtils.closeSoftInput(this);
        }
    }
}
