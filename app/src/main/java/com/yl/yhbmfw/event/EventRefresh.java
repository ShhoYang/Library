package com.yl.yhbmfw.event;

/**
 * @author Yang Shihao
 * @date 2017/9/7
 */

public class EventRefresh {

    public static final int REFRESH_REC_ADDRESS_LIST = 1;
    public static final int REFRESH_REC_ADDRESS = 2;
    public static final int REFRESH_USER_INFO = 3;
    public static final int REFRESH_AUTH_INFO = 4;
    public static final int REFRESH_AUTH_INFO_NET = 5;

    private int refreshLoc;

    public EventRefresh(int refreshLoc) {
        this.refreshLoc = refreshLoc;
    }

    public boolean isRefresh(int refreshLoc) {
        return refreshLoc == this.refreshLoc;
    }
}
