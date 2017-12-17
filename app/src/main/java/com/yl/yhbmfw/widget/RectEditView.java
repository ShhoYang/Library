package com.yl.yhbmfw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.EventTypeItem;

/**
 * @author Yang Shihao
 */

public class RectEditView extends LinearLayout {

    private View mView;
    private TextView mTvKey;
    private TextView mTvErrorReason;
    private EditText mEtValue;
    private String mKey = "";
    private String mHint = "";
    private Boolean mEnableEdit = true;

    private EventTypeItem.EventMaterial mEventMaterial;

    public RectEditView(Context context) {
        super(context);
        init(context, null);
    }

    public RectEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RectEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectEditView);
            mKey = typedArray.getString(R.styleable.RectEditView_key);
            mHint = typedArray.getString(R.styleable.RectEditView_value_hint);
            mEnableEdit = typedArray.getBoolean(R.styleable.RectEditView_enable_edit, true);
        }
        mView = LayoutInflater.from(context).inflate(R.layout.rect_edit_view, this, true);
        mTvKey = (TextView) mView.findViewById(R.id.tv_key);
        mTvErrorReason = (TextView) mView.findViewById(R.id.tv_error_reason);
        mEtValue = (EditText) mView.findViewById(R.id.et_value);
        mTvKey.setText(mKey);
        if (!TextUtils.isEmpty(mHint)) {
            mEtValue.setHint(mHint);
        }
        mEtValue.setEnabled(mEnableEdit);

        mTvErrorReason.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEventMaterial.getModify())) {
                    mEtValue.setText(mEventMaterial.getModify());
                }
            }
        });
    }

    public void setRequiredKeyText(String s) {
        mKey = s;
        String html = "<font color='#FF0000'>*</font>" + mKey;
        mTvKey.setText(Html.fromHtml(html));
    }

    public void setRequiredKeyText(String s, String desc) {
        mKey = s;
        String html = "<font color='#FF0000'>*</font>" + mKey;
        if (!TextUtils.isEmpty(desc)) {
            html = html + "<font color='#303F9F'>(" + desc + ")</font>";
        }
        mTvKey.setText(Html.fromHtml(html));
    }

    public void setKeyText(String s) {
        mKey = s;
        mTvKey.setText(mKey);
    }

    public void setKeyText(String s, String desc) {
        mKey = s;
        if (TextUtils.isEmpty(desc)) {
            mTvKey.setText(mKey);
        } else {
            mTvKey.setText(Html.fromHtml(s + "<font color='#303F9F'>(" + desc + ")</font>"));
        }
    }

    public String getKeyText() {
        return mKey;
    }

    public void setValueText(String s) {
        if (!TextUtils.isEmpty(s)) {
            mEtValue.setText(s);
            mEtValue.setSelection(mEtValue.length());
        }
    }

    public String getValue() {
        return mEtValue.getText().toString();
    }

    public void setEnableEdit(boolean enableEdit) {
        mEnableEdit = enableEdit;
        mEtValue.setEnabled(mEnableEdit);
    }

    public void setEventMaterial(EventTypeItem.EventMaterial material) {
        mEventMaterial = material;
        if ("1".equals(mEventMaterial.getIsmust())) {
            setRequiredKeyText(mEventMaterial.getName(), mEventMaterial.getDesc());
        } else {
            setKeyText(mEventMaterial.getName(), mEventMaterial.getDesc());
        }
        if (TextUtils.isEmpty(material.getModify())) {
            mTvErrorReason.setVisibility(View.GONE);
        } else {
            mTvErrorReason.setVisibility(View.VISIBLE);
            mTvErrorReason.setText("建议: " + material.getModify());
        }
        if (TextUtils.isEmpty(material.getValue())) {
            mEtValue.setText(material.getDefault_value());
        } else {
            mEtValue.setText(material.getValue());
        }

    }

    public EventTypeItem.EventMaterial getEventMaterial() {
        return mEventMaterial;
    }


    public boolean isPass() {
        if ("1".equals(mEventMaterial.getIsmust())) {
            return !TextUtils.isEmpty(getValue());
        } else {
            return true;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mEnableEdit) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
