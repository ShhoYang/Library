package com.yl.yhbmfw.bean;

import java.util.List;

/**
 * @author Yang Shihao
 */

public class EventDetails {

    private List<Process> process;
    private List<Material> material;
    private Postal postal;

    public void setProcess(List<Process> process) {
        this.process = process;
    }

    public void setMaterial(List<Material> material) {
        this.material = material;
    }

    public List<Process> getProcess() {
        return process;
    }

    public List<Material> getMaterial() {
        return material;
    }

    public Postal getPostal() {
        return postal;
    }

    public void setPostal(Postal postal) {
        this.postal = postal;
    }

    public static class Process {
        /**
         * name : 受理
         * time : null
         * dealstatus : 0
         * real_name : 别道临
         */

        private String name;
        private String time;
        private String mobile;
        private String dealstatus;
        private String real_name;
        private String level;
        private String deptname;

        public Process() {
        }

        public Process(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setDealstatus(String dealstatus) {
            this.dealstatus = dealstatus;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getName() {
            return name;
        }

        public String getTime() {
            return time;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDealstatus() {
            return dealstatus;
        }

        public String getReal_name() {
            return real_name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getDeptname() {
            return deptname;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }
    }

    public static class Material {
        /**
         * name : 房产证书
         * pic : ./upload/
         */

        private String name;
        private String pic;
        private String form;

        public Material(String name, String pic) {
            this.name = name;
            this.pic = pic;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getName() {
            return name;
        }

        public String getPic() {
            return pic;
        }

        public String getForm() {
            return form;
        }

        public void setForm(String form) {
            this.form = form;
        }
    }

    public static class Postal {
        private String postalname;
        private String postalnumber;
        //0-无需获取 1-邮寄 2-自拿
        private String ispostal;

        public String getPostalname() {
            return postalname;
        }

        public void setPostalname(String postalname) {
            this.postalname = postalname;
        }

        public String getPostalnumber() {
            return postalnumber;
        }

        public void setPostalnumber(String postalnumber) {
            this.postalnumber = postalnumber;
        }

        public String getIspostal() {
            return ispostal;
        }

        public void setIspostal(String ispostal) {
            this.ispostal = ispostal;
        }
    }
}
