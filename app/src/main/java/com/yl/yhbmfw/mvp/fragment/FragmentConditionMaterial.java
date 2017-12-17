package com.yl.yhbmfw.mvp.fragment;

import com.yl.library.base.fragment.BaseListFragment;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.MaterialAdapter;
import com.yl.yhbmfw.mvp.contract.FgEventConditionMaterialContract;
import com.yl.yhbmfw.mvp.presenter.FgEventConditionMaterialPresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;

/**
 * @author Yang Shihao
 *         <p>
 *         办理条件-申报素材
 */
public class FragmentConditionMaterial extends BaseListFragment<FgEventConditionMaterialContract.Presenter>
        implements FgEventConditionMaterialContract.View {
    @Override
    protected CommonAdapter getAdapter() {
        return new MaterialAdapter(mActivity, R.layout.item_event_material, mPresenter.getDataList());
    }

    @Override
    protected void initMVP() {
        mPresenter = new FgEventConditionMaterialPresenter(this);
    }


    @Override
    protected void initView() {
        setRefreshEnabled(false);
        setLoadMoreEnabled(false);
    }

    @Override
    protected void initData() {
        mPresenter.getEventMaterial(getArguments());
    }
}
