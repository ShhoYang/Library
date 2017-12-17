package com.yl.yhbmfw.widget;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.permissions.RxPermissions;
import com.yl.library.utils.T;
import com.yl.yhbmfw.R;

import io.reactivex.functions.Consumer;

/**
 * @author Yang Shihao
 */

public class CallPhoneDialog extends Dialog implements View.OnClickListener {

    private TextView mTvConsult;
    private TextView mTvComplaint;
    private RelativeLayout mRlConsult;
    private RelativeLayout mRlComplaint;
    private Activity mActivity;

    private String mConsulPhoneNum;
    private String mComplaintPhoneNum;


    public CallPhoneDialog(Activity activity) {
        super(activity, R.style.BottomDialog);
        mActivity = activity;
        createDialog();
    }

    private void createDialog() {
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialogAnimation);

        setContentView(getView());
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        setCancelable(false);
    }

    private View getView() {
        View view = View.inflate(mActivity, R.layout.dialog_call_phone, null);
        mTvConsult = view.findViewById(R.id.tv_consult);
        mTvComplaint = view.findViewById(R.id.tv_complaint);
        mRlConsult = view.findViewById(R.id.rl_consult);
        mRlComplaint = view.findViewById(R.id.rl_complaint);
        view.findViewById(R.id.iv_consult).setOnClickListener(this);
        view.findViewById(R.id.iv_complaint).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        return view;
    }

    public void setPhoneNum(String phoneNum1, String phoneNum2) {
        mConsulPhoneNum = phoneNum1;
        if (TextUtils.isEmpty(phoneNum1)) {
            mRlConsult.setVisibility(View.GONE);
        } else {
            mRlConsult.setVisibility(View.VISIBLE);
            mTvConsult.setText("咨询电话:" + mConsulPhoneNum);
        }

        mComplaintPhoneNum = phoneNum2;
        if (TextUtils.isEmpty(phoneNum2)) {
            mRlComplaint.setVisibility(View.GONE);
        } else {
            mRlComplaint.setVisibility(View.VISIBLE);
            mTvComplaint.setText("投诉电话:" + mComplaintPhoneNum);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.iv_consult:
                requestPermission(mConsulPhoneNum);
                break;
            case R.id.iv_complaint:
                requestPermission(mComplaintPhoneNum);
                break;
        }
    }

    private void requestPermission(final String phoneNum) {
        new RxPermissions(mActivity)
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            callPhone(phoneNum);
                        } else {
                            T.showLong(mActivity, "未获取拨打电话权限");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNum));
        mActivity.startActivity(intent);
    }
}

