package com.yl.yhbmfw.bean;

import java.io.Serializable;

/**
 * @author Yang Shihao
 *         收货地址
 */

public class RecAddress implements Serializable{

    /**
     * id : 1
     * isdefault : 1
     * full_addr : 收货地址1
     * province : 浙江省
     * city : 杭州市
     * county : 西湖区
     * town : 蒋村街道
     * name : 杨世豪
     * tel : 13563639898
     */

    private String id;
    private String isdefault;
    private String full_addr;
    private String province;
    private String city;
    private String county;
    private String town;
    private String name;
    private String tel;

    public void setId(String id) {
        this.id = id;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public void setFull_addr(String full_addr) {
        this.full_addr = full_addr;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getId() {
        return id;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public String getFull_addr() {
        return full_addr;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getTown() {
        return town;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getArea() {
        return province + city + county;
    }

    public String getAddr() {
        return province + city + county + full_addr;
    }

    public boolean isDefault() {
        return "1".equals(isdefault);
    }
}
