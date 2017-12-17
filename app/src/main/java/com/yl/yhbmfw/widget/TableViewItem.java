package com.yl.yhbmfw.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.library.widget.EditTextGroup;
import com.yl.yhbmfw.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang Shihao
 */

public class TableViewItem extends LinearLayout {

    private Context mContext;
    private View mView;
    private TextView mTvNum;
    private LinearLayout mLlContent;
    private List<EditTextGroup> mViewList;

    public TableViewItem(Context context) {
        this(context, null);
    }

    public TableViewItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableViewItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.table_view_item, this, true);
        mTvNum = mView.findViewById(R.id.tv_num);
        mLlContent = mView.findViewById(R.id.ll_content);
    }

    public void createTable(int num, String options, String def) {
        if (TextUtils.isEmpty(options)) {
            return;
        }
        mViewList = new ArrayList<>();
        String[] optionArr = TextUtils.split(options, ",");
        int size = optionArr.length;

        if (size == 0) {
            return;
        }

        String[] defArr = null;
        if (!TextUtils.isEmpty(def)) {
            defArr = TextUtils.split(def, ",");
        }

        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                mViewList.add(createEditText(R.drawable.shape_border_l_b, i, optionArr, defArr));
            } else {
                mViewList.add(createEditText(R.drawable.shape_border_l, i, optionArr, defArr));
            }
        }
        mTvNum.setText("" + num);
    }

    public String getValue() {
        if (mViewList == null || mViewList.size() == 0) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            for (EditTextGroup v : mViewList) {
                sb.append(v.getTextRight()).append(",");
            }
            return sb.substring(0, sb.length() - 1);
        }
    }

    EditTextGroup mEditTextGroup;

    private EditTextGroup createEditText(@DrawableRes int bg, int index, String[] optionArr, String[] defArr) {
        mEditTextGroup = new EditTextGroup(mContext);
        mEditTextGroup.setBackgroundResource(bg);
        mEditTextGroup.setTextLeft(optionArr[index]);
        if (defArr != null && defArr.length > index) {
            mEditTextGroup.setTextRight(defArr[index]);
        }
        mLlContent.addView(mEditTextGroup);
        return mEditTextGroup;
    }
}
