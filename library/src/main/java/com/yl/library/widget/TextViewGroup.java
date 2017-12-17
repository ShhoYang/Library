package com.yl.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suke.widget.SwitchButton;
import com.yl.library.R;

/**
 * @author Yang Shihao
 */

public class TextViewGroup extends RelativeLayout {

    private TextView mTvLeft;
    private TextView mTvRight;
    private ImageView mIvLeft;
    private ImageView mIvArrow;
    private SwitchButton mBtnSwitch;

    private String mTextLeft = "";
    private String mTextRight = "";
    private int mTextColorLeft = 0;
    private int mTextColorRight = 0;
    private int mTextRightGravity = 5;
    private int mImgLeft = 0;
    private boolean mTextRightSingleLine = true;
    private boolean mArrowShow = true;
    private boolean mBtnShow = false;
    private boolean mBtnOnOff = true;
    private SwitchListener mSwitchListener;

    public TextViewGroup(Context context) {
        super(context);
        init(context, null);
    }

    public TextViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.text_view_group, this, true);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewGroup);
            mTextLeft = typedArray.getString(R.styleable.TextViewGroup_text_left);
            mTextRight = typedArray.getString(R.styleable.TextViewGroup_text_right);
            mTextColorLeft = typedArray.getColor(R.styleable.TextViewGroup_text_color_left, mTextColorLeft);
            mTextColorRight = typedArray.getColor(R.styleable.TextViewGroup_text_color_right, mTextColorRight);
            mTextRightGravity = typedArray.getInt(R.styleable.TextViewGroup_text_right_gravity, mTextRightGravity);
            mImgLeft = typedArray.getResourceId(R.styleable.TextViewGroup_img_left, mImgLeft);
            mTextRightSingleLine = typedArray.getBoolean(R.styleable.TextViewGroup_text_right_single_line, mTextRightSingleLine);
            mArrowShow = typedArray.getBoolean(R.styleable.TextViewGroup_arrow_show, mArrowShow);
            mBtnShow = typedArray.getBoolean(R.styleable.TextViewGroup_btn_show, mBtnShow);
            mBtnOnOff = typedArray.getBoolean(R.styleable.TextViewGroup_btn_switch, mBtnOnOff);
            typedArray.recycle();
        }
    }

    public void setTextLeft(String s) {
        mTvLeft.setText(s);
    }

    public String getTextLeft() {
        return mTvLeft.getText().toString();
    }

    public void setTextRight(String s) {
        if (s != null) {
            mTvRight.setText(s);
        }
    }

    public String getTextRight() {
        return mTvRight.getText().toString();
    }

    public TextView getTextViewRight() {
        return mTvRight;
    }

    public void setTextSizeLeft(int size) {
        mTvLeft.setTextSize(size);
    }

    public void setTextSizeRight(int size) {
        mTvRight.setTextSize(size);
    }

    public void setTextColorLeft(int color) {
        mTvLeft.setTextColor(color);
    }

    public void setTextColorRight(int color) {
        mTvRight.setTextColor(color);
    }

    public void setTextRightGravity(int gravity) {
        mTvRight.setGravity(gravity);
    }

    public void setArrowShow(boolean visibility) {
        viewVisibility(mIvArrow, visibility);
    }

    public void setBtnShow(boolean visibility) {
        viewVisibility(mBtnSwitch, visibility);
    }

    public void setBtnSwitch(boolean b) {
        mBtnSwitch.setChecked(b);
    }

    public boolean getBtnSwitch() {
        return mBtnSwitch.isChecked();
    }

    public void setOnSwitchListener(SwitchListener switchListener) {
        mSwitchListener = switchListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTvLeft = findViewById(R.id.vg_tv_left);
        mTvRight = findViewById(R.id.vg_tv_right);
        mIvLeft = findViewById(R.id.vg_iv_left);
        mIvArrow = findViewById(R.id.vg_iv_arrow);
        mBtnSwitch = findViewById(R.id.vg_btn_switch);

        mTvLeft.setText(mTextLeft);

        if (mTextColorLeft != 0) {
            mTvLeft.setTextColor(mTextColorLeft);
        }
        mTvRight.setText(mTextRight);
        if (mTextColorRight != 0) {
            mTvRight.setTextColor(mTextColorRight);
        }
        mTvRight.setGravity(mTextRightGravity);
        mTvRight.setSingleLine(mTextRightSingleLine);

        if (mImgLeft != 0) {
            mIvLeft.setVisibility(View.VISIBLE);
            mIvLeft.setImageResource(mImgLeft);
        }

        viewVisibility(mIvArrow, mArrowShow);
        viewVisibility(mBtnSwitch, mBtnShow);

    }

    private void viewVisibility(View view, boolean visibility) {
        if (visibility) {
            view.setVisibility(View.VISIBLE);
            if (view instanceof SwitchButton) {
                mBtnSwitch.setChecked(mBtnOnOff);
                mBtnSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        if (mSwitchListener != null) {
                            mSwitchListener.onOff(isChecked);
                        }
                    }
                });
            }
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public interface SwitchListener {

        void onOff(boolean b);
    }
}
