package movies.flag.pt.moviesapp.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.Series;
import movies.flag.pt.moviesapp.http.entities.SeriesResponse;
import movies.flag.pt.moviesapp.http.requests.GetSeriesAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by Ricardo Neves on 20/10/2016.
 */

public class SeriesScreen extends Screen {

    private ListView mSeriesListView;

    private SeriesAdapter mSeriesAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executeRequestExample();

        setContentView(R.layout.movies_series_screen);

        findViews();
        addListeners();
    }

    private void findViews() {
        mSeriesListView = (ListView) findViewById(R.id.movies_series_screen_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
    }


    private void addListeners() {
        executeRequestSeries();

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {


                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        executeRequestSeries();
                    }
                }
        );

    }

    private void executeRequestSeries() {
        new GetSeriesAsyncTask(this) {

            @Override
            protected void onResponseSuccess(SeriesResponse seriesResponse) {
                DLog.d(tag, "onResponseSuccess " + seriesResponse);
                mSeriesAdapter = new SeriesAdapter(SeriesScreen.this, seriesResponse.getSeries());
                mSeriesListView.setAdapter(mSeriesAdapter);
            }

            @Override
            protected void onNetworkError() {
                DLog.d(tag, "onNetworkError ");
                // Here i know that some error occur when processing the request,
                // possible my internet connection if turned off
            }
        }.execute();
    }

    private void executeRequestExample() {
        // Example to request get now playing movies
        new GetSeriesAsyncTask(this) {

            @Override
            protected void onResponseSuccess(SeriesResponse seriesResponse) {
                DLog.d(tag, "onResponseSuccess " + seriesResponse);
                mSeriesAdapter = new SeriesAdapter(SeriesScreen.this, seriesResponse.getSeries());
                mSeriesListView.setAdapter(mSeriesAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
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
    private class SeriesAdapter extends ArrayAdapter<Series> {
        public SeriesAdapter(Context context, List<Series> series) {
            super(context, 0, series);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            SeriesScreen.SeriesHolder holder;
            // connection to AdapterItem

            if (v == null) {
                //inflate new views
                v = LayoutInflater.from(SeriesScreen.this).inflate(R.layout.movies_series_item, null);
                holder = new SeriesScreen.SeriesHolder();
                //uses holder to put findViewById in the if loop
                holder.mMovieImage = (ImageView) v.findViewById(R.id.movies_series_item_image);
                holder.mMovieTitle = (TextView) v.findViewById(R.id.movies_series_item_title);
                holder.mMovieScore = (TextView) v.findViewById(R.id.movies_series_item_score);
                holder.mMovieDetailButton = (Button) v.findViewById(R.id.movies_series_item_button);
                v.setTag(holder);
            } else {
                //reuse views
                holder = (SeriesScreen.SeriesHolder) v.getTag();
            }

            Series series = getItem(position);

            holder.mMovieImage.setImageResource(R.mipmap.ic_launcher);
            holder.mMovieTitle.setText(series.getName());
            holder.mMovieScore.setText(String.valueOf(series.getVoteAverage()));

            //check if image is already downloaded or not?
            return v;
        }
    }

    //class created to to put findViewById in the if loop
    private class SeriesHolder {
        ImageView mMovieImage;
        TextView mMovieTitle;
        TextView mMovieScore;
        Button mMovieDetailButton;
    }
}
