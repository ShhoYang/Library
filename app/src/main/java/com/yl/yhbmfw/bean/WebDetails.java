package com.yl.yhbmfw.bean;

import java.io.Serializable;

/**
 * @author Yang Shihao
 * @date 2017/9/5
 */

public class WebDetails implements Serializable {

    private String title;
    private String content;
    private String thumbnail;
    private String videoUrl;

    public WebDetails() {
    }

    public WebDetails(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public WebDetails(String title, String content, String videoUrl) {
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
    }

    public WebDetails(String title, String content, String thumbnail, String videoUrl) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
