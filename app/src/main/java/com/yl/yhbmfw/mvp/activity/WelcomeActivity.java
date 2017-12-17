package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.yl.yhbmfw.R;
import com.yl.library.base.activity.BaseActivity;
import com.yl.yhbmfw.mvp.contract.WelcomeContract;
import com.yl.yhbmfw.mvp.presenter.WelcomePresenter;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity<WelcomeContract.Presenter>
        implements WelcomeContract.View, Animation.AnimationListener {

    @BindView(R.id.iv_welcome)
    ImageView mIvWelcome;

    @Override
    protected boolean fullScreen() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initMVP() {
        mPresenter = new WelcomePresenter(this);
    }

    @Override
    protected void initView() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f, 1.0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setAnimationListener(this);
        mIvWelcome.startAnimation(alphaAnimation);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
