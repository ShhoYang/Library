package com.yl.yhbmfw.mvp.presenter;


import android.text.TextUtils;

import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Config;
import com.yl.yhbmfw.mvp.contract.RegisterContract;
import com.yl.yhbmfw.api.Api;
import com.yl.library.rx.HttpCode;
import com.yl.library.rx.RxSubscriber;
import com.yl.library.utils.AppUtils;
import com.yl.library.utils.RegexUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Yang Shihao
 */
public class RegisterPresenter extends RegisterContract.Presenter {

    private Disposable mTimer;
    private String mPhone;

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void getCode() {
        final String phone = mView.getPhone();
        if (TextUtils.isEmpty(phone)) {
            mView.toast("手机号不能为空");
            return;
        }
        if (!RegexUtils.isMobilePhoneNumber(phone)) {
            mView.toast("手机号格式错误");
            return;
        }
        mView.setBtnCodeEnable(false);
        startTimer();
        mView.showDialog("正在获取验证码...");
        mRxManager.add(new RxSubscriber<String>(Api.getSMSCode(phone, AppUtils.getIMEI(mContext, phone)), mView) {

            @Override
            protected void _onNext(String s) {
                mPhone = phone;
                mView.toast("验证码发送成功");
            }

            @Override
            protected void _onError(String code) {
                super._onError(code);
                closeTimer();
            }
        });
    }

    @Override
    public void register() {
        final String code = mView.getMSMCode();
        final String pwd = mView.getPwd();
        if (TextUtils.isEmpty(code)) {
            mView.toast("验证码不能为空");
            return;
        }
        if (!RegexUtils.isInteger(code, 6)) {
            mView.toast("验证码格式错误");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            mView.toast("密码不能为空");
            return;
        }

        if (!RegexUtils.isMatchesPwd(pwd)) {
            mView.toast("确认密码格式错误");
            return;
        }
        mView.showDialog("正在注册...");
        mRxManager.add(new RxSubscriber<String>(Api.register(code, pwd), mView) {

            @Override
            protected void _onNext(String s) {

            }

            @Override
            protected void _onError(String code) {
                if (code.equals(HttpCode.CODE_10002.getCode())) {
                    closeTimer();
                    Config config = App.getInstance().getConfig();
                    config.setPhone(mPhone);
                    config.setPassword(pwd);
                    mView.toast("注册成功");
                    mView.finishActivity();
                } else {
                    super._onError(code);
                }
            }
        });
    }

    private void startTimer() {
        mTimer = Flowable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mView.setBtnCodeText((60 - aLong) + "s");
                        if (60 - aLong <= 0) {
                            closeTimer();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        mRxManager.add(mTimer);
    }

    private void closeTimer() {
        mView.setBtnCodeEnable(true);
        mView.setBtnCodeText("获取验证码");
        if (mTimer != null && !mTimer.isDisposed()) {
            mTimer.dispose();
        }
    }
}
