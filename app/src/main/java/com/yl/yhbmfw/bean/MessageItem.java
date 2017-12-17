package com.yl.yhbmfw.bean;

/**
 * @author Yang Shihao
 */

public class MessageItem {

    /**
     * msg_id : 1
     * msg_title : 冬至吃饺子
     * msg_content : 饺子….
     * msg_create_time : 2017-11-02 16:21:17
     * real_name : 杨世豪
     * msg_type  : 1
     * read_status  : y
     */

    private String msg_id;
    private String msg_title;
    private String msg_content;
    private String msg_create_time;
    private String real_name;
    private String msg_type;
    private String read_status;

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public void setMsg_title(String msg_title) {
        this.msg_title = msg_title;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public void setMsg_create_time(String msg_create_time) {
        this.msg_create_time = msg_create_time;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public String getMsg_title() {
        return msg_title;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public String getMsg_create_time() {
        return msg_create_time;
    }

    public String getReal_name() {
        return real_name;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public String getRead_status() {
        return read_status;
    }

    public boolean isUnread() {
        return "n".equals(read_status);
    }
}
