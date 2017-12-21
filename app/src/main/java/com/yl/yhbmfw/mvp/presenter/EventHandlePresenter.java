package com.yl.yhbmfw.mvp.presenter;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.socks.library.KLog;
import com.yl.library.rx.RxSubscriber;
import com.yl.library.utils.AppManager;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.Config;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.api.Api;
import com.yl.yhbmfw.bean.AuthInfo;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.bean.RecAddress;
import com.yl.yhbmfw.mvp.activity.EventActivity;
import com.yl.yhbmfw.mvp.activity.EventConditionActivity;
import com.yl.yhbmfw.mvp.activity.EventTypeItemListActivity;
import com.yl.yhbmfw.mvp.contract.EventHandleContract;
import com.yl.yhbmfw.widget.CheckboxView;
import com.yl.yhbmfw.widget.MultipleImageView;
import com.yl.yhbmfw.widget.RectEditView;
import com.yl.yhbmfw.widget.TableView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * @author Yang Shihao
 */
public class EventHandlePresenter extends EventHandleContract.Presenter {

    private static final String TAG = "EventHandlePresenter";

    //表单定义常量 不同类型
    private static final String TEXT = "text";
    private static final String TEXTAREA = "textarea";
    private static final String RADIO = "radio";
    private static final String CHECKBOX = "checkbox";
    private static final String FIELDSET = "fieldset";
    private static final String CARD = "card";
    private static final String NUMBER = "number";
    private static final String EMBEDTABLE = "embedtable";

    private Config mConfig = App.getInstance().getConfig();
    //用户授权信息
    private AuthInfo mAuthInfo;
    //事件信息
    private EventTypeItem mEventTypeItem;
    //动态创建的表单的集合
    private List<RectEditView> mInputViewList = new ArrayList<>();
    private List<CheckboxView> mCheckboxViewList = new ArrayList<>();
    private List<MultipleImageView> mAddImageViewList = new ArrayList<>();
    private List<TableView> mTabViewViewList = new ArrayList<>();
    //添加图片的requestCode,创建控件时自增，
    private int mRequestCode = 100;

    //被驳回事件的id,修改后提交需要上传,也通过它判断是否是被驳回事件
    private String mId;
    private RecAddress mDefaultAddr = null;

    public EventHandlePresenter(EventHandleContract.View view) {
        super(view);
    }


    @Override
    public void createForm(Intent intent) {
        if (intent == null) {
            return;
        }
        mView.showDialog();
        mEventTypeItem = (EventTypeItem) intent.getSerializableExtra(Constant.KEY_BEAN);
        mId = intent.getStringExtra(Constant.KEY_STRING_1);
        if (mEventTypeItem == null) {
            return;
        }
        mAuthInfo = mConfig.getAuthInfo();
        if (mAuthInfo == null) {
            addRx2Destroy(new RxSubscriber<AuthInfo>(Api.authenticateResult()) {
                @Override
                protected void _onNext(AuthInfo info) {
                    mConfig.setAuthInfo(info);
                    mAuthInfo = info;
                    initData();
                }
            });
        } else {
            initData();
        }
    }

    @Override
    public void addImage(int requestCode, Intent data) {
        for (MultipleImageView addImageView : mAddImageViewList) {
            if (addImageView.getRequestCode() == requestCode) {
                addImageView.setImage(data);
                break;
            }
        }
    }

    /**
     * 初始化View数据
     */
    private void initData() {
        mView.setEventName(mEventTypeItem.getName());
        if (mEventTypeItem.getMaterial() != null && mEventTypeItem.getMaterial().size() != 0) {
            createForm(mEventTypeItem.getMaterial());
        }
        if (mEventTypeItem.enableExpress()) {
            mView.showObtainMode(View.VISIBLE);
            mView.showRlRecAddress(View.VISIBLE);
        } else {
            mView.showObtainMode(View.GONE);
            mView.showRlRecAddress(View.GONE);
        }
        mView.dismissDialog();
    }

    /**
     * 创建表格
     */
    private void createForm(List<EventTypeItem.EventMaterial> materialList) {
        String type;
        for (EventTypeItem.EventMaterial material : materialList) {
            type = material.getType();
            switch (type) {
                case TEXT:
                case TEXTAREA:
                    createInput(material, true);
                    break;
                case FIELDSET:
                    createInput(material, false);
                    break;
                case RADIO:
                    createRadio(material);
                    break;
                case CHECKBOX:
                    createCheckbox(material);
                    break;
                case CARD:
                case NUMBER:
                    createImageView(material);
                    break;
                case EMBEDTABLE:
                    createTable(material);
                    break;
            }
        }
        for (MultipleImageView view : mAddImageViewList) {
            mView.addView(view);
        }
    }

    private void createInput(EventTypeItem.EventMaterial material, boolean enableEdit) {

        RectEditView rectEditView = new RectEditView(mContext);
        rectEditView.setEventMaterial(material);
        String name = material.getName();
        if ("姓名".equals(name)) {
            rectEditView.setValueText(mAuthInfo.getReal_name());
        } else if (("身份证号").equals(name)) {
            rectEditView.setValueText(mAuthInfo.getCard());
        } else if (("联系电话").equals(name)) {
            rectEditView.setValueText(mConfig.getPhone());
        }

        //设置不可编辑
        if (!enableEdit || "郑重声明".equals(name)) {
            rectEditView.setEnableEdit(false);
        }
        mInputViewList.add(rectEditView);
        mView.addView(rectEditView);
    }

    private void createRadio(EventTypeItem.EventMaterial material) {
        CheckboxView checkboxView = new CheckboxView(mContext);
        checkboxView.setMaxSelectCount(1);
        checkboxView.setEventMaterial(material);
        mCheckboxViewList.add(checkboxView);
        mView.addView(checkboxView);
    }

    private void createCheckbox(EventTypeItem.EventMaterial material) {
        CheckboxView checkboxView = new CheckboxView(mContext);
        checkboxView.setEventMaterial(material);
        mCheckboxViewList.add(checkboxView);
        mView.addView(checkboxView);
    }


    private void createImageView(EventTypeItem.EventMaterial material) {
        MultipleImageView addImageView = new MultipleImageView(mContext);
        addImageView.setEventMaterial(material);
        addImageView.setRequestCode(mRequestCode++);
        mAddImageViewList.add(addImageView);
    }

    private void createTable(EventTypeItem.EventMaterial material) {
        TableView tableView = new TableView(mContext);
        tableView.setEventMaterial(material);
        mTabViewViewList.add(tableView);
        mView.addView(tableView);
    }

    @Override
    public void submit() {
        //判断input的必填项
        for (RectEditView rectEditView : mInputViewList) {
            if (!rectEditView.isPass()) {
                mView.toast(rectEditView.getKeyText() + "不能为空");
                return;
            }
        }
        //判断checkbox的必填项
        for (CheckboxView checkboxView : mCheckboxViewList) {
            if (!checkboxView.isPass()) {
                mView.toast("请选择" + checkboxView.getKeyText());
                return;
            }
        }
        //判断图片的必填项
        for (MultipleImageView addImageView : mAddImageViewList) {
            if (!addImageView.isPass()) {
                mView.toast("请上传" + addImageView.getKeyText());
                return;
            }
        }

        if (mEventTypeItem.enableExpress() && !mView.isSelf() && mDefaultAddr == null) {
            mView.toast("没有默认邮寄地址");
            return;
        }

        mView.showDialog("正在提交...");
        List<Map<String, String>> materialList = new ArrayList<>();
        List<Map<String, String>> formList = new ArrayList<>();
        //获取input数据
        for (RectEditView rectEditView : mInputViewList) {
            Map<String, String> params = new HashMap<>();
            params.put("eletable_id", rectEditView.getEventMaterial().getEletable_id());
            params.put("value", rectEditView.getValue());
            formList.add(params);
        }
        //获取checkbox数据
        for (CheckboxView checkboxView : mCheckboxViewList) {
            Map<String, String> params = new HashMap<>();
            params.put("eletable_id", checkboxView.getEventMaterial().getEletable_id());
            params.put("value", checkboxView.getValue());
            formList.add(params);
        }

        //获取table数据
        for (TableView tableView : mTabViewViewList) {
            Map<String, String> params = new HashMap<>();
            params.put("eletable_id", tableView.getEventMaterial().getEletable_id());
            params.put("value", tableView.getValue());
            formList.add(params);
        }

        //最终参数集合
        List<MultipartBody.Part> parts = new ArrayList<>();
        //获取图片数据
        List<EventTypeItem.ImageItem> imageList;
        EventTypeItem.ImageItem imageItem;
        String materialId;
        for (MultipleImageView addImageView : mAddImageViewList) {
            imageList = addImageView.getImageList();
            KLog.d(TAG, "-----------------------------------------------------");
            for (int i = 0; i < imageList.size(); i++) {
                materialId = addImageView.getEventMaterial().getMaterial_id();
                imageItem = imageList.get(i);
                KLog.d(TAG, imageItem.toString());
                if (!TextUtils.isEmpty(imageItem.getLocalPath())) {
                    parts.add(createMultipartBody(String.format("pic_%s_%d", materialId, i), imageItem.getLocalPath()));
                }
            }
        }

        //添加card数据
        /*List<EventTypeList.EventMaterial> eventMaterialList = mEventTypeItem.getMaterial();
        if (eventMaterialList != null && eventMaterialList.size() != 0) {

            for (EventTypeList.EventMaterial material : eventMaterialList) {
                Map<String, String> params = new HashMap<>();
                String type = material.getType();
                if (CARD.equals(type)) {
                    params.put("material_id", material.getMaterial_id());
                    params.put("value", mAuthInfo.getCard());
                    materialList.add(params);
                }
            }
        }*/

        /*//添加素材id
        if (!TextUtils.isEmpty(mEventTypeItem.getMaterial_id())) {
            Map<String, String> params = new HashMap<>();
            params.put("material_id", mEventTypeItem.getMaterial_id());
            params.put("value", "form");
            materialList.add(params);
        }*/

        //添加其他信息
        Map<String, Object> map = new HashMap<>();
        map.put("item_id", mEventTypeItem.getId());
        map.put("user_id", mConfig.getUser().getId());
        map.put("region_id", mAuthInfo.getRegion_id());
        map.put("material", materialList);
        map.put("material_value", formList);

        Map<String, Object> params = new HashMap<>();
        params.put("data", map);
        String json = new Gson().toJson(params);
        KLog.d(TAG, json);
        //设置表单参数
        parts.add(MultipartBody.Part.createFormData("data", json));

        //0-无需获取 1-邮寄 2-自拿
        if (mEventTypeItem.enableExpress()) {
            parts.add(MultipartBody.Part.createFormData("ispostal", mView.isSelf() ? "2" : "1"));
        } else {
            parts.add(MultipartBody.Part.createFormData("ispostal", "0"));
        }


        if (TextUtils.isEmpty(mId)) { //新事件
            addRx2Destroy(new RxSubscriber<String>(Api.submitEvent(parts), mView) {

                @Override
                protected void _onNext(String s) {
                    submitSuccess();
                }
            });
        } else {  //驳回再次提交,要传被驳回事件的id
            parts.add(MultipartBody.Part.createFormData("eid", mId));
            addRx2Destroy(new RxSubscriber<String>(Api.applyRejectEvent(parts), mView) {

                @Override
                protected void _onNext(String s) {
                    submitSuccess();
                }
            });
        }
    }

    private void submitSuccess() {
        mView.toast("提交成功");
        PictureFileUtils.deleteCacheDirFile(mContext);
        AppManager.getInstance().finishActivity(EventTypeItemListActivity.class);
        AppManager.getInstance().finishActivity(EventConditionActivity.class);
        mView.gotoActivityAndFinish(new Intent(mContext, EventActivity.class));
    }

    @Override
    public void getDefaultRecAddress() {
        if (!mEventTypeItem.enableExpress()) {
            return;
        }
        addRx2Destroy(new RxSubscriber<List<RecAddress>>(Api.getRecAddress()) {

            @Override
            protected void _onNext(List<RecAddress> recAddresses) {
                mDefaultAddr = null;
                for (RecAddress r : recAddresses) {
                    if (r.isDefault()) {
                        mDefaultAddr = r;
                        break;
                    }
                }

                if (mDefaultAddr == null) {
                    mView.showSetRecAddress(View.VISIBLE);
                    mView.showRecAddressInfo(View.INVISIBLE);
                } else {
                    mView.showSetRecAddress(View.GONE);
                    mView.showRecAddressInfo(View.VISIBLE);
                    mView.setRecAddress(mDefaultAddr);
                }
            }

            @Override
            protected void _onError(String code) {
                mDefaultAddr = null;
                mView.showSetRecAddress(View.VISIBLE);
                mView.showRecAddressInfo(View.GONE);
            }
        });
    }

    private MultipartBody.Part createMultipartBody(String key, String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(key, file.getName(), requestBody);
    }
}
