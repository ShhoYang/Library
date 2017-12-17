package com.yl.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.library.R;

/**
 * @author Yang Shihao
 */
public class EditTextGroup extends RelativeLayout {

    private View mView;
    private TextView mTvLeft;
    private EditText mEtRight;
    private String mTextLeft = "";
    private String mTextRight = "";
    private String mHint = "";

    public EditTextGroup(Context context) {
        this(context, null);
    }

    public EditTextGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mView = LayoutInflater.from(context).inflate(R.layout.edit_text_group, this, true);
        mTvLeft = mView.findViewById(R.id.vg_tv_left);
        mEtRight = mView.findViewById(R.id.vg_et_right);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextGroup);
            mTextLeft = typedArray.getString(R.styleable.EditTextGroup_text_left);
            mTextRight = typedArray.getString(R.styleable.EditTextGroup_text_right);
            mHint = typedArray.getString(R.styleable.EditTextGroup_text_hint);
            typedArray.recycle();
        }

        mTvLeft.setText(mTextLeft);
        mEtRight.setText(mTextRight);
        if (!TextUtils.isEmpty(mHint)) {
            mEtRight.setHint(mHint);
        }
    }

    public void setTextLeft(String s) {
        mTvLeft.setText(s);
    }

    public String getTextLeft() {
        return mTvLeft.getText().toString();
    }

    public void setTextRight(String s) {
        mEtRight.setText(s);
    }

    public String getTextRight() {
        return mEtRight.getText().toString();
    }

    public EditText getEditText() {
        return mEtRight;
    }

    public void setEnableEdit(boolean enable) {
        mEtRight.setEnabled(enable);
    }
}
