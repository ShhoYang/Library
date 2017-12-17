package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.yl.library.rx.RxBus;
import com.yl.library.rx.RxSubscriber;
import com.yl.library.utils.RegexUtils;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.api.Api;
import com.yl.yhbmfw.bean.RecAddress;
import com.yl.yhbmfw.bean.RegionNode;
import com.yl.yhbmfw.event.EventRefresh;
import com.yl.yhbmfw.mvp.contract.AddReceiveAddressContract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang Shihao
 *         我的事件
 */
public class AddReceiveAddressPresenter extends AddReceiveAddressContract.Presenter {

    private List<RegionNode> mProvinceList = new ArrayList<>();
    private List<List<RegionNode>> mCityList = new ArrayList<>();
    private List<List<List<RegionNode>>> mDistrictList = new ArrayList<>();
    private List<List<List<List<RegionNode>>>> mStreetList = new ArrayList<>();

    private List<RegionNode> mStreetListOption = new ArrayList<>();

    private RegionNode mSelectedProvince;
    private RegionNode mSelectedCity;
    private RegionNode mSelectedDistrict;
    private RegionNode mSelectedStreet;

    private String mSelectedAreaText;
    private String mSelectedStreetText;

    private int mSelectedProvinceIndex = 0;
    private int mSelectedCityIndex = 0;
    private int mSelectedDistrictIndex = 0;
    private int mSelectedStreetIndex = 0;

    private RecAddress mRecAddress;
    private Intent mIntent;


    public AddReceiveAddressPresenter(AddReceiveAddressContract.View view) {
        super(view);
    }

    @Override
    public void initData(Intent intent) {
        mIntent = intent;
        if (mIntent == null) {
            return;
        }

        mRecAddress = (RecAddress) mIntent.getSerializableExtra(Constant.KEY_BEAN);
        if (mRecAddress == null) {
            return;
        }

        int size = mIntent.getIntExtra(Constant.KEY_INT_1, 0);
        if (size < 2 && mRecAddress.isDefault()) {
            mView.showDefaultAddressView(View.GONE);
        }else {
            mView.showDefaultAddressView(View.VISIBLE);
        }

        mView.setTitleText("编辑地址");
        mView.setName(mRecAddress.getName());
        mView.setPhone(mRecAddress.getTel());
        mView.setArea(mRecAddress.getArea());
        mView.setStreet(mRecAddress.getTown());
        mView.setDetailsAddress(mRecAddress.getFull_addr());
        mView.isDefault(mRecAddress.isDefault());
        mRxManager.add(new RxSubscriber<List<RegionNode>>(Api.getRegion(null)) {

            @Override
            protected void _onNext(List<RegionNode> regionNodes) {
                arrangeRegionData(regionNodes, mRecAddress);
            }
        });
    }

    private void arrangeRegionData(List<RegionNode> regionNodes, RecAddress recAddress) {
        if (regionNodes == null || regionNodes.size() == 0) {
            return;
        }
        mProvinceList.clear();
        mCityList.clear();
        mDistrictList.clear();
        mStreetList.clear();

        mProvinceList.addAll(regionNodes);
        List<List<RegionNode>> temp1;
        List<List<RegionNode>> temp2;
        List<List<List<RegionNode>>> temp3;
        for (RegionNode p : mProvinceList) {
            temp1 = new ArrayList();
            temp3 = new ArrayList();
            if (recAddress != null && p.getText().equals(recAddress.getProvince())) {
                mSelectedProvince = p;
            }
            for (RegionNode c : p.getChildren()) {
                temp1.add(c.getChildren());
                temp2 = new ArrayList();
                if (recAddress != null && c.getText().equals(recAddress.getCity())) {
                    mSelectedCity = c;
                }
                for (RegionNode d : c.getChildren()) {
                    temp2.add(d.getChildren());
                    if (recAddress != null && d.getText().equals(recAddress.getCounty())) {
                        mSelectedAreaText = p.getText() + c.getText() + d.getText();
                        mSelectedDistrict = d;
                        mStreetListOption.clear();
                        mStreetListOption.addAll(d.getChildren());
                    }
                    for (RegionNode s : d.getChildren()) {
                        if (recAddress != null && s.getText().equals(recAddress.getTown())) {
                            mSelectedStreetText = s.getText();
                            mSelectedStreet = s;
                        }
                    }
                }
                temp3.add(temp2);
            }
            mCityList.add(p.getChildren());
            mDistrictList.add(temp1);
            mStreetList.add(temp3);
        }
    }

    @Override
    public void getAreaList() {
        if (mProvinceList.size() == 0) {
            mView.showDialog();
            mRxManager.add(new RxSubscriber<List<RegionNode>>(Api.getRegion(null)) {
                @Override
                protected void _onNext(List<RegionNode> regionNodes) {
                    arrangeRegionData(regionNodes, null);
                    mView.dismissDialog();
                    if (mProvinceList.size() != 0) {
                        mView.showAreaDialog(mProvinceList, mCityList, mDistrictList);
                    }
                }
            });
        } else {
            mView.showAreaDialog(mProvinceList, mCityList, mDistrictList);
        }
    }

    @Override
    public void getStreetList() {
        if (mStreetListOption.size() == 0) {
            mView.toast("请先选择区域");
        } else {
            mView.showStreetDialog(mStreetListOption);
        }
    }

    @Override
    public void setSelectedArea(int index1, int index2, int index3) {
        mSelectedProvinceIndex = index1;
        mSelectedCityIndex = index2;
        mSelectedStreetIndex = index3;
        mSelectedProvince = mProvinceList.get(index1);
        mSelectedCity = mCityList.get(index1).get(index2);
        mSelectedDistrict = mDistrictList.get(index1).get(index2).get(index3);
        mStreetListOption.clear();
        mStreetListOption.addAll(mSelectedDistrict.getChildren());
        mSelectedAreaText = mSelectedProvince.getText() + mSelectedCity.getText() + mSelectedDistrict.getText();
        mView.setArea(mSelectedAreaText);
    }

    @Override
    public void setSelectedStreet(int index1) {
        mSelectedStreetIndex = index1;
        mSelectedStreet = mStreetListOption.get(index1);
        mSelectedStreetText = mSelectedStreet.getText();
        mView.setStreet(mSelectedStreetText);
    }

    @Override
    public void save() {
        String name = mView.getName();
        if (TextUtils.isEmpty(name)) {
            mView.toast("姓名不能为空");
            return;
        }

        String phone = mView.getPhone();
        if (TextUtils.isEmpty(phone)) {
            mView.toast("手机号不能为空");
            return;
        }

        if (!RegexUtils.isMobilePhoneNumber(phone)) {
            mView.toast("手机号格式不正确");
            return;
        }

        if (TextUtils.isEmpty(mSelectedAreaText)) {
            mView.toast("社区不能为空");
            return;
        }

        if (TextUtils.isEmpty(mSelectedStreetText)) {
            mView.toast("街道不能为空");
            return;
        }

        String detailsAddress = mView.getDetailsAddress();
        if (TextUtils.isEmpty(detailsAddress)) {
            mView.toast("详细地址不能为空");
            return;
        }

        mView.showDialog("正在保存...");
        if (mRecAddress != null && !TextUtils.isEmpty(mRecAddress.getId())) {
            mRxManager.add(new RxSubscriber(Api.editRecAddress(mRecAddress.getId(), mSelectedProvince.getText(), mSelectedCity.getText(), mSelectedDistrict.getText(),
                    mSelectedStreet.getText(), detailsAddress, name, phone, mView.isDefault() ? "1" : "0"), mView) {
                @Override
                protected void _onNext(Object o) {
                    RxBus.getInstance().send(new EventRefresh(EventRefresh.REFRESH_REC_ADDRESS_LIST));
                    mView.toast("保存成功");
                    mView.finishActivity();
                }
            });
        } else {
            mRxManager.add(new RxSubscriber(Api.addRecAddress(mSelectedProvince.getText(), mSelectedCity.getText(),
                    mSelectedDistrict.getText(), mSelectedStreet.getText(), detailsAddress,
                    name, phone, mView.isDefault() ? "1" : "0"), mView) {
                @Override
                protected void _onNext(Object o) {
                    if (isChooseRecAddr()) {
                        //从办事跳转过来
                        RxBus.getInstance().send(new EventRefresh(EventRefresh.REFRESH_REC_ADDRESS));
                    } else {
                        //从地址列表跳转过来
                        RxBus.getInstance().send(new EventRefresh(EventRefresh.REFRESH_REC_ADDRESS_LIST));
                    }
                    mView.toast("保存成功");
                    mView.finishActivity();
                }
            });
        }
    }

    private boolean isChooseRecAddr() {
        if (mIntent == null) {
            return false;
        }

        return mIntent.getBooleanExtra(Constant.KEY_BOOLEAN_1, false);
    }
}