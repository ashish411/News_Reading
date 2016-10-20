package com.example.ashis.newsapp;

/**
 * Created by ashis on 10/19/2016.
 */
public class News {
    private String mNewsTitle;
    private String mUrl;

    public News(String mNewsTitle, String mUrl) {
        this.mNewsTitle = mNewsTitle;
        this.mUrl = mUrl;
    }

    public String getmNewsTitle() {
        return mNewsTitle;
    }

    public String getmUrl() {
        return mUrl;
    }
}
