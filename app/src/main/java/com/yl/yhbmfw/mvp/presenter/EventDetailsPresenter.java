package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;
import android.text.TextUtils;

import com.yl.library.rx.RxSubscriber;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.api.Api;
import com.yl.yhbmfw.bean.EventDetails;
import com.yl.yhbmfw.bean.EventList;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.mvp.activity.EventConditionActivity;
import com.yl.yhbmfw.mvp.contract.EventDetailsContract;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Yang Shihao
 */
public class EventDetailsPresenter extends EventDetailsContract.Presenter {

    private List<EventDetails.Material> mMaterialList = new ArrayList<>();
    private EventList.EventItem mEventItem;

    public EventDetailsPresenter(EventDetailsContract.View view) {
        super(view);
    }

    @Override
    public List<EventDetails.Material> getMaterialList() {
        return mMaterialList;
    }

    @Override
    public void getDetails(Intent intent) {
        if (intent == null) {
            return;
        }
        mEventItem = (EventList.EventItem) intent.getSerializableExtra(Constant.KEY_BEAN);
        if (mEventItem == null) {
            return;
        }
        mView.setEventInfo(mEventItem.getName(), "事件流水号: " + mEventItem.getCode(), "提交时间: " + mEventItem.getAdd_time());
        mView.showDialog();
        mRxManager.add(new RxSubscriber<EventDetails>(Api.getEventDetails(mEventItem.getId()), mView) {
            @Override
            protected void _onNext(EventDetails eventDetails) {
                arrangeData(eventDetails);
            }
        });
    }

    @Override
    public void searchByEventName(String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        mRxManager.add(new RxSubscriber<List<EventTypeItem>>(Api.getEventTypeList(null, name), mView) {

            @Override
            protected void _onNext(List<EventTypeItem> eventTypeItems) {
                if (eventTypeItems.size() == 0) {
                    mView.toast("未搜索到");
                } else {
                    Intent intent = new Intent(mContext, EventConditionActivity.class);
                    intent.putExtra(Constant.KEY_BEAN, eventTypeItems.get(0));
                    intent.putExtra(Constant.KEY_BOOLEAN_1, true);
                    mContext.startActivity(intent);
                }
            }

            @Override
            protected void _onError(String code) {
                mView.toast("搜索失败");
            }
        });
    }

    private void arrangeData(EventDetails eventDetails) {
        //是否办结
        if (mEventItem.isFinished()) {
            EventDetails.Postal postal = eventDetails.getPostal();
            if (postal == null) {
                mView.noFinished();
                return;
            }
            // 0-无需获取 1-邮寄 2-自拿
            if ("0".equals(postal.getIspostal())) {
                mView.noPostalInfo("获取方式: 无需获取");
            } else if ("1".equals(postal.getIspostal())) {
                mView.setPostalInfo("快递公司: " + postal.getPostalname(), "快递单号: " + postal.getPostalnumber());
            } else if ("2".equals(postal.getIspostal())) {
                mView.noPostalInfo("获取方式: 自提");
            }
        } else {
            mView.noFinished();
        }
        List<EventDetails.Process> processList = eventDetails.getProcess();
        if (processList == null || processList.size() == 0) {
            mView.toast("获取进度失败");
        } else {
            /*String[] progress = {
                    "【办结】",
                    "<font color='#1B82D1'>【办理中】</font>办事员:杨世豪,电话15137174714<br/>2017-10-17 10:30:00",
                    "<font color='#1B82D1'>【审核完成】</font>办事员:杨世豪,电话15137174714<br/>2017-10-17 10:20:00",
                    "<font color='#1B82D1'>【受理完成】</font>办事员:杨世豪,电话15137174714<br/>2017-10-17 10:10:00"
            };*/
            int size = processList.size();
            String[] contents = new String[size];
            EventDetails.Process process;
            String status;

            int handleWait = 0;
            for (int i = 0; i < size; i++) {
                process = processList.get(i);
                status = process.getDealstatus();
                if (TextUtils.isEmpty(status)) {
                    handleWait++;
                    contents[size - 1 - i] = "【" + process.getName() + "】";
                } else if ("0".equals(status)) {
                    contents[size - 1 - i] = getString(process, "<font color='#1B82D1'>【");
                } else if ("1".equals(status)) {
                    contents[size - 1 - i] = getString(process, "<font color='#303F9F'>【");
                }
            }

            mView.setEventProgress(handleWait, size, contents);
        }

        List<EventDetails.Material> materialList = eventDetails.getMaterial();
        if (materialList == null || materialList.size() == 0) {
            return;
        }

        mMaterialList.clear();
        for (EventDetails.Material material : materialList) {
            String pics = material.getPic();
            if (TextUtils.isEmpty(pics)) {
                continue;
            }
            if (pics.contains(",")) {
                String[] tempArr = pics.split(",");
                for (String s : tempArr) {
                    mMaterialList.add(new EventDetails.Material(material.getName(), s));
                }
            } else {
                mMaterialList.add(material);
            }
        }
        if (mMaterialList.size() == 0) {
            mView.noMaterial();
        } else {
            mView.updateMaterial();
        }
    }

    private StringBuffer stringBuffer;

    private String getString(EventDetails.Process process, String head) {
        stringBuffer = new StringBuffer(head);
        if (!TextUtils.isEmpty(process.getLevel())) {
            stringBuffer.append(process.getLevel())
                    .append("-");
        }
        stringBuffer.append(process.getName())
                .append("】")
                .append("【");
        if (!TextUtils.isEmpty(process.getDeptname())) {
            stringBuffer
                    .append(process.getDeptname())
                    .append("-");
        }
        stringBuffer.append(process.getReal_name())
                .append("】</font>");
        if (!TextUtils.isEmpty(process.getMobile())) {
            stringBuffer.append("<br/>电话:")
                    .append(process.getMobile());
        }
        if (!TextUtils.isEmpty(process.getTime())) {
            stringBuffer.append("<br/>")
                    .append(process.getTime());
        }
        return stringBuffer.toString();
    }
}
