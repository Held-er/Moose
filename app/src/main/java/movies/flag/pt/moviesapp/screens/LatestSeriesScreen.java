package movies.flag.pt.moviesapp.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.Series;
import movies.flag.pt.moviesapp.http.entities.SeriesResponse;
import movies.flag.pt.moviesapp.http.requests.GetLatestSeriesAsyncTask;
import movies.flag.pt.moviesapp.http.requests.GetSeriesAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by Ricardo Neves on 20/10/2016.
 */

public class LatestSeriesScreen extends Screen {

    //private SearchView mSearchView;
    private ListView mLatestSeriesListView;
    private ProgressBar mLoaderView;

    private LatestSeriesAdapter mLatestSeriesAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executeRequestLatestSeries();

        setContentView(R.layout.movies_series_screen);

        findViews();
        addListeners();

    }

    private void findViews() {
        //mSearchView = (SearchView) findViewById(R.id.movies_series_screen_search);
        mLatestSeriesListView = (ListView) findViewById(R.id.movies_series_screen_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mLoaderView = (ProgressBar) findViewById(R.id.movies_series_screen_loader);
    }


    private void addListeners() {
        mLoaderView.setVisibility(View.VISIBLE);
        executeRequestLatestSeries();

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        executeRequestLatestSeries();
                    }
                }
        );


    }

    private void executeRequestLatestSeries() {
        new GetLatestSeriesAsyncTask(this) {

            @Override
            protected void onResponseSuccess(SeriesResponse seriesResponse) {
                DLog.d(tag, "onResponseSuccess " + seriesResponse);
                mLatestSeriesAdapter = new LatestSeriesAdapter(LatestSeriesScreen.this, seriesResponse.getSeries());
                mLatestSeriesListView.setAdapter(mLatestSeriesAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
                mLoaderView.setVisibility(View.GONE);
            }

            @Override
            protected void onNetworkError() {
                DLog.d(tag, "onNetworkError ");
                // Here i know that some error occur when processing the request,
                // possible my internet connection if turned off
            }
        }.execute();
    }

    /**
     * Adapter
     **/
    //validate constructor
    private class LatestSeriesAdapter extends ArrayAdapter<Series> {
        public LatestSeriesAdapter(Context context, List<Series> series) {
            super(context, 0, series);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            LatestSeriesHolder holder;
            // connection to AdapterItem

            if (v == null) {
                //inflate new views
                v = LayoutInflater.from(LatestSeriesScreen.this).inflate(R.layout.movies_series_item, null);
                holder = new LatestSeriesHolder();
                //uses holder to put findViewById in the if loop
                holder.mSeriesImage = (ImageView) v.findViewById(R.id.movies_series_item_image);
                holder.mSeriesTitle = (TextView) v.findViewById(R.id.movies_series_item_title);
                holder.mSeriesScore = (TextView) v.findViewById(R.id.movies_series_item_score);
                holder.mSeriesDetailButton = (Button) v.findViewById(R.id.movies_series_item_button);
                v.setTag(holder);
            } else {
                //reuse views
                holder = (LatestSeriesHolder) v.getTag();
            }

            final Series series = getItem(position);

            holder.mSeriesImage.setImageResource(R.mipmap.ic_launcher);
            holder.mSeriesTitle.setText(series.getName());
            holder.mSeriesScore.setText(String.valueOf(series.getVoteAverage()));
            holder.mSeriesDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LatestSeriesScreen.this, SeriesDetailsScreen.class);
                    intent.putExtra(SeriesDetailsScreen.SERIES_PARCELABLE, series);
                    startActivity(intent);
                }
            });

            //check if image is already downloaded or not?
            return v;
        }
    }

    //class created to to put findViewById in the if loop
    private class LatestSeriesHolder {
        ImageView mSeriesImage;
        TextView mSeriesTitle;
        TextView mSeriesScore;
        Button mSeriesDetailButton;
    }
}