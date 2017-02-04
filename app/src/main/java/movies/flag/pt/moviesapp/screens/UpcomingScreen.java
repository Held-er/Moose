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
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.entities.UpcomingMovies;
import movies.flag.pt.moviesapp.http.entities.UpcomingMoviesResponse;
import movies.flag.pt.moviesapp.http.requests.GetUpcomingMoviesAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by Ricardo Neves on 20/10/2016.
 */

public class UpcomingScreen extends Screen {

    private ListView mUpcomingListView;

    private UpcomingAdapter mUpcomingAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executeRequestUpcoming();

        setContentView(R.layout.movies_series_screen);

        findViews();
        addListeners();

    }

    private void findViews() {
        mUpcomingListView = (ListView) findViewById(R.id.movies_series_screen_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
    }


    private void addListeners() {
        executeRequestUpcoming();

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {


                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        executeRequestUpcoming();
                    }
                }
        );
    }

    private void executeRequestUpcoming() {
        new GetUpcomingMoviesAsyncTask(this) {

            @Override
            protected void onResponseSuccess(UpcomingMoviesResponse upcomingMoviesResponse) {
                DLog.d(tag, "onResponseSuccess " + upcomingMoviesResponse);
                mUpcomingAdapter = new UpcomingAdapter(UpcomingScreen.this, upcomingMoviesResponse.getUpcomingMovies());
                mUpcomingListView.setAdapter(mUpcomingAdapter);
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
    private class UpcomingAdapter extends ArrayAdapter<UpcomingMovies> {
        public UpcomingAdapter(Context context, List<UpcomingMovies> upcoming) {
            super(context, 0, upcoming);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            UpcomingHolder holder;
            // connection to AdapterItem

            if (v == null) {
                //inflate new views
                v = LayoutInflater.from(UpcomingScreen.this).inflate(R.layout.movies_series_item, null);
                holder = new UpcomingHolder();
                //uses holder to put findViewById in the if loop
                holder.mMovieImage = (ImageView) v.findViewById(R.id.movies_series_item_image);
                holder.mMovieTitle = (TextView) v.findViewById(R.id.movies_series_item_title);
                holder.mMovieScore = (TextView) v.findViewById(R.id.movies_series_item_score);
                holder.mMovieDetailButton = (Button) v.findViewById(R.id.movies_series_item_button);
                v.setTag(holder);
            } else {
                //reuse views
                holder = (UpcomingHolder) v.getTag();
            }

            UpcomingMovies upcoming = getItem(position);

            holder.mMovieImage.setImageResource(R.mipmap.ic_launcher);
            holder.mMovieTitle.setText(upcoming.getTitle());
            holder.mMovieScore.setText(String.valueOf(upcoming.getVoteAverage()));

            //check if image is already downloaded or not?
            return v;
        }
    }

    //class created to to put findViewById in the if loop
    private class UpcomingHolder {
        ImageView mMovieImage;
        TextView mMovieTitle;
        TextView mMovieScore;
        Button mMovieDetailButton;
    }
}
