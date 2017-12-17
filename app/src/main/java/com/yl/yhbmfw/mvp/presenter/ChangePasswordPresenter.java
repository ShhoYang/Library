package com.yl.yhbmfw.mvp.presenter;


import android.text.TextUtils;

import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Config;
import com.yl.yhbmfw.mvp.contract.ChangePasswordContract;
import com.yl.yhbmfw.api.Api;
import com.yl.library.rx.RxSubscriber;
import com.yl.library.utils.RegexUtils;


/**
 * @author Yang Shihao
 */
public class ChangePasswordPresenter extends ChangePasswordContract.Presenter {

    private Config mConfig = App.getInstance().getConfig();

    public ChangePasswordPresenter(ChangePasswordContract.View view) {
        super(view);
    }

    @Override
    public void submit() {
        final String phone = mView.getPhone();
        String oldPwd = mView.getOldPwd();
        final String newPwd = mView.getNewPwd();
        String confirmPwd = mView.getConfirmPwd();
        if (TextUtils.isEmpty(phone)) {
            mView.toast("手机号不能为空");
            return;
        }

        if (!RegexUtils.isMobilePhoneNumber(phone)) {
            mView.toast("手机号格式错误");
            return;
        }

        if (TextUtils.isEmpty(oldPwd)) {
            mView.toast("原密码不能为空");
            return;
        }


        if (TextUtils.isEmpty(newPwd)) {
            mView.toast("新密码不能为空");
            return;
        }

        if (!RegexUtils.isMatchesPwd(newPwd)) {
            mView.toast("新密码格式错误");
            return;
        }

        if (oldPwd.equals(newPwd)) {
            mView.toast("新密码和原密码不能一样");
            return;
        }

        if (TextUtils.isEmpty(confirmPwd)) {
            mView.toast("确认密码不能为空");
            return;
        }

        if (!RegexUtils.isMatchesPwd(confirmPwd)) {
            mView.toast("确认密码格式错误");
            return;
        }

        if (!newPwd.equals(confirmPwd)) {
            mView.toast("新密码和确认密码不一致");
            return;
        }
        mView.showDialog("正在提交...");
        mRxManager.add(new RxSubscriber<String>(Api.changePassword(phone, oldPwd, newPwd)) {

            @Override
            protected void _onNext(String s) {
                mConfig.setPhone(phone);
                mConfig.setPassword(newPwd);
                login(phone,newPwd);
            }

            @Override
            protected void _onError(String code) {
                super._onError(code);
                mView.dismissDialog();
            }
        });
    }

    private void login(String phone,String pwd) {
        /*mView.showDialog("正在重新登录...");
        mRxManager.add(new RxSubscriber<User>(Api.login(phone, pwd, AppUtils.getIMEI(mContext, phone)),mView) {
            @Override
            protected void _onNext(User userHttpResult) {
                User user = userHttpResult;
                mConfig.setToken(user.getToken());
                mConfig.setLogin(true);
                mConfig.setUser(user);

                if (!TextUtils.isEmpty(user.getBind_regionname()) || user.isManager()) {
                    mView.gotoActivity(new Intent(mContext, MainActivity.class),true);
                } else {
                    Intent intent = new Intent(mContext, ProfileActivity.class);
                    intent.putExtra(Constant.KEY_BOOLEAN_1, true);
                    mView.gotoActivity(intent,true);
                }
                mView.toast("修改成功");
                mView.finishActivity();
            }

            @Override
            protected void _onError(String code) {
                super._onError(code);
                mView.gotoActivity(new Intent(mContext, LoginActivity.class),true);
            }
        });*/
    }
}
