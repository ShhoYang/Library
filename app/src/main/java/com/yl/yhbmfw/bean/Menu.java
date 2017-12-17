package com.yl.yhbmfw.bean;

import com.yl.library.bean.IMenuItem;

/**
 * @author Yang Shihao
 */

public class Menu  implements IMenuItem{

    private int iconId;
    private String text;

    public Menu() {
    }

    public Menu(int iconId, String text) {
        this.iconId = iconId;
        this.text = text;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getMenuText() {
        return text;
    }
}
