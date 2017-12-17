package com.yl.yhbmfw.bean;

import java.io.Serializable;

/**
 * @author Yang Shihao
 *         授权信息
 */

public class AuthInfo implements Serializable {

    private static final String AUTH_PASS = "认证成功";
    private static final String AUTH_ING = "认证资料正在审核中";
    private static final String AUTH_FAIL = "认证失败";
    private static final String AUTH_NO_SUBMIT = "未提交认证";


    private String mobile;
    private String real_name;
    private String status;
    private String fail_reason;
    private String card;
    private String region_id;
    private String mini_pics;
    private String pics_front;
    private String pics_back;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getFail_reason() {
        return fail_reason;
    }

    public void setFail_reason(String fail_reason) {
        this.fail_reason = fail_reason;
    }

    public String getCard() {
        return card == null ? "" : card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getMini_pics() {
        return mini_pics;
    }

    public void setMini_pics(String mini_pics) {
        this.mini_pics = mini_pics;
    }

    public String getPics_front() {
        return pics_front;
    }

    public void setPics_front(String pics_front) {
        this.pics_front = pics_front;
    }

    public String getPics_back() {
        return pics_back;
    }

    public void setPics_back(String pics_back) {
        this.pics_back = pics_back;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        if ("认证成功".equals(status)) {
            return real_name;
        }
        return "";
    }

    public boolean authPass(){
        return AUTH_PASS.equals(status);
    }

    public boolean authing(){
        return AUTH_ING.equals(status);
    }

    public boolean authFail(){
        return AUTH_FAIL.equals(status);
    }

    public boolean noSubmit(){
       return AUTH_NO_SUBMIT.equals(status);
    }
}
