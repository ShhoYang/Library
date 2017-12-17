package com.yl.yhbmfw.bean;

import android.content.Context;
import android.text.TextUtils;

import com.yl.library.utils.SPUtils;
import com.yl.yhbmfw.Constant;
import com.yl.yhbmfw.R;

/**
 * @author Yang Shihao
 * @date 2017/8/28
 */

public class User {

    private static String EMPTY = "";

    private String id;
    private String token;
    private String phone;
    private String real_name;
    private String nickname;
    private String region_code;
    private String photo;
    private String birthday;
    private String sex;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname == null ? EMPTY : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone == null ? EMPTY : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token == null ? EMPTY : token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Object getPhoto() {
        return TextUtils.isEmpty(photo) ? R.drawable.head_default : Constant.getBaseUrl() + photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBirthday() {
        return birthday == null ? EMPTY : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex == null ? EMPTY : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getReal_name() {
        return real_name == null ? EMPTY : real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getRegion_code() {
        return region_code == null ? EMPTY : region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private static final String ID = "user_id";
    private static final String NAME = "user_rel_name";
    private static final String NICKNAME = "user_nickname";
    private static final String PHONE = "user_phone";
    private static final String TOKEN = "user_token";
    private static final String PHOTO = "user_photo";
    private static final String BIRTHDAY = "user_birthday";
    private static final String SEX = "user_sex";
    private static final String REGION_CODE = "user_region_code";
    private static final String EMAIL = "user_email";

    public static User load(Context context) {
        User user = new User();
        user.setId(SPUtils.get(context, ID, EMPTY));
        user.setReal_name(SPUtils.get(context, NAME, EMPTY));
        user.setNickname(SPUtils.get(context, NICKNAME, EMPTY));
        user.setPhone(SPUtils.get(context, PHONE, EMPTY));
        user.setToken(SPUtils.get(context, TOKEN, EMPTY));
        user.setBirthday(SPUtils.get(context, BIRTHDAY, EMPTY));
        user.setSex(SPUtils.get(context, SEX, EMPTY));
        user.setRegion_code(SPUtils.get(context, REGION_CODE, EMPTY));
        user.setPhoto(SPUtils.get(context, PHOTO, EMPTY));
        user.setPhoto(SPUtils.get(context, EMAIL, EMPTY));

        return user;
    }

    public void save(Context context) {
        SPUtils.put(context, ID, id);
        SPUtils.put(context, NAME, real_name);
        SPUtils.put(context, NICKNAME, nickname);
        SPUtils.put(context, PHONE, phone);
        SPUtils.put(context, TOKEN, token);
        SPUtils.put(context, BIRTHDAY, birthday);
        SPUtils.put(context, REGION_CODE, region_code);
        SPUtils.put(context, SEX, sex);
        SPUtils.put(context, PHOTO, photo);
        SPUtils.put(context, EMAIL, email);
    }
}
