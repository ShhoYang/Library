package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;

import com.yl.library.base.activity.BaseActivity;
import com.yl.library.utils.AppManager;
import com.yl.library.widget.ClearEditViewSingle;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.mvp.contract.LoginContract;
import com.yl.yhbmfw.mvp.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginContract.Presenter>
        implements LoginContract.View {

    @BindView(R.id.et_phone)
    ClearEditViewSingle mEtPhone;
    @BindView(R.id.et_pwd)
    ClearEditViewSingle mEtPwd;

    private boolean mIsCloseMainActivity = false;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAccount();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initMVP() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("登录");
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mIsCloseMainActivity = intent.getBooleanExtra(Constant.KEY_BOOLEAN_1, false);
            if (mIsCloseMainActivity) {
                AppManager.getInstance().finishAllActivityExceptAppoint(LoginActivity.class);
            }
        }
    }

    @OnClick(R.id.btn_login)
    public void onBtnLoginClicked() {
        mPresenter.login();
    }

    @OnClick(R.id.tv_register)
    public void onTvRegClicked() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public String getPhone() {
        return mEtPhone.getText();
    }

    @Override
    public String getPwd() {
        return mEtPwd.getText();
    }

    @Override
    public void setAccount(String phone, String pwd) {
        mEtPhone.setText(phone);
        mEtPwd.setText(pwd);
        mEtPhone.setFocusable(false);
        mEtPwd.setFocusable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onImageViewLeftClicked();
    }

    @Override
    protected void onImageViewLeftClicked() {
        if (mIsCloseMainActivity) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            super.onImageViewLeftClicked();
        }
    }
}
