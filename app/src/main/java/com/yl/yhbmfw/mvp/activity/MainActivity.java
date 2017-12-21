package com.yl.yhbmfw.mvp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;
import com.yl.library.base.activity.BaseActivity;
import com.yl.library.rx.RxBus;
import com.yl.library.utils.AppManager;
import com.yl.library.widget.TabView;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.event.EventSwitchFragment;
import com.yl.yhbmfw.event.EventUnbindService;
import com.yl.yhbmfw.mvp.contract.MainContract;
import com.yl.yhbmfw.mvp.fragment.FragmentMain;
import com.yl.yhbmfw.mvp.fragment.FragmentMessage;
import com.yl.yhbmfw.mvp.fragment.FragmentMy;
import com.yl.yhbmfw.mvp.presenter.MainPresenter;
import com.yl.yhbmfw.service.DownApkService;
import com.yl.yhbmfw.service.MyConn;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * @author Yang Shihao
 */
public class MainActivity extends BaseActivity<MainContract.Presenter> implements
        MainContract.View, TabView.TabClickListener {

    @BindView(R.id.tab_main)
    TabView mTabMain;

    @BindView(R.id.tab_message)
    TabView mTabMessage;

    @BindView(R.id.tab_my)
    TabView mTabMy;

    private Disposable mDownApkDisposable;
    private FragmentManager mFragmentManager;
    private TabView mCurrentTab;

    private boolean mExit = false;

    @Override
    protected void onStart() {
        super.onStart();
        if (App.getInstance().mToMessage) {
            showFragment(mTabMessage);
            mPresenter.getUnreadMsgCount();
            App.getInstance().mToMessage = false;
        }
    }

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initMVP() {
        mPresenter = new MainPresenter(this);
    }

    @Override
    protected void initView() {
        mTabMain.setFragment(new FragmentMain()).setTabClickListener(this);
        mTabMessage.setFragment(new FragmentMessage()).setTabClickListener(this);
        mTabMy.setFragment(new FragmentMy()).setTabClickListener(this);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.fl_container, mTabMain.getFragment()).commit();
        mCurrentTab = mTabMain;
    }

    @Override
    protected void initData() {
        //请求权限
        new RxPermissions(this)
                .requestEach(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@NonNull Permission permission) throws Exception {
                        if (permission.granted && permission.name.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            mPresenter.deleteFile();
                            mPresenter.checkVersion();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
        //注册EventBus
        RxBus.getInstance().register(EventSwitchFragment.class).subscribe(new Consumer<EventSwitchFragment>() {
            @Override
            public void accept(@NonNull EventSwitchFragment event) throws Exception {
                mPresenter.getUnreadMsgCount();
                showFragment(mTabMessage);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        });
        mPresenter.getUnreadMsgCount();
    }

    @Override
    public void onBackPressed() {
        if (mExit) {
            AppManager.getInstance().exit();
        } else {
            mExit = true;
            toast("再按返回键退出");
            Flowable.timer(3, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    mExit = false;
                }
            });
        }
    }

    @Override
    public void setUnreadMsgNum(String count) {
        mTabMessage.setCount(count);
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
                downApk();
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

    /**
     * 下载新版apk
     */
    private void downApk() {
        final MyConn myConn = new MyConn(MainActivity.this);
        Intent intent = new Intent(MainActivity.this, DownApkService.class);
        intent.putExtra(Constant.KEY_STRING_1, mPresenter.getDownUrl());
        bindService(intent, myConn, BIND_AUTO_CREATE);
        mDownApkDisposable = RxBus.getInstance().register(EventUnbindService.class).subscribe(new Consumer<EventUnbindService>() {
            @Override
            public void accept(EventUnbindService eventUnbindService) throws Exception {
                unbindService(myConn);
                mDownApkDisposable.dispose();
                mDownApkDisposable = null;
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        });
    }

    @Override
    public void tabClick(TabView tabView) {
        showFragment(tabView);
    }

    private void showFragment(TabView indexTab) {
        if (indexTab == mTabMessage && !App.getInstance().getConfig().isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        if (indexTab == mCurrentTab) {
            return;
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.hide(mCurrentTab.getFragment());
        mCurrentTab.setSelected(false);
        Fragment showFragment = indexTab.getFragment();
        if (showFragment.isAdded()) {
            fragmentTransaction.show(showFragment);
        } else {
            fragmentTransaction.add(R.id.fl_container, showFragment).show(showFragment);
        }
        fragmentTransaction.commit();
        indexTab.setSelected(true);
        mCurrentTab = indexTab;
    }
}
