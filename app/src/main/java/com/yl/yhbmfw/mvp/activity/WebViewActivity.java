package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.library.base.activity.BaseActivity;
import com.yl.yhbmfw.bean.WebDetails;

import butterknife.BindView;

/**
 * @author Yang Shihao
 *         <p>
 *         详情页
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.web)
    WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initMVP() {

    }

    @Override
    protected void initView() {
        setTitle("详情");
        mWebView.setClickable(false);
        mWebView.setFocusable(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        WebSettings settings = mWebView.getSettings();
        settings.setDefaultFontSize(16);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBlockNetworkImage(false);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        WebDetails webDetails = (WebDetails) intent.getSerializableExtra(Constant.KEY_BEAN);
        if (webDetails == null) {
            return;
        }
        if (!TextUtils.isEmpty(webDetails.getTitle())) {
            setTitle(webDetails.getTitle());
        }
        if (!TextUtils.isEmpty(webDetails.getContent())) {
            mWebView.loadDataWithBaseURL(null, Constant.HTML_START + webDetails.getContent() + Constant.HTML_END, "text/html", "utf-8", null);
        }
    }
}
