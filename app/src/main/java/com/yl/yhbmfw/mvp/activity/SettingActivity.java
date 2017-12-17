package com.yl.yhbmfw.mvp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.yl.library.base.activity.BaseActivity;
import com.yl.library.utils.AppUtils;
import com.yl.library.utils.PermissionsUtils;
import com.yl.library.widget.TextViewGroup;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.mvp.contract.SettingContract;
import com.yl.yhbmfw.mvp.presenter.SettingPresenter;
import com.yl.yhbmfw.service.DownApkService;
import com.yl.yhbmfw.service.MyConn;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity<SettingContract.Presenter>
        implements SettingContract.View, PermissionsUtils.OnPermissionListener {

    @BindView(R.id.tv_change_pwd)
    TextViewGroup mTvChangePwd;
    @BindView(R.id.tv_clear_cache)
    TextViewGroup mTvClearCache;
    @BindView(R.id.tv_version)
    TextViewGroup mTvVersion;
    @BindView(R.id.btn_exit)
    Button mBtnExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initMVP() {
        mPresenter = new SettingPresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("设置");
        mTvVersion.setTextRight("当前版本" + AppUtils.getVersionName(this));
    }

    @Override
    protected void initData() {
        mPresenter.getCacheSize();
        if (App.getInstance().getConfig().isLogin()) {
            mTvChangePwd.setVisibility(View.VISIBLE);
            mBtnExit.setVisibility(View.VISIBLE);
        } else {
            mTvChangePwd.setVisibility(View.GONE);
            mBtnExit.setVisibility(View.GONE);

        }
    }

    @OnClick(R.id.tv_change_pwd)
    public void onTvChangePwdClicked() {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    @OnClick(R.id.tv_clear_cache)
    public void onTvClearCacheClicked() {
        mPresenter.clearCache();
    }

    @OnClick(R.id.tv_version)
    public void onTvVersionClicked() {
        mPresenter.checkVersion();
    }

    @OnClick(R.id.btn_exit)
    public void onBtnExitClicked() {
        mPresenter.signOut();
    }

    @Override
    public void setCacheSize(String text) {
        mTvClearCache.setTextRight(text);
    }

    @Override
    public void showUpdateDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本");
        builder.setMessage(msg);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PermissionsUtils.getInstance().checkSD(SettingActivity.this, SettingActivity.this);
            }
        });
        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void granted() {
        Intent intent = new Intent(this, DownApkService.class);
        intent.putExtra(Constant.KEY_STRING_1, mPresenter.getDownUrl());
        bindService(intent, new MyConn(this), BIND_AUTO_CREATE);
    }

    @Override
    public void noGranted() {
        toast("未获取读写SD卡权限,不能下载");
    }
}
