package com.yl.yhbmfw.adapter;

import android.content.Context;
import android.view.View;

import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.RecAddress;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * @author Yang Shihao
 */

public class RecAddressAdapter extends CommonAdapter<RecAddress> {

    private OnItemEditClickListener mOnItemEditClickListener;

    public RecAddressAdapter(Context context, int layoutId, List<RecAddress> datas) {
        super(context, layoutId, datas);
    }

    public void setOnItemEditClickListener(OnItemEditClickListener onItemEditClickListener) {
        mOnItemEditClickListener = onItemEditClickListener;
    }

    @Override
    protected void convert(ViewHolder holder, final RecAddress recAddress, int position) {
        holder.setText(R.id.tv_name, recAddress.getName())
                .setText(R.id.tv_phone, recAddress.getTel())
                .setText(R.id.tv_addr, recAddress.getAddr())
                .setOnClickListener(R.id.ll_default, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!recAddress.isDefault() && mOnItemEditClickListener != null) {
                            mOnItemEditClickListener.setDefault(recAddress);
                        }
                    }
                })
                .setOnClickListener(R.id.ll_edit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemEditClickListener != null) {
                            mOnItemEditClickListener.edit(recAddress);
                        }
                    }
                })
                .setOnClickListener(R.id.ll_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemEditClickListener != null) {
                            mOnItemEditClickListener.delete(recAddress);
                        }
                    }
                });
        holder.getView(R.id.iv_default).setSelected(recAddress.isDefault());
        holder.getView(R.id.tv_default).setSelected(recAddress.isDefault());
        if (recAddress.isDefault()) {
            holder.setText(R.id.tv_default, "默认地址");
        } else {
            holder.setText(R.id.tv_default, "设为默认");
        }
    }

    public interface OnItemEditClickListener {

        void setDefault(RecAddress recAddress);

        void delete(RecAddress recAddress);

        void edit(RecAddress recAddress);
    }
}
