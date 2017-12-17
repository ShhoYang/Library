package com.yl.yhbmfw.widget;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yang Shihao
 */

public class CheckboxView extends LinearLayout {

    private LayoutInflater mLayoutInflater;
    private View mView;
    private TextView mTvKey;
    private TextView mTvErrorReason;
    private TagFlowLayout mFlowLayout;
    private String mKey = "";
    private String[] mTextArr;

    private EventTypeItem.EventMaterial mEventMaterial;

    public CheckboxView(Context context) {
        super(context);
        init(context, null);
    }

    public CheckboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CheckboxView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mLayoutInflater = LayoutInflater.from(context);
        mView = mLayoutInflater.inflate(R.layout.checkbox_view, this, true);
        mTvKey = (TextView) mView.findViewById(R.id.tv_key);
        mTvErrorReason = (TextView) mView.findViewById(R.id.tv_error_reason);
        mFlowLayout = (TagFlowLayout) mView.findViewById(R.id.flow);
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
            html = html + "<font color='#303F9F'>" + desc + "</font>";
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
            mTvKey.setText(Html.fromHtml(s + "<font color='#303F9F'>" + desc + "</font>"));
        }
    }

    public String getKeyText() {
        return mKey;
    }

    public void setEventMaterial(EventTypeItem.EventMaterial material) {
        mEventMaterial = material;
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
            mTvErrorReason.setText("建议: " + material.getModify());
        }
        setCheckBoxOption(material);
    }

    private void setCheckBoxOption(EventTypeItem.EventMaterial material) {
        //选项
        mTextArr = mEventMaterial.getValue().split(",");
        final TagAdapter<String> tagAdapter = new TagAdapter<String>(mTextArr) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) mLayoutInflater.inflate(R.layout.checkbox_item, mFlowLayout, false);
                textView.setText(s);
                return textView;
            }
        };
        mFlowLayout.setAdapter(tagAdapter);
        String defValue = material.getDefault_value();
        if (TextUtils.isEmpty(defValue)) {
            //默认选中第一项
            tagAdapter.setSelectedList(0);
        } else {
            //保存选中过的索引
            Set<Integer> selectedSet = new HashSet<>();
            //选中的选项
            String[] arr = defValue.split(",");
            //将选项转换成集合
            List<String> list = Arrays.asList(arr);
            //遍历选项
            int size = mTextArr.length;
            for (int i = 0; i < size; i++) {
                if (list.contains(mTextArr[i])) {
                    selectedSet.add(i);
                }
            }
            if (selectedSet.size() == 0) {
                //默认选中第一项
                tagAdapter.setSelectedList(0);
            } else {
                tagAdapter.setSelectedList(selectedSet);
            }
        }
    }

    public EventTypeItem.EventMaterial getEventMaterial() {
        return mEventMaterial;
    }

    public void setMaxSelectCount(int count) {
        mFlowLayout.setMaxSelectCount(count);
    }

    public String getValue() {
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i : mFlowLayout.getSelectedList()) {
            stringBuffer.append(mTextArr[i]).append(",");
        }
        String value = stringBuffer.toString();
        if (TextUtils.isEmpty(value)) {
            return "";
        } else {
            return stringBuffer.substring(0, stringBuffer.length() - 1);
        }
    }

    public boolean isPass() {
        if ("1".equals(mEventMaterial.getIsmust())) {
            return mFlowLayout.getSelectedList().size() != 0;
        } else {
            return true;
        }
    }
}
