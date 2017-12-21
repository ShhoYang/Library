package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;
import android.view.View;

import com.yl.library.rx.HttpCode;
import com.yl.library.rx.RxBus;
import com.yl.library.rx.RxSubscriber;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.api.Api;
import com.yl.yhbmfw.bean.MessageItem;
import com.yl.yhbmfw.event.EventSwitchFragment;
import com.yl.yhbmfw.mvp.activity.EventActivity;
import com.yl.yhbmfw.mvp.activity.MessageDetailsActivity;
import com.yl.yhbmfw.mvp.contract.FgMessageContract;

import java.util.List;

/**
 * @author Yang Shihao
 */
public class FgMessagePresenter extends FgMessageContract.Presenter {

    public FgMessagePresenter(FgMessageContract.View view) {
        super(view);
    }

    @Override
    public void getPageData(boolean isRefresh) {
        super.getPageData(isRefresh);
        addRx2Destroy(new RxSubscriber<List<MessageItem>>(Api.getMsgList()) {

            @Override
            protected void _onNext(List<MessageItem> messageItems) {
                setDataList(messageItems);
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

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        MessageItem messageItem = mDataList.get(position);

        if ("1".equals(messageItem.getMsg_type())) {
            mView.gotoActivity(new Intent(mContext, EventActivity.class));
        } else {
            Intent intent = new Intent(mContext, MessageDetailsActivity.class);
            intent.putExtra(Constant.KEY_STRING_1, messageItem.getMsg_content());
            mView.gotoActivity(intent);
        }

        if (!messageItem.isUnread()) {
            return;
        }

        addRx2Destroy(new RxSubscriber<String>(Api.setMsgRead(mDataList.get(position).getMsg_id())) {
            @Override
            protected void _onNext(String s) {
                RxBus.getInstance().send(new EventSwitchFragment(1));
            }
        });
    }
}
