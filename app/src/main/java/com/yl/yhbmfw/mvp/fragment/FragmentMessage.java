package com.yl.yhbmfw.mvp.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yl.library.base.fragment.BaseListFragment;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.MessageAdapter;
import com.yl.yhbmfw.mvp.contract.FgMessageContract;
import com.yl.yhbmfw.mvp.presenter.FgMessagePresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

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
        MessageAdapter messageAdapter = new MessageAdapter(mActivity, R.layout.item_message, mPresenter.getDataList());
        messageAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mPresenter.itemClick(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return messageAdapter;
    }

    @Override
    protected void initMVP() {
        mPresenter = new FgMessagePresenter(this);
    }
}
