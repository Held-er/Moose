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
    private Button mSeriesButton;

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
        mSeriesButton = (Button) findViewById(R.id.activity_main_series_button);
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

        mSeriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, SeriesScreen.class));
            }
        });
    }
}

