package com.yl.yhbmfw.api;

import android.text.TextUtils;

import com.yl.library.rx.RetrofitUtils;
import com.yl.library.rx.RxSchedulers;
import com.yl.yhbmfw.App;
import com.yl.yhbmfw.bean.AuthInfo;
import com.yl.yhbmfw.bean.BannerItem;
import com.yl.yhbmfw.bean.EventDetails;
import com.yl.yhbmfw.bean.EventList;
import com.yl.yhbmfw.bean.EventTypeItem;
import com.yl.yhbmfw.bean.MessageItem;
import com.yl.yhbmfw.bean.RecAddress;
import com.yl.yhbmfw.bean.RegionNode;
import com.yl.yhbmfw.bean.User;
import com.yl.yhbmfw.bean.VersionInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Yang Shihao
 */
public class Api {

    //private static final ApiService API_SERVICE = RetrofitUtils.getInstance().getRetrofit().create(ApiService.class);
    private static final ApiService API_SERVICE = RetrofitUtils.getInstance().getProxy(ApiService.class, new MyProxyHandler());

    private static String getToken() {
        return App.getInstance().getConfig().getToken();
    }

    private static MultipartBody.Part createMultipartBody(String key, String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(key, file.getName(), requestBody);
    }

    /**
     * 获取验证码
     */
    public static Observable<String> getSMSCode(String phone, String serial) {
        Map<String, String> params = new HashMap<>();
        params.put("phone_number", phone);
        params.put("serialnumber", serial);
        return API_SERVICE.getSMSCode(params).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 注册
     */
    public static Observable<String> register(String code, String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("verification_code", code);
        params.put("password", pwd);

        return API_SERVICE.register(params).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 登录
     */
    public static Observable<User> login(String phone, String pwd, String serial, String deviceToken) {
        Map<String, String> params = new HashMap<>();
        params.put("phone_number", phone);
        params.put("password", pwd);
        params.put("serialnumber", serial);
        if (!TextUtils.isEmpty(deviceToken)) {
            params.put("deviceToken", deviceToken);
        }
        return API_SERVICE.login(params).compose(RxSchedulers.<User>io_main());
    }

    /**
     * 登录
     */
    public static Observable<User> loginNoSubscribe(String phone, String pwd, String serial, String deviceToken) {
        Map<String, String> params = new HashMap<>();
        params.put("phone_number", phone);
        params.put("password", pwd);
        params.put("serialnumber", serial);
        if (!TextUtils.isEmpty(deviceToken)) {
            params.put("deviceToken", deviceToken);
        }
        return API_SERVICE.login(params);
    }

    /**
     * 登出
     */
    public static Observable<String> signOut() {
        return API_SERVICE.signOut().compose(RxSchedulers.<String>io_main());
    }

    /**
     * 忘记密码
     */
    public static Observable<String> forgetPassword(String code, String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("SMSCode", code);
        params.put("userPwd", pwd);
        params.put("userCfmPwd", pwd);
        return API_SERVICE.forgetPassword(params).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 修改密码
     */
    public static Observable<String> changePassword(String phone, String oldPwd, String newPwd) {
        Map<String, String> params = new HashMap<>();
        params.put("phone_number", phone);
        params.put("oldPasswd", oldPwd);
        params.put("newPasswd", newPwd);
        return API_SERVICE.changePassword(getToken(), params).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 修改用户信息
     */
    public static Observable<String> updateUserInfo(String headImagePath, String nickname,
                                                    String sex, String birthday, String email) {

        List<MultipartBody.Part> parts = new ArrayList<>();
        if (!TextUtils.isEmpty(headImagePath)) {
            parts.add(createMultipartBody("FileData", headImagePath));
        }
        parts.add(MultipartBody.Part.createFormData("nickname", nickname));
        parts.add(MultipartBody.Part.createFormData("sex", sex));
        parts.add(MultipartBody.Part.createFormData("birthday", birthday));
        parts.add(MultipartBody.Part.createFormData("email", email));

        return API_SERVICE.updateUserInfo(getToken(), parts).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 实名认证
     */
    public static Observable<String> authenticate(String phone, String name, String idCard,
                                                  String regionId, String headPath, String idCardFrontPath,
                                                  String idCardAfterPath) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(MultipartBody.Part.createFormData("phone_number", phone));
        parts.add(MultipartBody.Part.createFormData("real_name", name));
        parts.add(MultipartBody.Part.createFormData("card", idCard));
        parts.add(MultipartBody.Part.createFormData("region_id", regionId));
        if (!TextUtils.isEmpty(headPath)) {
            parts.add(createMultipartBody("standard_photo", headPath));
        }
        if (!TextUtils.isEmpty(idCardFrontPath)) {
            parts.add(createMultipartBody("card_img_heads", idCardFrontPath));
        }
        if (!TextUtils.isEmpty(headPath)) {
            parts.add(createMultipartBody("card_img_tails", idCardAfterPath));
        }
        return API_SERVICE.authenticate(getToken(), parts).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 实名认证查询
     */
    public static Observable<AuthInfo> authenticateResult() {
        return API_SERVICE.authenticateResult(getToken()).compose(RxSchedulers.<AuthInfo>io_main());
    }

    /**
     * 获取区域列表
     */
    public static Observable<List<RegionNode>> getRegion(String regionCode) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(regionCode)) {
            params.put("region_code", regionCode);
        }
        return API_SERVICE.getRegion(getToken(), params).compose(RxSchedulers.<List<RegionNode>>io_main());
    }

    /**
     * 获取轮播图
     */
    public static Observable<List<BannerItem>> getBanner() {
        return API_SERVICE.getBanner().compose(RxSchedulers.<List<BannerItem>>io_main());
    }

    /**
     * 获取事件类型列表
     *
     * @param type 按类型
     * @param name 按事项名字
     */
    public static Observable<List<EventTypeItem>> getEventTypeList(String type, String name) {
        Map<String, String> params = new HashMap<>();

        if (!TextUtils.isEmpty(type)) {
            params.put("type", type);
        }
        if (!TextUtils.isEmpty(name)) {
            params.put("name", name);
        }
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return API_SERVICE.getEventTypeListNoToken(params).compose(RxSchedulers.<List<EventTypeItem>>io_main());
        } else {
            return API_SERVICE.getEventTypeList(token, params).compose(RxSchedulers.<List<EventTypeItem>>io_main());
        }

    }

    /**
     * 提交事件
     */
    public static Observable<String> submitEvent(List<MultipartBody.Part> parts) {
        return API_SERVICE.submitEvent(getToken(), parts).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 获取事件列表
     */
    public static Observable<EventList> getEventList() {
        return API_SERVICE.getEventList(getToken()).compose(RxSchedulers.<EventList>io_main());
    }

    /**
     * 获取事件详情和进度
     */
    public static Observable<EventDetails> getEventDetails(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("eid", id);
        return API_SERVICE.getEventDetails(getToken(), params).compose(RxSchedulers.<EventDetails>io_main());
    }

    /**
     * 获取被驳回事件的数据
     */
    public static Observable<EventTypeItem> getRejectEventData(String id) {
        return API_SERVICE.getRejectEventData(getToken(), id).compose(RxSchedulers.<EventTypeItem>io_main());
    }

    /**
     * 被驳回事件再次提交
     */
    public static Observable<String> applyRejectEvent(List<MultipartBody.Part> parts) {
        return API_SERVICE.applyRejectEvent(getToken(), parts).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 获取收货地址
     */
    public static Observable<List<RecAddress>> getRecAddress() {
        return API_SERVICE.getRecAddress(getToken()).compose(RxSchedulers.<List<RecAddress>>io_main());
    }

    /**
     * 新增收货地址
     */
    public static Observable<String> addRecAddress(String province, String city, String county,
                                                   String street, String detailsAddr, String name,
                                                   String phone, String isDefault) {
        Map<String, String> params = new HashMap<>();
        params.put("province", province);
        params.put("city", city);
        params.put("county", county);
        params.put("town", street);
        params.put("full_addr", detailsAddr);
        params.put("name", name);
        params.put("tel", phone);
        params.put("isdefault", isDefault);
        return API_SERVICE.addRecAddress(getToken(), params).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 编辑收货地址
     */
    public static Observable<String> editRecAddress(String id, String province, String city, String county,
                                                    String street, String detailsAddr, String name,
                                                    String phone, String isDefault) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("province", province);
        params.put("city", city);
        params.put("county", county);
        params.put("town", street);
        params.put("full_addr", detailsAddr);
        params.put("name", name);
        params.put("tel", phone);
        params.put("isdefault", isDefault);
        return API_SERVICE.editRecAddress(getToken(), params).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 删除收货地址
     */
    public static Observable<String> deleteRecAddress(String id) {
        return API_SERVICE.deleteRecAddress(getToken(), id).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 获取未读消息数量
     */
    public static Observable<String> getUnreadMsgNum() {
        return API_SERVICE.getUnreadMsgNum(getToken()).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 获取通知消息列表
     */
    public static Observable<List<MessageItem>> getMsgList() {
        return API_SERVICE.getMsgList(getToken()).compose(RxSchedulers.<List<MessageItem>>io_main());
    }

    /**
     * 设置消息已读
     */
    public static Observable<String> setMsgRead(String id) {
        return API_SERVICE.setMsgRead(getToken(), id).compose(RxSchedulers.<String>io_main());
    }

    /**
     * 检查新版本
     */
    public static Observable<VersionInfo> checkApkVersion() {
        return API_SERVICE.checkApkVersion().compose(RxSchedulers.<VersionInfo>io_main());
    }
}
