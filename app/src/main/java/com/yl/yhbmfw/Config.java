package com.yl.yhbmfw;

import android.content.Context;

import com.yl.library.utils.SPUtils;
import com.yl.yhbmfw.bean.AuthInfo;
import com.yl.yhbmfw.bean.User;


/**
 * @author Yang Shihao
 * @date 2017/7/29
 */

public class Config {

    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_LOGIN_STATUS = "login_status";

    private static final String EMPTY = "";

    private String phone;
    private String password;
    private String token;
    private boolean login;
    private User user;
    private AuthInfo authInfo;
    private Context mContext;

    public Config(Context context) {
        mContext = context;
        phone = SPUtils.get(mContext, KEY_PHONE, EMPTY);
        password = SPUtils.get(mContext, KEY_PASSWORD, EMPTY);
        token = SPUtils.get(mContext, KEY_TOKEN, EMPTY);
        login = SPUtils.get(mContext, KEY_LOGIN_STATUS, false);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        SPUtils.put(mContext, KEY_PHONE, phone);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        SPUtils.put(mContext, KEY_PASSWORD, password);
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
        SPUtils.put(mContext, KEY_TOKEN, token);
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
        SPUtils.put(mContext, KEY_LOGIN_STATUS, login);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.user.save(mContext);
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    public void exit() {
        this.user = null;
        this.authInfo = null;
        setLogin(false);
        setToken("");
        SPUtils.remove(mContext, KEY_TOKEN);
    }
}
