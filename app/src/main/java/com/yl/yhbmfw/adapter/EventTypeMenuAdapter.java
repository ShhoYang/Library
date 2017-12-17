package com.yl.yhbmfw.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.Menu;
import com.yl.yhbmfw.mvp.activity.EventTypeItemListActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * @author Yang Shihao
 */

public class EventTypeMenuAdapter extends CommonAdapter<Menu> {

    public EventTypeMenuAdapter(Context context, int layoutId, List<Menu> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final Menu menu, final int position) {
        holder.setImageResource(R.id.iv_icon, menu.getIconId())
                .setText(R.id.tv_text, menu.getText())
                .getConvertView()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, EventTypeItemListActivity.class);
                        intent.putExtra(Constant.KEY_STRING_1, menu.getText());
                        mContext.startActivity(intent);
                    }
                });
    }
}
