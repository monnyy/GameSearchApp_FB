package com.example.gamesearchapp;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONDownloader {

    private static final String JSON_DATA_URL = "https://nz1sdgxjnf.execute-api.ca-central-1.amazonaws.com/Prod/mistplay-search";
    private final Context mContext;

    public JSONDownloader(Context c) {
        this.mContext = c;
    }

    // Retrieves JSON Data
    public ArrayList<Game> retrieve(int resultsPerFetch, final ProgressBar myProgressBar) {

        final ArrayList<Game> downloadedData = new ArrayList<>();
        myProgressBar.setIndeterminate(true);
        myProgressBar.setVisibility(View.VISIBLE);

        AndroidNetworking.get(JSON_DATA_URL)
                .addQueryParameter("requiredResults", String.valueOf(resultsPerFetch))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jo;
                        Game currGame;
                        try {
                            for(int i = 0; i < response.length(); i++) {

                                jo = response.getJSONObject(i);

                                String genre =  jo.getString("genre");
                                String imgURL = jo.getString("imgURL");
                                String subgenre = jo.getString("subgenre");
                                String title = jo.getString("title");
                                String pid = jo.getString("pid");
                                String rating = jo.getString("rating");
                                String rCount = jo.getString("rCount");

                                // game object
                                currGame = new Game(genre, imgURL, subgenre, title, pid, rating, rCount);

                                downloadedData.add(currGame);
                            }
                            myProgressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            myProgressBar.setVisibility(View.GONE);
                            Toast.makeText(mContext, "Experiencing difficulty in parsing the response obtained,\n " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError e) {
                        myProgressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                        Toast.makeText(mContext, "Failed to retrieve data,\nplease contact your administrator.", Toast.LENGTH_LONG).show();
                    }
                });
        return downloadedData;
    }
}
