package com.yl.yhbmfw.mvp.activity;

import android.widget.TextView;

import com.yl.library.base.activity.BaseActivity;
import com.yl.library.utils.AppUtils;
import com.yl.yhbmfw.BuildConfig;
import com.yl.yhbmfw.R;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initMVP() {
    }

    @Override
    protected void initView() {
        setTitle("关于");
    }

    @Override
    protected void initData() {
        String debug = BuildConfig.API_DEBUG ? "测试版" : "正式版";
        mTvVersion.setText(debug + AppUtils.getVersionName(this));
    }
}
