package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.yhbmfw.R;
import com.yl.yhbmfw.adapter.EventDetailMaterialAdapter;
import com.yl.library.base.activity.BaseActivity;
import com.yl.yhbmfw.mvp.contract.EventDetailsContract;
import com.yl.yhbmfw.mvp.presenter.EventDetailsPresenter;
import com.yl.yhbmfw.widget.StepView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Yang Shihao
 * 办事详情
 */
public class EventDetailsActivity extends BaseActivity<EventDetailsContract.Presenter>
        implements EventDetailsContract.View {

    @BindView(R.id.tv_event_name)
    TextView mTvEventName;

    @BindView(R.id.tv_event_serial)
    TextView mTvEventSerial;

    @BindView(R.id.tv_submit_time)
    TextView mTvSubmitTime;

    @BindView(R.id.tv_postal_name)
    TextView mTvPostalName;

    @BindView(R.id.tv_postal_num)
    TextView mTvPostalNum;

    @BindView(R.id.tv_access_type)
    TextView mTvAccessType;

    @BindView(R.id.step)
    StepView mStepView;

    @BindView(R.id.rv_material)
    RecyclerView mRvMaterial;

    @BindView(R.id.ll_material)
    LinearLayout mLlMaterial;


    private EventDetailMaterialAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_details;
    }

    @Override
    protected void initMVP() {
        mPresenter = new EventDetailsPresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("办事详情");
        setImageRight(R.drawable.main_white);
        mRvMaterial.setNestedScrollingEnabled(false);
        mRvMaterial.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new EventDetailMaterialAdapter(this, R.layout.item_event_details_material, mPresenter.getMaterialList());
        mRvMaterial.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mPresenter.getDetails(getIntent());
    }

    /**
     * 回到首页
     */
    @Override
    public void onImageViewRightClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void setEventInfo(String name, String submitTime, String serial) {
        mTvEventName.setText(name);
        mTvSubmitTime.setText(submitTime);
        mTvEventSerial.setText(serial);
    }

    @Override
    public void setPostalInfo(String postalName, String postalNum) {
        mTvAccessType.setVisibility(View.VISIBLE);
        mTvPostalName.setVisibility(View.VISIBLE);
        mTvPostalNum.setVisibility(View.VISIBLE);

        mTvAccessType.setText("获取方式: 邮寄");
        mTvPostalName.setText(postalName);
        mTvPostalNum.setText(postalNum);
    }

    @Override
    public void setEventProgress(int cur, int total, String[] contents) {
        mStepView.setProgress(cur, total, contents, null);
        mStepView.requestLayout();
    }

    @Override
    public void updateMaterial() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void noMaterial() {
        mRvMaterial.setVisibility(View.GONE);
        mLlMaterial.setVisibility(View.GONE);
    }

    @Override
    public void noPostalInfo(String s) {
        mTvAccessType.setVisibility(View.VISIBLE);
        mTvPostalName.setVisibility(View.GONE);
        mTvPostalNum.setVisibility(View.GONE);
        mTvAccessType.setText(s);
    }

    @Override
    public void noFinished() {
        mTvPostalName.setVisibility(View.GONE);
        mTvPostalNum.setVisibility(View.GONE);
        mTvAccessType.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_event_name)
    public void onTvEventNameClicked() {
        mPresenter.searchByEventName(mTvEventName.getText().toString());
    }
}
