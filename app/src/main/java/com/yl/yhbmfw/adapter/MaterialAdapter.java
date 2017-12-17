package com.yl.yhbmfw.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.yl.library.utils.ImageManager;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author Yang Shihao
 */

public class MaterialAdapter extends CommonAdapter<EventTypeItem.EventMaterial> {


    public MaterialAdapter(Context context, int layoutId, List<EventTypeItem.EventMaterial> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, EventTypeItem.EventMaterial eventMaterial, int position) {
        holder.setText(R.id.tv_name, (position + 1) + "." + eventMaterial.getName())
                .setText(R.id.tv_desc, eventMaterial.getEgdesc());
        ImageManager.getInstance().loadImage(mContext, Constant.getBaseUrl() + eventMaterial.getEgpath(), (ImageView) holder.getView(R.id.iv_image));
        //ImageManager.getInstance().loadImage(mContext, R.drawable.banner0, (ImageView) holder.getView(R.id.iv_image));
    }
}
