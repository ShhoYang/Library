package com.yl.yhbmfw.adapter;

import android.content.Context;
import android.view.View;

import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.EventList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * @author Yang Shihao
 */

public class EventAdapter extends CommonAdapter<EventList.EventItem> {

    public EventAdapter(Context context, int layoutId, List<EventList.EventItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final EventList.EventItem eventItem, final int position) {
        holder.setText(R.id.tv_event_name, eventItem.getName())
                .setText(R.id.tv_event_status, "办理状态: " + eventItem.getStatus())
                .setText(R.id.tv_event_serial, "事件流水号: " + eventItem.getCode())
                .setText(R.id.tv_submit_time, "提交时间: " + eventItem.getAdd_time());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(null, null, position);
            }
        });
    }
}
