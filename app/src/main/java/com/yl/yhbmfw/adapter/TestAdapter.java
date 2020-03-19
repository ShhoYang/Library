package com.yl.yhbmfw.adapter;

import android.content.Context;

import com.yl.yhbmfw.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author Yang Shihao
 * @date 2018/1/7
 */

public class TestAdapter extends CommonAdapter<String> {

    public TestAdapter(Context context,List<String> datas) {
        super(context, R.layout.item_test, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.et_test,s);
    }
}
