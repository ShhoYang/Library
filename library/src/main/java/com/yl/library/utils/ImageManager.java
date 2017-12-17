package com.yl.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yl.library.R;

/**
 * @author Yang Shihao
 *         <p>
 *         Glide图片管理类
 */

public class ImageManager {

    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder);

    private RequestOptions requestCircleOptions = new RequestOptions()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(new CircleCrop());

    private RequestOptions requestRoundOptions = new RequestOptions()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(new RoundedCorners(20));

    public static ImageManager getInstance() {
        return ImageManager.Holder.INSTANCE;
    }

    public void loadImage(Context context, Object url, ImageView iv) {
        Glide.with(context).load(url).apply(requestOptions).into(iv);
    }

    public void loadImage(Context context, Object url, @IdRes int placeholderId, ImageView iv) {
         RequestOptions options = new RequestOptions()
                .placeholder(placeholderId)
                .error(placeholderId);
        Glide.with(context).load(url).apply(options).into(iv);
    }

    public void loadImageNoHolder(Context context, Object url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }

    /**
     * 加载圆形图片
     */
    public void loadCircleImage(Context context, Object url, ImageView iv) {
        Glide.with(context).load(url).apply(requestCircleOptions).into(iv);
    }

    /**
     * 加载圆角图片
     */
    public void loadRoundCornerImage(Context context, Object url, ImageView iv) {
        Glide.with(context).load(url).apply(requestRoundOptions).into(iv);
    }

    /**
     * 释放内存
     */
    public void clearMemory(Context context) {
        try {
            Glide.get(context).clearMemory();
        } catch (RuntimeException e) {

        }
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCache(final Context context) {
        try {
            Glide.get(context).clearDiskCache();
        } catch (RuntimeException e) {

        }
    }

    /**
     * 清除所有缓存
     */
    public void cleanAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }


    public static class Holder {
        public static final ImageManager INSTANCE = new ImageManager();
    }
}
