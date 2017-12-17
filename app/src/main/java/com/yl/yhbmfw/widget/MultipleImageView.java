package com.yl.yhbmfw.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.library.ui.PreviewImageActivity;
import com.yl.library.utils.ImageManager;
import com.yl.library.utils.PictureSelectorUtils;
import com.yl.library.widget.SquareRelativityLayout;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.ImageAdapter;
import com.yl.yhbmfw.bean.EventTypeItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Shihao
 */

public class MultipleImageView extends LinearLayout implements ImageAdapter.OnAddImageListener {

    private Context mContext;
    private View mView;
    private TextView mTvKey;
    private TextView mTvErrorReason;
    private ImageView mIvSample;
    private TextView mTvDesc;
    private SquareRelativityLayout mRlSample;
    private RecyclerView mRecyclerView;
    private String mKey = "";

    private List<EventTypeItem.ImageItem> mImageList = new ArrayList<>();
    private EventTypeItem.EventMaterial mEventMaterial;
    private ImageAdapter mAddImageAdapter;
    private int mRequestCode; //整个列表用一个请求码
    private int position;//记录选择图片的
    //示例图片
    private String[] images = null;
    private String[] descs = null;

    public MultipleImageView(Context context) {
        super(context);
        init(context, null);
    }

    public MultipleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MultipleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.add_image_view, this, true);
        mTvKey = mView.findViewById(R.id.tv_key);
        mTvErrorReason = mView.findViewById(R.id.tv_error_reason);
        mIvSample = mView.findViewById(R.id.iv_sample);
        mTvDesc = mView.findViewById(R.id.tv_desc);
        mRlSample = mView.findViewById(R.id.rl_sample);
        mRecyclerView = mView.findViewById(R.id.rv_image);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        mAddImageAdapter = new ImageAdapter(mImageList);
        mAddImageAdapter.setOnAddImageListener(this);
        mRecyclerView.setAdapter(mAddImageAdapter);
    }

    public void setRequiredKeyText(String s) {
        mKey = s;
        String html = "<font color='#FF0000'>*</font>" + mKey;
        mTvKey.setText(Html.fromHtml(html));
    }

    public void setRequiredKeyText(String s, String desc) {
        mKey = s;
        String html = "<font color='#FF0000'>*</font>" + mKey;
        if (!TextUtils.isEmpty(desc)) {
            html = html + "<font color='#303F9F'>(" + desc + ")</font>";
        }
        mTvKey.setText(Html.fromHtml(html));
    }

    public void setKeyText(String s) {
        mKey = s;
        mTvKey.setText(mKey);
    }

    public void setKeyText(String s, String desc) {
        mKey = s;
        if (TextUtils.isEmpty(desc)) {
            mTvKey.setText(mKey);
        } else {
            mTvKey.setText(Html.fromHtml(s + "<font color='#303F9F'>(" + desc + ")</font>"));
        }
    }


    public String getKeyText() {
        return mKey;
    }

    public List<EventTypeItem.ImageItem> getImageList() {
        return mImageList;
    }

    public void setEventMaterial(final EventTypeItem.EventMaterial material) {
        mAddImageAdapter.setMax(material.getImageCount());
        mEventMaterial = material;
        //是否必填
        if ("0".equals(mEventMaterial.getPicsmust())) {
            setRequiredKeyText(mEventMaterial.getName(), mEventMaterial.getDesc());
        } else {
            setKeyText(mEventMaterial.getName(), mEventMaterial.getDesc());
        }
        //错误原因
        if (TextUtils.isEmpty(material.getModify())) {
            mTvErrorReason.setVisibility(View.GONE);
        } else {
            mTvErrorReason.setVisibility(View.VISIBLE);
            mTvErrorReason.setText("意见: " + material.getModify());
        }
        //该项说明
        mTvDesc.setText(material.getEgdesc());
        //图例

        if (!TextUtils.isEmpty(material.getEgpath())) {
            images = material.getEgpath().split(",");
            for (int i = 0; i < images.length; i++) {
                images[i] = Constant.getBaseUrl() + images[i];
            }
            ImageManager.getInstance().loadImage(mContext, images[0], mIvSample);
        }
        if (!TextUtils.isEmpty(material.getEgdesc())) {
            descs = material.getEgdesc().split(",");
            mTvDesc.setText(descs[0]);
        }
        mRlSample.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PreviewImageActivity.start(mContext, Arrays.asList(images), Arrays.asList(descs), 0);
            }
        });
        //素材
        mImageList.clear();
        mImageList.addAll(mEventMaterial.getImageList());
        mAddImageAdapter.notifyDataSetChanged();
    }

    public EventTypeItem.EventMaterial getEventMaterial() {
        return mEventMaterial;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public void setRequestCode(int requestCode) {
        mRequestCode = requestCode;
    }


    public void setImage(Intent data) {
        //替换
        if (position < mImageList.size()) {
            mImageList.get(position).addImage(data);
        } else {
            //点击的是加号,添加
            EventTypeItem.ImageItem imageItem = new EventTypeItem.ImageItem();
            imageItem.addImage(data);
            mImageList.add(imageItem);
        }
        mAddImageAdapter.notifyDataSetChanged();
    }

    public boolean isPass() {
        if ("0".equals(mEventMaterial.getPicsmust())) {
            for (EventTypeItem.ImageItem item : mImageList) {
                if (item.hasImage()) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void addImage(int position) {
        this.position = position;
        PictureSelectorUtils.getImageSingleOption((Activity) mContext, mRequestCode);
    }
}
