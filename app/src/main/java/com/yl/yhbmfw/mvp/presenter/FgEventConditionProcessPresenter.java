package com.yl.yhbmfw.mvp.presenter;


import android.os.Bundle;

import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.mvp.contract.FgEventConditionProcessContract;

import java.util.List;


/**
 * @author Yang Shihao
 */
public class FgEventConditionProcessPresenter extends FgEventConditionProcessContract.Presenter {


    public FgEventConditionProcessPresenter(FgEventConditionProcessContract.View view) {
        super(view);
    }

    @Override
    public void getEventConditionProcess(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        EventTypeItem eventTypeItem = (EventTypeItem) bundle.getSerializable(Constant.KEY_BEAN);
        if (eventTypeItem == null) {
            return;
        }

        List<EventTypeItem.EventProcess> processList = eventTypeItem.getProcess();
        if (processList == null || processList.size() == 0) {
            return;
        }
        /*String[] progress = {
                    "【办结】",
                    "<font color='#1B82D1'>【办理中】</font>办事员:杨世豪,电话15137174714<br/>2017-10-17 10:30:00",
                    "<font color='#1B82D1'>【审核完成】</font>办事员:杨世豪,电话15137174714<br/>2017-10-17 10:20:00",
                    "<font color='#1B82D1'>【受理完成】</font>办事员:杨世豪,电话15137174714<br/>2017-10-17 10:10:00"
         };*/
        int size = processList.size();
        String[] contents = new String[size];
        EventTypeItem.EventProcess process;

        //【区县级-民政局-受理】
        String level;
        for (int i = 0; i < size; i++) {
            process = processList.get(i);
            level = process.getLevel();
            if ("2".equals(level)) {
                contents[size - 1 - i] = "<font color='#1B82D1'>【区县级-" + process.getDeptname() + "】【" + process.getName() + "】</font>";
            } else if ("3".equals(level)) {
                contents[size - 1 - i] = "<font color='#1B82D1'>【街镇级-" + process.getDeptname() + "】【" + process.getName() + "】</font>";
            } else if ("4".equals(level)) {
                contents[size - 1 - i] = "<font color='#1B82D1'>【村社级-" + process.getDeptname() + "】【" + process.getName() + "】</font>";
            } else {
                contents[size - 1 - i] = "<font color='#1B82D1'>【" + process.getDeptname() + "】【" + process.getName() + "】</font>";
            }
        }

        mView.setEventProcess(size, contents);
    }
}
