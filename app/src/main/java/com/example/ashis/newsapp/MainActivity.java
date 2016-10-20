package com.example.ashis.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private ListView newsList;
    private CustomAdapter mAdapter;
    private TextView emptyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsList=(ListView) findViewById(R.id.list);
        mAdapter=new CustomAdapter(this,new ArrayList<News>());
        newsList.setAdapter(mAdapter);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,this);
        emptyText=(TextView) findViewById(R.id.emptyView);
        newsList.setEmptyView(emptyText);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = (News) mAdapter.getItem(position);
                Uri uri = Uri.parse(currentNews.getmUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_settings){
            Intent settingsIntent = new Intent(this,SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String userInput = sharedPref.getString("pref","0");
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("content.guardianapis.com").appendPath("search")
                .appendQueryParameter("api-key","f8942d92-f1fe-4b4d-ac30-d61f9c225f99")
                .appendQueryParameter("section",userInput);

               // .appendQueryParameter("q","debates");
        String url = builder.build().toString();
        Log.i("url",url);



        return new NewsLoader(this,url);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        mAdapter.clear();
        Log.i("on_load_finished","on load finished method");
        if (data!=null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
        emptyText.setText("No News Found");
        View loadingIndicator = findViewById(R.id.progress);
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
