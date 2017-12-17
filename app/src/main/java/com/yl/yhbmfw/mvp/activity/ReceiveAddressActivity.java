package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.yl.library.base.activity.BaseListActivity;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.RecAddressAdapter;
import com.yl.yhbmfw.bean.RecAddress;
import com.yl.yhbmfw.mvp.contract.ReceiveAddressContract;
import com.yl.yhbmfw.mvp.presenter.ReceiveAddressPresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;

import butterknife.OnClick;

/**
 * 收货地址
 */
public class ReceiveAddressActivity extends BaseListActivity<ReceiveAddressContract.Presenter>
        implements ReceiveAddressContract.View, RecAddressAdapter.OnItemEditClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receive_address;
    }

    @Override
    protected void initMVP() {
        mPresenter = new ReceiveAddressPresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("管理收货地址");
        setLoadMoreEnabled(false);
        setBackgroundColor(ContextCompat.getColor(this, R.color.line));
    }

    @Override
    protected void initData() {
        super.initData();
        //TODO
        //registerRxBus(EventRefresh.REFRESH_REC_ADDRESS_LIST);
    }

    @Override
    protected CommonAdapter getAdapter() {
        RecAddressAdapter recAddressAdapter = new RecAddressAdapter(this, R.layout.item_rec_address, mPresenter.getDataList());
        recAddressAdapter.setOnItemEditClickListener(this);
        return recAddressAdapter;
    }

    @OnClick(R.id.btn_add_rec)
    public void onBtnAddRecClicked() {
        startActivity(new Intent(ReceiveAddressActivity.this, AddReceiveAddressActivity.class));
    }

    @Override
    public void setDefault(RecAddress recAddress) {
        mPresenter.setDefaultRecAddress(recAddress);
    }

    @Override
    public void delete(RecAddress recAddress) {
        mPresenter.deleteRecAddress(recAddress);
    }

    @Override
    public void edit(RecAddress recAddress) {
        Intent intent = new Intent(this, AddReceiveAddressActivity.class);
        intent.putExtra(Constant.KEY_BEAN, recAddress);
        intent.putExtra(Constant.KEY_INT_1, mPresenter.getDataList().size());
        startActivity(intent);
    }

    @Override
    protected void onImageViewLeftClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        //判断是否是过来选择收货地址
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra(Constant.KEY_BOOLEAN_1, false)) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(Constant.KEY_RESULT, mPresenter.getDefaultAddress());
            setResult(RESULT_OK, resultIntent);
        }
        super.onBackPressed();
    }
}
