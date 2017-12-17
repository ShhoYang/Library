package com.yl.yhbmfw.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Yang Shihao
 */

public class EventList {

    /**
     * total : 6
     * rows : [{"real_name":"张成良","code":"20171017GA17031800110001","add_time":"2017-10-17 09:19:40","status":"受理中","name":"婚育情况","id":"2","oper":"2","item_id":"11"},{"real_name":"张成良","code":"20171017GA17039208740027","add_time":"2017-10-17 09:32:00","status":"受理中","name":"婚育情况","id":"3","oper":"3","item_id":"11"},{"real_name":"张成良","code":"20171017GA17042650180022","add_time":"2017-10-17 09:37:45","status":"受理中","name":"婚育情况","id":"4","oper":"4","item_id":"11"},{"real_name":"张成良","code":"20171017GA17055545890072","add_time":"2017-10-17 09:59:14","status":"受理中","name":"婚育情况","id":"5","oper":"5","item_id":"11"},{"real_name":"张成良","code":"20171017GA17056653190049","add_time":"2017-10-17 10:01:05","status":"受理中","name":"婚育情况","id":"6","oper":"6","item_id":"11"}]
     */

    private String total;
    private List<EventItem> rows;

    public void setTotal(String total) {
        this.total = total;
    }

    public void setRows(List<EventItem> rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public List<EventItem> getRows() {
        return rows;
    }

    public static class EventItem implements Serializable {
        /**
         * real_name : 张成良
         * code : 20171017GA17031800110001
         * add_time : 2017-10-17 09:19:40
         * status : 受理中
         * name : 婚育情况
         * id : 2
         * oper : 2
         * item_id : 11
         */

        private String real_name;
        private String code;
        private String add_time;
        private String status;
        private String name;
        private String id;
        private String oper;
        private String item_id;

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setOper(String oper) {
            this.oper = oper;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getReal_name() {
            return real_name;
        }

        public String getCode() {
            return code;
        }

        public String getAdd_time() {
            return add_time;
        }

        public String getStatus() {
            return status;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getOper() {
            return oper;
        }

        public String getItem_id() {
            return item_id;
        }

        public boolean isReject() {
            return "驳回".equals(status);
        }

        public boolean isFinished() {
            return ("待送达".equals(status) || "已办结".equals(status));
        }

    }
}
