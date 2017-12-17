package com.yl.yhbmfw.bean;

/**
 * @author Yang Shihao
 */

public class BannerItem {


    /**
     * id : 3
     * title : 3
     * icon : /upload/advertise/20171102/1509604096412.jpg
     * content : 3
     * create_time : 2017-11-02 14:28:19
     * real_name : ""
     */

    private String id;
    private String title;
    private String icon;
    private String content;
    private String create_time;
    private String real_name;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public String getContent() {
        return content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getReal_name() {
        return real_name;
    }
}
