package com.yl.yhbmfw.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * @author Yang Shihao
 */

public class RegionNode implements IPickerViewData {

    private String id;
    private String text;
    private String pid;
    private String code;
    private String level;
    private List<RegionNode> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<RegionNode> getChildren() {
        return children;
    }

    public void setChildren(List<RegionNode> children) {
        this.children = children;
    }

    @Override
    public String getPickerViewText() {
        return text;
    }
}
