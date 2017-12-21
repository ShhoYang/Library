package com.yl.yhbmfw.mvp.fragment;

import com.yl.library.base.fragment.BaseListFragment;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.MessageAdapter;
import com.yl.yhbmfw.mvp.contract.FgMessageContract;
import com.yl.yhbmfw.mvp.presenter.FgMessagePresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;

/**
 * @author Yang Shihao
 *         <p>
 *         我的
 */
public class FragmentMessage extends BaseListFragment<FgMessageContract.Presenter>
        implements FgMessageContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        setDefaultItemDecoration();
    }

    @Override
    protected CommonAdapter getAdapter() {
        return new MessageAdapter(mActivity, R.layout.item_message, mPresenter.getDataList());
    }

    @Override
    protected void initMVP() {
        mPresenter = new FgMessagePresenter(this);
    }
}
