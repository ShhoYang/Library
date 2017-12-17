package com.yl.yhbmfw.mvp.presenter;


import com.yl.yhbmfw.bean.RecAddress;
import com.yl.yhbmfw.mvp.contract.ReceiveAddressContract;
import com.yl.yhbmfw.api.Api;
import com.yl.library.rx.HttpCode;
import com.yl.library.rx.RxSubscriber;

import java.util.List;

/**
 * @author Yang Shihao
 */
public class ReceiveAddressPresenter extends ReceiveAddressContract.Presenter {

    public ReceiveAddressPresenter(ReceiveAddressContract.View view) {
        super(view);
    }

    @Override
    public void deleteRecAddress(RecAddress recAddress) {
        mRxManager.add(new RxSubscriber<String>(Api.deleteRecAddress(recAddress.getId())) {
            @Override
            protected void _onNext(String s) {
                mView.toast("删除成功");
                getPageData(true);
            }
        });
    }

    @Override
    public void setDefaultRecAddress(RecAddress recAddress) {
        mRxManager.add(new RxSubscriber<String>(Api.editRecAddress(recAddress.getId(), recAddress.getProvince(),
                recAddress.getCity(), recAddress.getCounty(), recAddress.getTown(), recAddress.getFull_addr(),
                recAddress.getName(), recAddress.getTel(), "1")) {
            @Override
            protected void _onNext(String s) {
                mView.toast("设置成功");
                getPageData(true);
            }
        });
    }

    @Override
    public RecAddress getDefaultAddress() {
        for (RecAddress recAddress : mDataList) {
            if (recAddress.isDefault()) {
                return recAddress;
            }
        }
        return null;
    }


    @Override
    public void getPageData(boolean isRefresh) {
        super.getPageData(isRefresh);
        mRxManager.add(new RxSubscriber<List<RecAddress>>(Api.getRecAddress()) {

            @Override
            protected void _onNext(List<RecAddress> recAddresses) {
                setDataList(recAddresses);
            }


            @Override
            protected void _onError(String code) {
                if (code.equals(HttpCode.CODE_20001.getCode())) {
                    clear();
                } else {
                    super._onError(code);
                    mView.loadError();
                }
            }
        });
    }
}
