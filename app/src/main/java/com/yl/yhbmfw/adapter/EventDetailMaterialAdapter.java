package com.yl.yhbmfw.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yl.library.ui.PreviewImageActivity;
import com.yl.library.utils.ImageManager;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.EventDetails;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Yang Shihao
 */

public class EventDetailMaterialAdapter extends CommonAdapter<EventDetails.Material> {

    public EventDetailMaterialAdapter(Context context, int layoutId, List<EventDetails.Material> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, EventDetails.Material material, final int position) {
        ImageManager.getInstance().loadImage(mContext, Constant.getBaseUrl() + material.getPic(), (ImageView) holder.getView(R.id.iv_image));
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imageList = new ArrayList<>();
                ArrayList<String> titleList = new ArrayList<>();
                for (EventDetails.Material m : mDatas) {
                    imageList.add(Constant.getBaseUrl() + m.getPic());
                    titleList.add(m.getName());
                }
                PreviewImageActivity.start(mContext, imageList, titleList, position);
            }
        });
    }
}
