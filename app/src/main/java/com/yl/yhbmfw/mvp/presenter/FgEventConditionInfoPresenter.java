package com.yl.yhbmfw.mvp.presenter;


import android.os.Bundle;
import android.text.TextUtils;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.mvp.contract.FgEventConditionInfoContract;


/**
 * @author Yang Shihao
 */
public class FgEventConditionInfoPresenter extends FgEventConditionInfoContract.Presenter {

    private EventTypeItem mEventTypeItem;

    public FgEventConditionInfoPresenter(FgEventConditionInfoContract.View view) {
        super(view);
    }

    @Override
    public void getEventInfo(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        mEventTypeItem = (EventTypeItem) bundle.getSerializable(Constant.KEY_BEAN);
        if (mEventTypeItem == null) {
            return;
        }

        mView.setEventName(mEventTypeItem.getName());
        mView.setEventCost(mEventTypeItem.getFee());
        String time = mEventTypeItem.getTime_limit();
        if (TextUtils.isEmpty(time) || "无".equals(time) || "0".equals(time)) {
            mView.setEventTimeLimit("无");
        } else {
            mView.setEventTimeLimit(time + "天");
        }

        String time2 = mEventTypeItem.getLaw_period();
        if (TextUtils.isEmpty(time2) || "无".equals(time2) || "0".equals(time2)) {
            mView.setEventLegalTime("无");
        } else {
            mView.setEventLegalTime(time2 + "天");
        }
        mView.setEventObject(mEventTypeItem.getObj());
        mView.setEventDepart(mEventTypeItem.getDept_name());
        String dates = mEventTypeItem.getDates();
        if (!TextUtils.isEmpty(dates)) {
            dates = dates.replaceAll(" +", "");
            dates = dates.replaceAll("冬", "\n冬");
            mView.setEventHandleTime(dates);
        }
        mView.setEventHandleAddr(mEventTypeItem.getAddr());
        mView.setEventDesc(mEventTypeItem.getDesc());
    }
}
