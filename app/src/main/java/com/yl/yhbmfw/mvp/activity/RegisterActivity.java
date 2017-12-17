package com.yl.yhbmfw.mvp.activity;

import android.widget.Button;

import com.yl.library.base.activity.BaseActivity;
import com.yl.library.widget.ClearEditViewSingle;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.mvp.contract.RegisterContract;
import com.yl.yhbmfw.mvp.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterContract.Presenter>
        implements RegisterContract.View {


    @BindView(R.id.et_phone)
    ClearEditViewSingle mEtPhone;
    @BindView(R.id.et_code)
    ClearEditViewSingle mEtCode;
    @BindView(R.id.et_pwd)
    ClearEditViewSingle mEtPwd;
    @BindView(R.id.btn_get_code)
    Button mBtnCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initMVP() {
        mPresenter = new RegisterPresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("注册");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_get_code)
    public void onBtnGetCodeClicked() {
        mPresenter.getCode();
    }

    @OnClick(R.id.btn_reg)
    public void onBtnRegClicked() {
        mPresenter.register();
    }

    @Override
    public String getPhone() {
        return mEtPhone.getText();
    }

    @Override
    public String getMSMCode() {
        return mEtCode.getText();
    }

    @Override
    public String getPwd() {
        return mEtPwd.getText();
    }

    @Override
    public void setBtnCodeEnable(boolean enabled) {
        mBtnCode.setEnabled(enabled);
    }

    @Override
    public void setBtnCodeText(String text) {
        mBtnCode.setText(text);
    }
}
