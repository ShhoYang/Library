package com.yl.yhbmfw.mvp.activity;

import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yl.library.base.activity.BaseActivity;
import com.yl.library.utils.KeyBoardUtils;
import com.yl.library.widget.EditTextGroup;
import com.yl.library.widget.TextViewGroup;
import com.yl.yhbmfw.R;
import com.yl.yhbmfw.bean.RegionNode;
import com.yl.yhbmfw.mvp.contract.AddReceiveAddressContract;
import com.yl.yhbmfw.mvp.presenter.AddReceiveAddressPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddReceiveAddressActivity extends BaseActivity<AddReceiveAddressContract.Presenter>
        implements AddReceiveAddressContract.View {

    //姓名
    @BindView(R.id.et_name)
    EditTextGroup mEtName;
    //电话
    @BindView(R.id.et_phone)
    EditTextGroup mEtPhone;
    //地区
    @BindView(R.id.tv_area)
    TextViewGroup mTvArea;
    //街道
    @BindView(R.id.tv_street)
    TextViewGroup mTvStreet;
    //详细地址
    @BindView(R.id.et_details_address)
    EditText mEtDetailsAddress;
    //设置默认开关
    @BindView(R.id.tv_default)
    TextViewGroup mTvDefault;
    //地区选择器
    private OptionsPickerView mOptionsPickerViewArea;
    //街道选择器
    private OptionsPickerView mOptionsPickerViewStreet;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_receive_address;
    }

    @Override
    protected void initMVP() {
        mPresenter = new AddReceiveAddressPresenter(this);
    }

    @Override
    protected void initView() {
        setTitle("添加新地址");
        setTextRight("保存");
    }

    @Override
    protected void initData() {
        mPresenter.initData(getIntent());
    }

    /**
     * 保存
     */
    @Override
    public void onTextViewRightClicked() {
        mPresenter.save();
    }

    /**
     * 选择地区
     */
    @OnClick(R.id.tv_area)
    public void onTvRegionClicked() {
        KeyBoardUtils.closeSoftInput(this);
        mPresenter.getAreaList();
    }

    /**
     * 选择街道
     */
    @OnClick(R.id.tv_street)
    public void onTvStreetClicked() {
        KeyBoardUtils.closeSoftInput(this);
        mPresenter.getStreetList();
    }

    /**
     * 标题
     * 添加新地址
     * 编辑地址
     */
    @Override
    public void setTitleText(String s) {
        setTitle(s);
    }

    @Override
    public void setName(String s) {
        mEtName.setTextRight(s);
    }

    @Override
    public void setPhone(String s) {
        mEtPhone.setTextRight(s);
    }

    @Override
    public void setArea(String s) {
        mTvArea.setTextRight(s);
    }

    @Override
    public void setStreet(String s) {
        mTvStreet.setTextRight(s);
    }

    @Override
    public void setDetailsAddress(String s) {
        mEtDetailsAddress.setText(s);
    }

    @Override
    public void isDefault(boolean def) {
        mTvDefault.setBtnSwitch(def);
    }

    @Override
    public void showAreaDialog(List<RegionNode> list1, List<List<RegionNode>> list2, List<List<List<RegionNode>>> list3) {
        if (mOptionsPickerViewArea == null) {
            mOptionsPickerViewArea = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    mPresenter.setSelectedArea(options1, options2, options3);
                }
            }).build();
        }
        mOptionsPickerViewArea.setPicker(list1, list2, list3);
        if (!mOptionsPickerViewArea.isShowing()) {
            mOptionsPickerViewArea.show();
        }
    }

    @Override
    public void showStreetDialog(List<RegionNode> list1) {
        if (mOptionsPickerViewStreet == null) {
            mOptionsPickerViewStreet = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    mPresenter.setSelectedStreet(options1);
                }
            }).build();
        }
        mOptionsPickerViewStreet.setPicker(list1);
        if (!mOptionsPickerViewStreet.isShowing()) {
            mOptionsPickerViewStreet.show();
        }
    }

    @Override
    public String getName() {
        return mEtName.getTextRight();
    }

    @Override
    public String getPhone() {
        return mEtPhone.getTextRight();
    }

    @Override
    public String getDetailsAddress() {
        return mEtDetailsAddress.getText().toString();
    }

    @Override
    public boolean isDefault() {
        return mTvDefault.getBtnSwitch();
    }

    @Override
    public void showDefaultAddressView(int visibility) {
        mTvDefault.setVisibility(visibility);
    }
}