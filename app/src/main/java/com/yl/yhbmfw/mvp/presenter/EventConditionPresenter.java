package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Config;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.bean.AuthInfo;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.mvp.activity.AuthenticateActivity;
import com.yl.yhbmfw.mvp.activity.EventHandleActivity;
import com.yl.yhbmfw.mvp.activity.LoginActivity;
import com.yl.yhbmfw.mvp.contract.EventConditionContract;
import com.yl.yhbmfw.mvp.fragment.FragmentConditionInfo;
import com.yl.yhbmfw.mvp.fragment.FragmentConditionMaterial;
import com.yl.yhbmfw.mvp.fragment.FragmentConditionProcess;
import com.yl.yhbmfw.api.Api;
import com.yl.library.rx.RxSubscriber;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Yang Shihao
 */
public class EventConditionPresenter extends EventConditionContract.Presenter {

    private Config mConfig = App.getInstance().getConfig();
    private AuthInfo mAuthInfo;
    private EventTypeItem mEventTypeItem;

    public EventConditionPresenter(EventConditionContract.View view) {
        super(view);
    }

    @Override
    public void createFragment(Intent intent) {
        if (intent == null) {
            mView.handleVisibility(View.GONE);
            return;
        }
        mEventTypeItem = (EventTypeItem) intent.getSerializableExtra(Constant.KEY_BEAN);
        if (mEventTypeItem == null) {
            mView.handleVisibility(View.GONE);
            return;
        }

        boolean isPreview = intent.getBooleanExtra(Constant.KEY_BOOLEAN_1, false);
        if (isPreview) {
            mView.handleVisibility(View.GONE);
        } else {
            mView.handleVisibility(View.VISIBLE);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY_BEAN, mEventTypeItem);
        FragmentConditionInfo f1 = new FragmentConditionInfo();
        f1.setArguments(bundle);

        FragmentConditionMaterial f2 = new FragmentConditionMaterial();
        f2.setArguments(bundle);

        FragmentConditionProcess f3 = new FragmentConditionProcess();
        f3.setArguments(bundle);

        mView.setViewPagerData(new String[]{"基本信息", "申报材料", "办理流程"}, new Fragment[]{f1, f2, f3});
    }

    @Override
    public void getAuthInfo() {
        if (!mConfig.isLogin()) {
            return;
        }
        mRxManager.add(new RxSubscriber<AuthInfo>(Api.authenticateResult()) {
            @Override
            protected void _onNext(AuthInfo authInfo) {
                mAuthInfo = authInfo;
                mConfig.setAuthInfo(mAuthInfo);
            }
        });
    }

    /**
     * 点击了申报
     */
    @Override
    public void clickDeclare() {
        if (!mConfig.isLogin()) {
            mView.gotoActivity(new Intent(mContext, LoginActivity.class));
        } else if (mAuthInfo == null || mAuthInfo.authFail() || mAuthInfo.noSubmit()) {
            mView.gotoActivity(new Intent(mContext, AuthenticateActivity.class));
        } else if (mAuthInfo.authing()) {
            mView.toast("尚未认证通过");
        } else if (mAuthInfo.authPass()) {
            handle();
        }
    }


    @Override
    public void getPhoneNum() {
        if (mEventTypeItem == null) {
            return;
        }
        String consulPhoneNum = mEventTypeItem.getZixun();
        String complaintPhoneNum = mEventTypeItem.getTousu();
        if (TextUtils.isEmpty(consulPhoneNum) && TextUtils.isEmpty(complaintPhoneNum)) {
            mView.toast("暂无联系方式");
        } else {
            mView.setPhoneNum(consulPhoneNum, complaintPhoneNum);
        }
    }

    /**
     * 办理
     */
    private void handle() {
        Intent intent = new Intent(mContext, EventHandleActivity.class);
        intent.putExtra(Constant.KEY_BEAN, mEventTypeItem);
        mView.gotoActivity(intent);
    }

    /**
     * 老年证判断是否到60岁
     *
     * @param idCard 身份证
     * @return
     */
    private boolean isHandle(String idCard) {
        try {
            String birthday = idCard.substring(6, 14);
            int birthday_ = Integer.parseInt(birthday);
            String now = new SimpleDateFormat("yyyyMMdd").format(new Date());
            int now_ = Integer.parseInt(now);
            return (now_ - birthday_) >= 600000;
        } catch (RuntimeException e) {
            return true;
        }
    }
}
