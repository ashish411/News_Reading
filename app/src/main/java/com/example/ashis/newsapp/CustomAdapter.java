package com.example.ashis.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashis on 10/19/2016.
 */
public class CustomAdapter extends ArrayAdapter<News> {
    public CustomAdapter(Context context, ArrayList<News> list) {
        super(context,0,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null)
        {
            rootView= LayoutInflater.from(getContext()).inflate(R.layout.custom_layout,parent,false);
        }
        final News currentNews = getItem(position);
        TextView titleText = (TextView)rootView.findViewById(R.id.textView_title);
        titleText.setText(currentNews.getmNewsTitle());
        return rootView;
    }
}
