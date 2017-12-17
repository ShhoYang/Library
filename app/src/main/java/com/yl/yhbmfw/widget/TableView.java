package com.yl.yhbmfw.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.EventTypeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang Shihao
 */

public class TableView extends LinearLayout {

    private Context mContext;
    private View mView;
    private TextView mTvTableTitle;
    private TextView mTvErrorReason;
    private LinearLayout mLlTableContent;
    private EventTypeItem.EventMaterial mEventMaterial;
    private List<TableViewItem> mViewList;

    private String mTableName = "";

    public TableView(Context context) {
        this(context, null);
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.table_view, this, true);
        mTvTableTitle = (TextView) mView.findViewById(R.id.tv_table_title);
        mTvErrorReason = (TextView) mView.findViewById(R.id.tv_error_reason);
        mLlTableContent = (LinearLayout) mView.findViewById(R.id.tv_table_content);
    }

    public void setRequiredKeyText(String s) {
        mTableName = s;
        String html = "<font color='#FF0000'>*</font>" + mTableName;
        mTvTableTitle.setText(Html.fromHtml(html));
    }

    public void setRequiredKeyText(String s, String desc) {
        mTableName = s;
        String html = "<font color='#FF0000'>*</font>" + mTableName;
        if (!TextUtils.isEmpty(desc)) {
            html = html + "<font color='#303F9F'>(" + desc + ")</font>";
        }
        mTvTableTitle.setText(Html.fromHtml(html));
    }

    public void setKeyText(String s) {
        mTableName = s;
        mTvTableTitle.setText(mTableName);
    }

    public void setKeyText(String s, String desc) {
        mTableName = s;
        if (TextUtils.isEmpty(desc)) {
            mTvTableTitle.setText(mTableName);
        } else {
            mTvTableTitle.setText(Html.fromHtml(s + "<font color='#303F9F'>(" + desc + ")</font>"));
        }
    }

    public void setEventMaterial(final EventTypeItem.EventMaterial material) {
        mEventMaterial = material;
        String options = material.getValue();
        if (TextUtils.isEmpty(options)) {
            return;
        }
        String count = mEventMaterial.getCount();
        if (TextUtils.isEmpty(count)) {
            count = mEventMaterial.getDefault_value();
        }
        int countI = 1;
        if (!TextUtils.isEmpty(count) && TextUtils.isDigitsOnly(count)) {
            countI = Integer.parseInt(count);
        }

        String defaultValue = mEventMaterial.getDefault_value();
        String[] defArr = null;
        if (!TextUtils.isEmpty(mEventMaterial.getCount()) && !TextUtils.isEmpty(defaultValue)) {
            defArr = TextUtils.split(defaultValue, "#");
        }
        mViewList = new ArrayList<>();

        for (int i = 0; i < countI; i++) {
            if (i == 0) {
                mViewList.add(createTableViewItem(R.drawable.shape_border, i, options, defArr));
            } else {
                mViewList.add(createTableViewItem(R.drawable.shape_border_l_b_r, i, options, defArr));
            }
        }

        //是否必填
        if ("1".equals(mEventMaterial.getIsmust())) {
            setRequiredKeyText(mEventMaterial.getName(), mEventMaterial.getDesc());
        } else {
            setKeyText(mEventMaterial.getName(), mEventMaterial.getDesc());
        }

        //错误原因
        if (TextUtils.isEmpty(material.getModify())) {
            mTvErrorReason.setVisibility(View.GONE);
        } else {
            mTvErrorReason.setVisibility(View.VISIBLE);
            mTvErrorReason.setText("意见: " + material.getModify());
        }
    }

    public EventTypeItem.EventMaterial getEventMaterial() {
        return mEventMaterial;
    }

    public String getValue() {
        if (mViewList == null || mViewList.size() == 0) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            for (TableViewItem v : mViewList) {
                sb.append(v.getValue()).append("#");
            }
            return sb.substring(0, sb.length() - 1);
        }
    }

    TableViewItem mTableViewItem;

    private TableViewItem createTableViewItem(@DrawableRes int bg, int num, String options, String[] defArr) {
        mTableViewItem = new TableViewItem(mContext);
        mTableViewItem.setBackgroundResource(bg);
        //判断默认值
        if (defArr != null && defArr.length > num) {
            mTableViewItem.createTable(num + 1, options, defArr[num]);
        } else {
            mTableViewItem.createTable(num + 1, options, null);
        }

        mLlTableContent.addView(mTableViewItem);
        return mTableViewItem;
    }
}
