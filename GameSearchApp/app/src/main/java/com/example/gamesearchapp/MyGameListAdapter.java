package com.example.gamesearchapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyGameListAdapter extends BaseAdapter implements Filterable
{
    ArrayList<Game> games;
    public ArrayList<Game> currentList;
    MyGameFilterHelper myFilter;

    private Context mContext;//variable for context
    int mResource; //variable for resource

    //custom adapter
    public MyGameListAdapter(Context context, int resource, ArrayList<Game> objects) {
        mContext = context;
        mResource = resource;
        this.games = objects;
        this.currentList = objects;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int i) {
        return games.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //customizing the getView function
    @NonNull
    @Override
    public View getView(int index, @Nullable View switchView, @NonNull ViewGroup parent)
    {
        if(switchView == null) {
            // setting the inflater
            LayoutInflater inflater = LayoutInflater.from(mContext);
            switchView = inflater.inflate(mResource, parent, false);
        }

        final Game gItem= (Game) this.getItem(index);

        String gameGenre= gItem.getGenre(); //game's genre
        String gameImgURL = gItem.getImgURL(); //game's image url
        String gameSubgenre = gItem.getSubgenre(); //game's subgenre
        final String gameTitle = gItem.getTitle(); //game's title
        String gamePid = gItem.getPid(); //game's package id
        String gameRating = gItem.getRating(); //game's rating
        String gameRCount = gItem.getrCount(); //game's rcount

        //linking TextView objects to textView variables
        TextView genreView = switchView.findViewById(R.id.genre_category);
        ImageView imgURLView = switchView.findViewById(R.id.game_icon);
        TextView subgenreView = switchView.findViewById(R.id.subgenre_category);
        TextView titleView = switchView.findViewById(R.id.game_title);
        TextView pidView = switchView.findViewById(R.id.package_id);
        TextView ratingView = switchView.findViewById(R.id.score_rating);
        TextView rCountView = switchView.findViewById(R.id.rating_count);


        // fill the views with actual data
        genreView.setText(gameGenre);
        subgenreView.setText(gameSubgenre);
        titleView.setText(gameTitle);
        pidView.setText(gamePid);
        ratingView.setText(gameRating);
        rCountView.setText(gameRCount);

        if(gameImgURL != null && gameImgURL.length() > 0)
        {
            Glide.with(mContext)
                    .load(gameImgURL)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imgURLView);
        }

        switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, gameTitle, Toast.LENGTH_SHORT).show();
            }
        });

        return switchView;
    }

    public void setGames(ArrayList<Game> filteredGames) {
        this.games=filteredGames;
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyGameFilterHelper(currentList, this, mContext);
        }

        return myFilter;
    }

    public void refresh(){
        this.notifyDataSetChanged();
    }
}