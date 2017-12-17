package com.yl.yhbmfw.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.mvp.activity.EventConditionActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * @author Yang Shihao
 */

public class EventTypeItemAdapter extends CommonAdapter<EventTypeItem> {

    public EventTypeItemAdapter(Context context, int layoutId, List<EventTypeItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final EventTypeItem eventTypeItem, int position) {
        StringBuffer sb = new StringBuffer("");

        String departName = eventTypeItem.getDept_name();
        if (!TextUtils.isEmpty(departName)){
            sb.append("业务指导部门:   ");
            sb.append(departName);
        }

        holder.setText(R.id.tv_event_type_name, eventTypeItem.getName())
                .setText(R.id.tv_time_limit, sb.toString())
                .getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventConditionActivity.class);
                intent.putExtra(Constant.KEY_BEAN, eventTypeItem);
                mContext.startActivity(intent);
            }
        });
    }
}
