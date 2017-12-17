package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;
import android.widget.TextView;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.library.base.activity.BaseActivity;

import butterknife.BindView;

public class MessageDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_details)
    TextView mTvDetails;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_details;
    }

    @Override
    protected void initMVP() {

    }

    @Override
    protected void initView() {
        setTitle("消息详情");
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        String s = intent.getStringExtra(Constant.KEY_STRING_1);
        mTvDetails.setText(s);
    }
}
