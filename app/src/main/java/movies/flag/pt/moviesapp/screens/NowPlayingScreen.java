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
import movies.flag.pt.moviesapp.http.requests.GetNowPlayingMoviesAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by Ricardo Neves on 20/10/2016.
 */

public class NowPlayingScreen extends Screen {

    private ListView mNowPlayingListView;

    private NowPlayingAdapter mNowPlayingAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executeRequestNowPlaying();

        setContentView(R.layout.movies_series_screen);

        findViews();
        addListeners();

    }

    private void findViews() {
        mNowPlayingListView = (ListView) findViewById(R.id.movies_series_screen_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
    }


    private void addListeners() {
        executeRequestNowPlaying();

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {


                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        executeRequestNowPlaying();
                    }
                }
        );
    }

    private void executeRequestNowPlaying() {
        new GetNowPlayingMoviesAsyncTask(this) {

            @Override
            protected void onResponseSuccess(MoviesResponse moviesResponse) {
                DLog.d(tag, "onResponseSuccess " + moviesResponse);
                mNowPlayingAdapter = new NowPlayingAdapter(NowPlayingScreen.this, moviesResponse.getMovies());
                mNowPlayingListView.setAdapter(mNowPlayingAdapter);
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
    private class NowPlayingAdapter extends ArrayAdapter<Movie> {
        public NowPlayingAdapter(Context context, List<Movie> movie) {
            super(context, 0, movie);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            NowPlayingHolder holder;
            // connection to AdapterItem

            if (v == null) {
                //inflate new views
                v = LayoutInflater.from(NowPlayingScreen.this).inflate(R.layout.movies_series_item, null);
                holder = new NowPlayingHolder();
                //uses holder to put findViewById in the if loop
                holder.mMovieImage = (ImageView) v.findViewById(R.id.movies_series_item_image);
                holder.mMovieTitle = (TextView) v.findViewById(R.id.movies_series_item_title);
                holder.mMovieScore = (TextView) v.findViewById(R.id.movies_series_item_score);
                holder.mMovieDetailButton = (Button) v.findViewById(R.id.movies_series_item_button);
                v.setTag(holder);
            } else {
                //reuse views
                holder = (NowPlayingHolder) v.getTag();
            }

            Movie movie = getItem(position);

            holder.mMovieImage.setImageResource(R.mipmap.ic_launcher);
            holder.mMovieTitle.setText(movie.getTitle());
            holder.mMovieScore.setText(String.valueOf(movie.getVoteAverage()));

            //check if image is already downloaded or not?
            return v;
        }
    }

    //class created to to put findViewById in the if loop
    private class NowPlayingHolder {
        ImageView mMovieImage;
        TextView mMovieTitle;
        TextView mMovieScore;
        Button mMovieDetailButton;
    }
}
