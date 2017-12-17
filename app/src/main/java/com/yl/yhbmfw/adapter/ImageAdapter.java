package com.yl.yhbmfw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yl.library.ui.PreviewImageActivity;
import com.yl.library.utils.ImageManager;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.EventTypeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Yang Shihao
 * @date 2017/7/28
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context mContext;
    private OnAddImageListener mOnAddImageListener;
    private List<EventTypeItem.ImageItem> mDatas;
    private int mMax = 1;

    public ImageAdapter(List<EventTypeItem.ImageItem> datas) {
        mDatas = datas;
    }

    public void setMax(int max) {
        mMax = max;
    }

    public void setOnAddImageListener(OnAddImageListener onAddImageListener) {
        mOnAddImageListener = onAddImageListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position < mDatas.size()) {
            //加号之前
            final EventTypeItem.ImageItem imageItem = mDatas.get(position);
            String localPath = imageItem.getLocalPath();
            if (TextUtils.isEmpty(localPath)) {
                //网络图片
                ImageManager.getInstance().loadImage(mContext, mDatas.get(position).getRemotePath(), holder.mIvImage);
                holder.mIvUpdateImage.setVisibility(View.VISIBLE);
                holder.mIvDeleteImage.setVisibility(View.GONE);
                //网络图片可以更新
                holder.mIvUpdateImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnAddImageListener != null) {
                            mOnAddImageListener.addImage(position);
                        }
                    }
                });
            } else {
                //本地图片
                ImageManager.getInstance().loadImage(mContext, localPath, holder.mIvImage);
                holder.mIvUpdateImage.setVisibility(View.GONE);
                holder.mIvDeleteImage.setVisibility(View.VISIBLE);
                //本地图片可以删除
                holder.mIvDeleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(imageItem.getRemotePath())) {
                            mDatas.remove(position);
                        } else {
                            imageItem.setLocalPath(null);
                        }
                        notifyDataSetChanged();
                    }
                });
            }
            //点击预览
            holder.mIvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    previewImages(position);
                }
            });
        } else {
            //加号
            holder.mIvUpdateImage.setVisibility(View.GONE);
            holder.mIvDeleteImage.setVisibility(View.GONE);
            ImageManager.getInstance().loadImage(mContext, R.drawable.add_media, holder.mIvImage);
            holder.mIvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnAddImageListener != null) {
                        mOnAddImageListener.addImage(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int size = mDatas.size();
        if (size < mMax) {
            return size + 1;
        } else {
            return size;
        }
    }

    private void previewImages(int position) {
        ArrayList<String> imagePaths = new ArrayList<>();
        for (EventTypeItem.ImageItem item : mDatas) {
            imagePaths.add(item.getPath());
        }
        PreviewImageActivity.start(mContext, imagePaths, null, position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.iv_delete_image)
        ImageView mIvDeleteImage;
        @BindView(R.id.iv_update_image)
        ImageView mIvUpdateImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnAddImageListener {
        void addImage(int position);
    }
}
