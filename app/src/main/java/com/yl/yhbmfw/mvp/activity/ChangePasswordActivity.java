package com.yl.yhbmfw.mvp.activity;

import com.yl.library.base.activity.BaseActivity;
import com.yl.library.widget.ClearEditViewSingle;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.mvp.contract.ChangePasswordContract;
import com.yl.yhbmfw.mvp.presenter.ChangePasswordPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity<ChangePasswordContract.Presenter>
        implements ChangePasswordContract.View {

    @BindView(R.id.et_phone)
    ClearEditViewSingle mEtPhone;
    @BindView(R.id.et_old_pwd)
    ClearEditViewSingle mEtOldPwd;
    @BindView(R.id.et_new_psd)
    ClearEditViewSingle mEtNewPsd;
    @BindView(R.id.et_confirm_pwd)
    ClearEditViewSingle mEtConfirmPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initMVP() {
        mPresenter = new ChangePasswordPresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("修改密码");
        mEtPhone.setText(App.getInstance().getConfig().getPhone());
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {
        mPresenter.submit();
    }

    @Override
    public String getPhone() {
        return mEtPhone.getText();
    }

    @Override
    public String getOldPwd() {
        return mEtOldPwd.getText();
    }

    @Override
    public String getNewPwd() {
        return mEtNewPsd.getText();
    }

    @Override
    public String getConfirmPwd() {
        return mEtConfirmPwd.getText();
    }
}
