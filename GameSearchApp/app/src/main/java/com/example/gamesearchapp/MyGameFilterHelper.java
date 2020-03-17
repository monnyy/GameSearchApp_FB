package com.example.gamesearchapp;

import android.content.Context;
import android.widget.Filter;
import java.util.ArrayList;

public class MyGameFilterHelper extends Filter {
    ArrayList<Game> currentList;
    MyGameListAdapter adapter;
    Context mContext;


    public MyGameFilterHelper(ArrayList<Game> currentList, MyGameListAdapter adapter,Context c) {
        this.currentList = currentList;
        this.adapter = adapter;
        this.mContext = c;
    }

    // Performs actual filtering
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();

        if(constraint != null && constraint.length()>0) {
            constraint = constraint.toString().toLowerCase();
            ArrayList<Game> foundFilters = new ArrayList<>();
            Game currGame;

            for (int i = 0; i < currentList.size(); i++)
            {
                currGame = currentList.get(i);

                // perform the search
                if(currGame.getTitle().toLowerCase().contains(constraint)) {
                    // add if something is found
                    foundFilters.add(currGame);
                }
            }

            // update results of the filtered list
            filterResults.count = foundFilters.size();
            filterResults.values = foundFilters;

        } else {
            // keep the results intact if no item is found
            filterResults.count = currentList.size();
            filterResults.values = currentList;
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.setGames((ArrayList<Game>) filterResults.values);
        adapter.refresh();
    }
}

