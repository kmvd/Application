package com.zhou.demo33333.bean;

import cn.bmob.v3.BmobObject;

public class News extends BmobObject {
    private Integer NewsId;
    private String key1;
    private String title;
    private String URL;

    public News() {
        this.setTableName("News");
    }

    public String getTitle() {
        return title;
    }

    public Integer getNewsId() {
        return NewsId;
    }

    public void setNewsId(Integer newsId) {
        NewsId = newsId;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
