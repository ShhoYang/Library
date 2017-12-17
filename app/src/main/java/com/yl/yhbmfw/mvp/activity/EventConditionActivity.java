package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.yl.library.base.activity.BaseViewPagerActivity;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.mvp.contract.EventConditionContract;
import com.yl.yhbmfw.mvp.presenter.EventConditionPresenter;
import com.yl.yhbmfw.widget.CallPhoneDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 办理条件
 */
public class EventConditionActivity extends BaseViewPagerActivity<EventConditionContract.Presenter>
        implements EventConditionContract.View {

    //投诉咨询
    @BindView(R.id.btn_complaint)
    Button mBtnComplaint;
    //在线申报
    @BindView(R.id.btn_declare)
    Button mBtnDeclare;

    private CallPhoneDialog mCallPhoneDialog;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAuthInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_condition;
    }

    @Override
    protected void initMVP() {
        mPresenter = new EventConditionPresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("详情");
        setImageRight(R.drawable.main_white);
        setPageLimit(2);
    }

    @Override
    protected void initData() {
        mPresenter.createFragment(getIntent());
    }

    /**
     * 回到首页
     */
    @Override
    public void onImageViewRightClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * 申报
     */
    @OnClick(R.id.btn_declare)
    public void onBtnDeclareClicked() {
        mPresenter.clickDeclare();
    }

    /**
     * 咨询
     */
    @OnClick(R.id.btn_complaint)
    public void onBtnComplaintClicked() {
        mPresenter.getPhoneNum();
    }

    @Override
    public void handleVisibility(int visibility) {
        mBtnComplaint.setVisibility(visibility);
        mBtnDeclare.setVisibility(visibility);
    }

    @Override
    public String[] getTitles() {
        return null;
    }

    @Override
    public Fragment[] getFragments() {
        return null;
    }

    @Override
    public void setPhoneNum(String consulPhoneNum, String complaintPhoneNum) {
        if (mCallPhoneDialog == null) {
            mCallPhoneDialog = new CallPhoneDialog(this);
        }
        mCallPhoneDialog.setPhoneNum(consulPhoneNum, complaintPhoneNum);
        if (!mCallPhoneDialog.isShowing()) {
            mCallPhoneDialog.show();
        }
    }
}
