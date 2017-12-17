package com.yl.yhbmfw.mvp.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.library.base.fragment.BaseFragment;
import com.yl.library.rx.RxBus;
import com.yl.library.utils.ImageManager;
import com.yl.library.widget.TextViewGroup;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.AuthInfo;
import com.yl.yhbmfw.event.EventRefresh;
import com.yl.yhbmfw.mvp.activity.AboutActivity;
import com.yl.yhbmfw.mvp.activity.SettingActivity;
import com.yl.yhbmfw.mvp.contract.FgMyContract;
import com.yl.yhbmfw.mvp.presenter.FgMyPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * @author Yang Shihao
 *         <p>
 *         我的
 */
public class FragmentMy extends BaseFragment<FgMyContract.Presenter>
        implements FgMyContract.View {

    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.tv_phone)
    TextView mTvPhone;

    @BindView(R.id.iv_head)
    ImageView mIvHead;

    @BindView(R.id.iv_no_login)
    ImageView mIvNoLogin;

    @BindView(R.id.tv_auth)
    TextViewGroup mTvAuth;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initMVP() {
        mPresenter = new FgMyPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter.getUserInfo();
        mPresenter.getAuthInfo();
        mPresenter.getRxManager().add(RxBus.getInstance().register(EventRefresh.class).subscribe(new Consumer<EventRefresh>() {
            @Override
            public void accept(@NonNull EventRefresh eventRefresh) throws Exception {
                if (eventRefresh.isRefresh(EventRefresh.REFRESH_USER_INFO)) {
                    mPresenter.getUserInfo();
                    mPresenter.getAuthInfo();
                } else if (eventRefresh.isRefresh(EventRefresh.REFRESH_AUTH_INFO_NET)) {
                    mPresenter.getAuthInfo();
                } else if (eventRefresh.isRefresh(EventRefresh.REFRESH_AUTH_INFO)) {
                    AuthInfo authInfo = App.getInstance().getConfig().getAuthInfo();
                    mTvName.setText(authInfo.getName());
                    mTvAuth.setTextRight(authInfo.getStatus());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        }));
    }

    @OnClick(R.id.rl_profile)
    public void onRlProfileClicked() {
        mPresenter.clickProfile();
    }

    @OnClick(R.id.tv_my_event)
    public void onTvMyEventClicked() {
        mPresenter.clickMyEvent();
    }

    @OnClick(R.id.tv_auth)
    public void onTvAuthClicked() {
        mPresenter.clickAuth();
    }

    @OnClick(R.id.tv_rec_addr)
    public void onTvRecAddresClicked() {
        mPresenter.clickRecAddress();
    }

    @OnClick(R.id.tv_setting)
    public void onTvSettingClicked() {
        startActivity(new Intent(mActivity, SettingActivity.class));
    }

    @OnClick(R.id.tv_advice)
    public void onTvAdviceClicked() {
        toast("正在开发...");
    }

    @OnClick(R.id.tv_about)
    public void onVgAboutClicked() {
        startActivity(new Intent(mActivity, AboutActivity.class));
    }

    @Override
    public void setName(String s) {
        mTvName.setText(s);
    }

    @Override
    public void setPhone(String s) {
        mTvPhone.setText(s);
    }

    @Override
    public void setAuthStatus(String s) {
        mTvAuth.setTextRight(s);
    }

    @Override
    public void setHead(Object path) {
        ImageManager.getInstance().loadCircleImage(mActivity, path, mIvHead);
    }

    @Override
    public void setNoLoginShow(boolean show) {
        if (show) {
            mTvName.setVisibility(View.GONE);
            mTvPhone.setVisibility(View.GONE);
            mIvHead.setVisibility(View.GONE);
            mIvNoLogin.setVisibility(View.VISIBLE);
        } else {
            mTvName.setVisibility(View.VISIBLE);
            mTvPhone.setVisibility(View.VISIBLE);
            mIvHead.setVisibility(View.VISIBLE);
            mIvNoLogin.setVisibility(View.GONE);
        }
    }
}
