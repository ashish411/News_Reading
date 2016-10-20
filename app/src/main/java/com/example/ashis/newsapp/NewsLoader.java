package com.example.ashis.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by ashis on 10/19/2016.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl=url;
    }

    @Override
    public List<News> loadInBackground() {
        URL url = null;
        try {
            url=new URL(mUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String jsonOutput = QueryUtils.makeHttpReq(url);
        List<News> newsArray = QueryUtils.extractFromJson(jsonOutput);
        return newsArray;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
