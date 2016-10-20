package com.example.ashis.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ashis on 10/19/2016.
 */
public class QueryUtils {

    public static ArrayList<News> extractFromJson(String newsJson){
        if (TextUtils.isEmpty(newsJson)){
            return null;
        }

        ArrayList<News> newsArray = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(newsJson);
            JSONObject responseObject = root.getJSONObject("response");
            JSONArray resultsArray = responseObject.getJSONArray("results");
            if (resultsArray.length()<1 || resultsArray==null){
                return null;
            }
            for (int i=0;i<resultsArray.length();i++)
            {
                JSONObject resultArrayObj = resultsArray.getJSONObject(i);
                String webTitle = "No News";
                if (resultArrayObj.has("webTitle")){
                    webTitle=resultArrayObj.getString("webTitle");
                }
                String webUrl = resultArrayObj.getString("webUrl");
                newsArray.add(new News(webTitle,webUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsArray;
    }

    public static String makeHttpReq(URL url){
        String jsonResponse = "";
        if (url==null){
            return jsonResponse;
        }
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();
            int serverCode = connection.getResponseCode();
            if (serverCode == 200){
                Log.i("server_code",String.valueOf(serverCode));
                inputStream=connection.getInputStream();
                jsonResponse=readFromInputSteam(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null)
                connection.disconnect();
            if (inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return jsonResponse;
    }
    private static String readFromInputSteam(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8");
            BufferedReader bufferedReader  = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line!=null){
                output.append(line);
                line=bufferedReader.readLine();
            }
        }
        return output.toString();
    }
}
