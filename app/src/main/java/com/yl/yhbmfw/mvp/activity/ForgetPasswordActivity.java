package com.yl.yhbmfw.mvp.activity;

import android.widget.Button;

import com.yl.library.base.activity.BaseActivity;
import com.yl.library.widget.ClearEditViewSingle;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.mvp.contract.ForgetPasswordContract;
import com.yl.yhbmfw.mvp.presenter.ForgetPasswordPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordContract.Presenter>
        implements ForgetPasswordContract.View {

    @BindView(R.id.et_phone)
    ClearEditViewSingle mEtPhone;
    @BindView(R.id.et_code)
    ClearEditViewSingle mEtCode;
    @BindView(R.id.et_pwd)
    ClearEditViewSingle mEtPwd;
    @BindView(R.id.et_confirm_pwd)
    ClearEditViewSingle mEtConfirmPwd;
    @BindView(R.id.btn_get_code)
    Button mBtnGetCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initMVP() {
        mPresenter = new ForgetPasswordPresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("忘记密码");
    }

    @Override
    protected void initData() {

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
    public String getConfirmPwd() {
        return mEtConfirmPwd.getText();
    }

    @Override
    public void setBtnCodeEnable(boolean enabled) {
        mBtnGetCode.setEnabled(enabled);
    }

    @Override
    public void setBtnCodeText(String text) {
        mBtnGetCode.setText(text);
    }

    @OnClick(R.id.btn_get_code)
    public void onBtnGetCodeClicked() {
        mPresenter.getCode();
    }

    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {
        mPresenter.submit();
    }
}
