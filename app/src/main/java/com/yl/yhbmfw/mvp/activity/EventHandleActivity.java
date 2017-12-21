package com.yl.yhbmfw.mvp.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;
import com.yl.library.base.activity.BaseActivity;
import com.yl.yhbmfw.bean.RecAddress;
import com.yl.yhbmfw.event.EventRefresh;
import com.yl.yhbmfw.mvp.contract.EventHandleContract;
import com.yl.yhbmfw.mvp.presenter.EventHandlePresenter;
import com.yl.library.rx.RxBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 办理事件
 */
public class EventHandleActivity extends BaseActivity<EventHandleContract.Presenter>
        implements EventHandleContract.View, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.scroll)
    ScrollView mScrollview;

    //表单跟布局，往里面动态添加控件
    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;
    //提示
    @BindView(R.id.tv_remind)
    TextView mTvRemind;
    //提交
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    //获取方式
    @BindView(R.id.ll_obtain_mode)
    LinearLayout mLlObtainMode;
    @BindView(R.id.rg_obtain_mode)
    RadioGroup mRgObtainMode;
    //自提
    @BindView(R.id.rb_self)
    RadioButton mRbModeSelf;
    //快递
    @BindView(R.id.rb_express)
    RadioButton mRbModeExpress;

    //收货地址
    @BindView(R.id.rl_rec_address)
    RelativeLayout mRlRecAddress;

    //收货地址信息
    @BindView(R.id.rl_rec_address_info)
    RelativeLayout mRlRecAddressInfo;

    //姓名
    @BindView(R.id.tv_rec_name)
    TextView mTvRecName;
    //电话
    @BindView(R.id.tv_rec_phone)
    TextView mTvRecPhone;
    //地址
    @BindView(R.id.tv_rec_address)
    TextView mTvRecAddress;

    //添加收货地址
    @BindView(R.id.tv_set_rec_address)
    TextView mTvSetRecAddress;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getDefaultRecAddress();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_handle;
    }

    @Override
    protected void initMVP() {
        mPresenter = new EventHandlePresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("请填写");
        setImageRight(R.drawable.main_white);
        String remind = "1.带<font color='#FF0000'>*</font>为必填项<br/>2.所需材料你必须提交才能申报";
        mTvRemind.setText(Html.fromHtml(remind));
        mRgObtainMode.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        showDialog();
        mPresenter.createForm(getIntent());
        mPresenter.getDefaultRecAddress();
        mPresenter.getRxManager2Destroy().add(RxBus.getInstance().register(EventRefresh.class).subscribe(new Consumer<EventRefresh>() {
            @Override
            public void accept(@NonNull EventRefresh eventRefresh) throws Exception {
                if (eventRefresh.isRefresh(EventRefresh.REFRESH_REC_ADDRESS)) {
                    mPresenter.getDefaultRecAddress();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        }));
    }

    /**
     * 回到首页
     */
    @Override
    public void onImageViewRightClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void setEventName(String s) {
        setTitle(s);
    }

    @Override
    public void addView(View view) {
        mLlRoot.addView(view);
    }

    @Override
    public void clear() {
        mBtnSubmit.setVisibility(View.GONE);
    }

    @Override
    public void showObtainMode(int visibility) {
        mLlObtainMode.setVisibility(visibility);
    }

    @Override
    public void showRlRecAddress(int visibility) {
        mRlRecAddress.setVisibility(visibility);
    }

    @Override
    public void showRecAddressInfo(int visibility) {
        mRlRecAddressInfo.setVisibility(visibility);
    }

    @Override
    public void showSetRecAddress(int visibility) {
        mTvSetRecAddress.setVisibility(visibility);
    }

    @Override
    public void setRecAddress(RecAddress recAddress) {
        mTvRecName.setText(recAddress.getName());
        mTvRecPhone.setText(recAddress.getTel());
        mTvRecAddress.setText(recAddress.getAddr());
    }

    @Override
    public boolean isSelf() {
        return mRbModeSelf.isChecked();
    }

    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {
        mPresenter.submit();
    }

    /**
     * 添加收货地址
     */
    @OnClick(R.id.tv_set_rec_address)
    public void onTvSerRecAddressClicked() {
        Intent intent = new Intent(this, AddReceiveAddressActivity.class);
        intent.putExtra(Constant.KEY_BOOLEAN_1, true);
        startActivityForResult(intent, 99);
    }

    /**
     * 选择收货地址
     */
    @OnClick(R.id.rl_rec_address_info)
    public void onRlRecAddressInfoClicked() {
        Intent intent = new Intent(this, ReceiveAddressActivity.class);
        intent.putExtra(Constant.KEY_BOOLEAN_1, true);
        startActivityForResult(intent, 99);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }

        if (requestCode == 99) {    //选择收货地址
            /*RecAddress recAddress = (RecAddress) data.getSerializableExtra(Constant.KEY_RESULT);
            if (recAddress != null) {
                setRecAddress(recAddress);
            }*/
        } else {    //添加照片
            mPresenter.addImage(requestCode, data);
        }
    }

    /**
     * 两个RadioButton
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.rb_self) {
            mRlRecAddress.setVisibility(View.INVISIBLE);
            mTvRecAddress.setVisibility(View.INVISIBLE);
        } else if (checkedId == R.id.rb_express) {
            mRlRecAddress.setVisibility(View.VISIBLE);
            mTvRecAddress.setVisibility(View.VISIBLE);
        }
    }
}
