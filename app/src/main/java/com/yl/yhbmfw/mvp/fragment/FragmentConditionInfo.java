package com.yl.yhbmfw.mvp.fragment;

import android.widget.TextView;

import com.yl.library.base.fragment.BaseFragment;
import com.yl.library.widget.TextViewGroup;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.mvp.contract.FgEventConditionInfoContract;
import com.yl.yhbmfw.mvp.presenter.FgEventConditionInfoPresenter;

import butterknife.BindView;

/**
 * @author Yang Shihao
 *         <p>
 *         办理条件-基本信息
 */
public class FragmentConditionInfo extends BaseFragment<FgEventConditionInfoContract.Presenter>
        implements FgEventConditionInfoContract.View {

    //时间名称
    @BindView(R.id.tv_name)
    TextView mTvName;
    //费用
    @BindView(R.id.tv_cost)
    TextViewGroup mTvCost;
    //办理期限
    @BindView(R.id.tv_time_limit)
    TextViewGroup mTvTimeLimit;
    //法定期限
    @BindView(R.id.tv_legal_time)
    TextViewGroup mTvLegalTime;
    //办理对象
    @BindView(R.id.tv_object)
    TextViewGroup mTvObject;
    //部门
    @BindView(R.id.tv_depart)
    TextViewGroup mTvDepart;
    //办理时间
    @BindView(R.id.tv_handle_time)
    TextView mTvHandleTime;
    //地址
    @BindView(R.id.tv_addr)
    TextViewGroup mTvAddr;
    //说明
    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_condition_info;
    }

    @Override
    protected void initMVP() {
        mPresenter = new FgEventConditionInfoPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter.getEventInfo(getArguments());
    }

    @Override
    public void setEventName(String s) {
        mTvName.setText(s);
    }

    @Override
    public void setEventCost(String s) {
        mTvCost.setTextRight(s);
    }

    @Override
    public void setEventTimeLimit(String s) {
        mTvTimeLimit.setTextRight(s);
    }

    @Override
    public void setEventLegalTime(String s) {
        mTvLegalTime.setTextRight(s);
    }

    @Override
    public void setEventObject(String s) {
        mTvObject.setTextRight(s);
    }

    @Override
    public void setEventDepart(String s) {
        mTvDepart.setTextRight(s);
    }

    @Override
    public void setEventHandleTime(String s) {
        mTvHandleTime.setText(s);
    }

    @Override
    public void setEventHandleAddr(String s) {
        mTvAddr.setTextRight(s);
    }

    @Override
    public void setEventDesc(String s) {
        mTvDesc.setText(s);
    }
}
