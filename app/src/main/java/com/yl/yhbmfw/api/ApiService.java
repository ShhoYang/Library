package com.yl.yhbmfw.api;

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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author Yang Shihao
 * @date 2017/7/29
 */

public interface ApiService {

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("User/getSMSCode")
    Observable<String> getSMSCode(@FieldMap Map<String, String> map);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("User/userRegister")
    Observable<String> register(@FieldMap Map<String, String> map);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("User/signin")
    Observable<User> login(@FieldMap() Map<String, String> map);

    /**
     * 登出
     */
    @POST("User/signout")
    Observable<String> signOut();

    /**
     * 忘记密码
     */
    @FormUrlEncoded
    @POST("resetPasswd")
    Observable<String> forgetPassword(@FieldMap Map<String, String> map);

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("User/changePasswd")
    Observable<String> changePassword(@Field("TOKEN") String token, @FieldMap Map<String, String> map);

    /**
     * 修改用户信息
     */
    @Multipart
    @POST("User/updateUserInfo")
    Observable<String> updateUserInfo(@Part("TOKEN") String token, @Part() List<MultipartBody.Part> list);

    /**
     * 实名认证
     */
    @Multipart
    @POST("User/realNameAuth")
    Observable<String> authenticate(@Part("TOKEN") String token, @Part() List<MultipartBody.Part> list);

    /**
     * 实名认证查询
     */
    @FormUrlEncoded
    @POST("User/queryRealNameAuthstatus")
    Observable<AuthInfo> authenticateResult(@Field("TOKEN") String token);

    /**
     * 获取区域列表
     */
    @FormUrlEncoded
    @POST("User/getRegion")
    Observable<List<RegionNode>> getRegion(@Field("TOKEN") String token, @FieldMap Map<String, String> map);

    /**
     * 获取轮播图
     */
    @POST("AppService/getADList")
    Observable<List<BannerItem>> getBanner();

    /**
     * 获取事件类型列表
     */
    @FormUrlEncoded
    @POST("Event/getItemList")
    Observable<List<EventTypeItem>> getEventTypeList(@Field("TOKEN") String token, @FieldMap Map<String, String> map);

    /**
     * 获取事件类型列表
     */
    @FormUrlEncoded
    @POST("Event/getItemList")
    Observable<List<EventTypeItem>> getEventTypeListNoToken(@FieldMap Map<String, String> map);

    /**
     * 提交事件
     */
    @Multipart
    @POST("Event/applyEvent")
    Observable<String> submitEvent(@Part("TOKEN") String token, @Part() List<MultipartBody.Part> list);

    /**
     * 获取事件列表
     */
    @FormUrlEncoded
    @POST("Event/getEventList")
    Observable<EventList> getEventList(@Field("TOKEN") String token);

    /**
     * 获取事件详情和进度
     */
    @FormUrlEncoded
    @POST("Event/getEventProcess")
    Observable<EventDetails> getEventDetails(@Field("TOKEN") String token, @FieldMap Map<String, String> map);

    /**
     * 获取被驳回事件的数据
     */
    @FormUrlEncoded
    @POST("Event/getRejectEvent")
    Observable<EventTypeItem> getRejectEventData(@Field("TOKEN") String token, @Field("eid") String id);

    /**
     * 被驳回事件再次提交
     */
    @Multipart
    @POST("Event/applyRejectEvent")
    Observable<String> applyRejectEvent(@Part("TOKEN") String token, @Part() List<MultipartBody.Part> list);

    /**
     * 获取收货地址
     */
    @FormUrlEncoded
    @POST("User/getPostalList")
    Observable<List<RecAddress>> getRecAddress(@Field("TOKEN") String token);

    /**
     * 新增收货地址
     */
    @FormUrlEncoded
    @POST("User/addPostalList")
    Observable<String> addRecAddress(@Field("TOKEN") String token, @FieldMap Map<String, String> map);

    /**
     * 编辑收货地址
     */
    @FormUrlEncoded
    @POST("User/editPostalList")
    Observable<String> editRecAddress(@Field("TOKEN") String token, @FieldMap Map<String, String> map);

    /**
     * 删除收货地址
     */
    @FormUrlEncoded
    @POST("User/delPostalList")
    Observable<String> deleteRecAddress(@Field("TOKEN") String token, @Field("id") String id);

    /**
     * 获取未读消息数量
     */
    @FormUrlEncoded
    @POST("AppService/getUnReadMsgNum")
    Observable<String> getUnreadMsgNum(@Field("TOKEN") String token);

    /**
     * 获取通知消息列表
     */
    @FormUrlEncoded
    @POST("AppService/getMsgList")
    Observable<List<MessageItem>> getMsgList(@Field("TOKEN") String token);

    /**
     * 设置消息已读
     */
    @FormUrlEncoded
    @POST("AppService/setMsgRead")
    Observable<String> setMsgRead(@Field("TOKEN") String token, @Field("msg_id") String id);

    /**
     * 检查新版本
     */
    @POST("AppService/getLatestApp")
    Observable<VersionInfo> checkApkVersion();
}
