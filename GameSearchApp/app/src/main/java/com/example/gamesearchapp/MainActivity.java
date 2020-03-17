package com.example.gamesearchapp;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected static final String TAG = "Main Activity"; //to log the activity for debugging

    ArrayList <Game> currentList = new ArrayList<>();
    MyGameListAdapter adapter;
    ListView gameList;

    static int countTrack = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameListDisplay();
    }

    // function used for the generate the list
    public void gameListDisplay() {

        Log.d(TAG, "The List Generation event"); // tracking TAG

        final SearchView searchView = findViewById(R.id.search_area);
        final TextView noResultView = findViewById(R.id.alt_text);
        gameList = findViewById(R.id.search_list);

        final ProgressBar footer = new ProgressBar(this);
        gameList.addFooterView(footer);

        searchView.setIconified(true);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });

        gameList.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                this.currentVisibleItemCount = visibleItemCount;
            }

            private void isScrollCompleted() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (!gameList.canScrollList(View.SCROLL_AXIS_VERTICAL) && currentScrollState == SCROLL_STATE_IDLE) {

                        // update count to load new results
                        countTrack += 10;

                        updateListDisplay(noResultView, footer, countTrack);

                        adapter.refresh();

                        // clear the search view
                        searchView.setQuery("", false);
                        searchView.setIconified(true);
                    }
                }
            }
        });

        updateListDisplay(noResultView, footer, countTrack);
    }

    private void updateListDisplay(TextView noResultView, ProgressBar footer, int countTrack) {

        // do the API call
        currentList = new JSONDownloader(MainActivity.this).retrieve(countTrack, footer);

        if (currentList.size() < 0) {
            noResultView.setVisibility(View.VISIBLE);
        }

        adapter = new MyGameListAdapter(this, R.layout.game_item_list, currentList);

        gameList.setAdapter(adapter);
    }
}
