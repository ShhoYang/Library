package com.yl.yhbmfw.mvp.fragment;

import com.yl.library.base.fragment.BaseFragment;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.mvp.contract.FgEventConditionProcessContract;
import com.yl.yhbmfw.mvp.presenter.FgEventConditionProcessPresenter;
import com.yl.yhbmfw.widget.StepView;

import butterknife.BindView;

/**
 * @author Yang Shihao
 *         <p>
 *         办理条件-流程
 */
public class FragmentConditionProcess extends BaseFragment<FgEventConditionProcessContract.Presenter>
        implements FgEventConditionProcessContract.View {

    @BindView(R.id.step)
    StepView mStepView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_condition_process;
    }

    @Override
    protected void initMVP() {
        mPresenter = new FgEventConditionProcessPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter.getEventConditionProcess(getArguments());
    }

    @Override
    public void setEventProcess(int total, String[] contents) {
        mStepView.setProgress(0, total, contents, null);
        mStepView.requestLayout();
    }
}
