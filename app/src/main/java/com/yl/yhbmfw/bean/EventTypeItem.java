package com.yl.yhbmfw.bean;

import android.content.Intent;
import android.text.TextUtils;

import com.luck.picture.lib.PictureSelector;
import com.yl.yhbmfw.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang Shihao
 */

public class EventTypeItem implements Serializable {

    private String id;
    private String name;
    private String dept_name;
    private String type;
    private String desc;
    private String time_limit;
    private String obj;
    private String law_period;
    private String addr;
    private String dates;
    private String fee;
    private String zixun;
    private String tousu;
    private String level;
    private String material_id;
    private String rettype;
    private String terminal;
    private List<EventMaterial> material;
    private List<EventProcess> process;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(String time_limit) {
        this.time_limit = time_limit;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getLaw_period() {
        return law_period;
    }

    public void setLaw_period(String law_period) {
        this.law_period = law_period;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getZixun() {
        return zixun;
    }

    public void setZixun(String zixun) {
        this.zixun = zixun;
    }

    public String getTousu() {
        return tousu;
    }

    public void setTousu(String tousu) {
        this.tousu = tousu;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getRettype() {
        return rettype;
    }

    public void setRettype(String rettype) {
        this.rettype = rettype;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public List<EventMaterial> getMaterial() {
        return material;
    }

    public void setMaterial(List<EventMaterial> material) {
        this.material = material;
    }

    public List<EventProcess> getProcess() {
        return process;
    }

    public void setProcess(List<EventProcess> process) {
        this.process = process;
    }

    public boolean isPc() {
        return "pc".equals(terminal);
    }

    /**
     * 是否可以快递
     */
    public boolean enableExpress() {
        return "1".equals(rettype) || "2".equals(rettype);
    }

    /**
     * type:card 表示与身份证相关，用户无需提交，number表示与证书编号相关，需要用户提交证书编号
     */
    public static class EventMaterial implements Serializable {

        private String id;
        private String code;
        private String name;
        private String type;
        private String material_id;
        private String ismust;
        private String value;
        private String eletable_id;
        private String desc;
        private String default_value;
        private String egpath;
        private String egdesc;
        private String material_val;
        private String modify;
        private String count;
        private String picsmust = "0";
        private String egnum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMaterial_id() {
            return material_id;
        }

        public void setMaterial_id(String material_id) {
            this.material_id = material_id;
        }

        public String getIsmust() {
            return ismust;
        }

        public void setIsmust(String ismust) {
            this.ismust = ismust;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getEletable_id() {
            return eletable_id;
        }

        public void setEletable_id(String eletable_id) {
            this.eletable_id = eletable_id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDefault_value() {
            return default_value;
        }

        public void setDefault_value(String default_value) {
            this.default_value = default_value;
        }

        public String getEgpath() {
            return egpath;
        }

        public void setEgpath(String egpath) {
            this.egpath = egpath;
        }

        public String getEgdesc() {
            return egdesc;
        }

        public void setEgdesc(String egdesc) {
            this.egdesc = egdesc;
        }

        public String getMaterial_val() {
            return material_val;
        }

        public void setMaterial_val(String material_val) {
            this.material_val = material_val;
        }

        public String getModify() {
            return modify;
        }

        public void setModify(String modify) {
            this.modify = modify;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPicsmust() {
            return picsmust;
        }

        public void setPicsmust(String picsmust) {
            this.picsmust = picsmust;
        }

        public String getEgnum() {
            return egnum;
        }

        public void setEgnum(String egnum) {
            this.egnum = egnum;
        }

        public List<ImageItem> getImageList() {
            List<ImageItem> list = new ArrayList<>();
            if (TextUtils.isEmpty(material_val)) {
                return list;
            }
            String[] remoteImages = material_val.split(",");
            int remoteImageCount = remoteImages.length;
            for (int i = 0; i < remoteImageCount; i++) {
                list.add(new ImageItem(Constant.getBaseUrl() + remoteImages[i]));
            }
            return list;
        }

        public int getImageCount() {
            if (TextUtils.isEmpty(egnum) || !TextUtils.isDigitsOnly(egnum)) {
                return 1;
            }
            return Integer.parseInt(egnum);
        }
    }

    public static class EventProcess implements Serializable {
        /**
         * name : 受理
         * deptname : 民政局
         * level : 4
         */

        private String name;
        private String deptname;
        private String level;

        public void setName(String name) {
            this.name = name;
        }

        public void setDeptname(String deptname) {
            this.deptname = deptname;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public String getDeptname() {
            return deptname;
        }

        public String getLevel() {
            return level;
        }
    }

    public static class ImageItem {

        private String localPath;
        private String remotePath;
        private int requestCode;

        public ImageItem() {
        }

        public ImageItem(String remotePath) {
            this.remotePath = remotePath;
        }

        public String getLocalPath() {
            return localPath;
        }

        public void setLocalPath(String localPath) {
            this.localPath = localPath;
        }

        public String getRemotePath() {
            return remotePath;
        }

        public void setRemotePath(String remotePath) {
            this.remotePath = remotePath;
        }

        public int getRequestCode() {
            return requestCode;
        }

        public void setRequestCode(int requestCode) {
            this.requestCode = requestCode;
        }

        public void addImage(Intent data) {
            localPath = PictureSelector.obtainMultipleResult(data).get(0).getPath();
        }

        public boolean hasImage() {
            return !TextUtils.isEmpty(localPath) || !TextUtils.isEmpty(remotePath);
        }

        public String getPath() {
            if (TextUtils.isEmpty(localPath)) {
                return remotePath;
            } else {
                return localPath;
            }
        }

        @Override
        public String toString() {
            return "ImageItem{" +
                    "localPath='" + localPath + '\'' +
                    ", remotePath='" + remotePath + '\'' +
                    ", requestCode=" + requestCode +
                    '}';
        }
    }
}
