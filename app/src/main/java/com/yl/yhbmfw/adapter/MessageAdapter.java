package com.yl.yhbmfw.adapter;

import android.content.Context;
import android.view.View;

import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.MessageItem;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * @author Yang Shihao
 */

public class MessageAdapter extends CommonAdapter<MessageItem> {

    public MessageAdapter(Context context, int layoutId, List<MessageItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, final MessageItem messageItem, final int position) {
        holder.setText(R.id.tv_title, messageItem.getMsg_title())
                .setText(R.id.tv_content, messageItem.getMsg_content())
                .setText(R.id.tv_time, messageItem.getMsg_create_time())
                .setVisible(R.id.tv_read, messageItem.isUnread())
                .getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setVisible(R.id.tv_read, false);
                mOnItemClickListener.onItemClick(null, null, position);
            }
        });
    }
}
