package movies.flag.pt.moviesapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import movies.flag.pt.moviesapp.R;

/**
 * Created by Helder Silva on 28/01/2017.
 */

public class MainScreen extends Screen {

    private Button mNowPlayingButton;
    private Button mUpcomingButton;
    private Button mSearchMoviesButton;
    private Button mSeriesButton;
    private Button mLatestSeriesButton;
    private Button mSearchSeriesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        findViews();
        setListeners();
    }

    private void findViews() {
        mNowPlayingButton = (Button) findViewById(R.id.activity_main_now_playing_button);
        mUpcomingButton = (Button) findViewById(R.id.activity_main_upcoming_button);
        mSearchMoviesButton = (Button) findViewById(R.id.activity_main_search_movies_button);
        mSeriesButton = (Button) findViewById(R.id.activity_main_series_button);
        mLatestSeriesButton = (Button) findViewById(R.id.activity_main_latest_series_button);
        mSearchSeriesButton = (Button) findViewById(R.id.activity_main_search_series_button);
    }

    private void setListeners() {
        mNowPlayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, NowPlayingScreen.class));
            }
        });

        mUpcomingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, UpcomingScreen.class));
            }
        });

        mSearchMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, SearchMoviesScreen.class));
            }
        });

        mSeriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, SeriesScreen.class));
            }
        });

        mLatestSeriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, LatestSeriesScreen.class));
            }
        });

        mSearchSeriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, SearchSeriesScreen.class));
            }
        });
    }
}

