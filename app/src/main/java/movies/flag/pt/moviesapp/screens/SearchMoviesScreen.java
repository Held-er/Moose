package movies.flag.pt.moviesapp.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.requests.ExecuteRequestAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by Ricardo Neves on 20/10/2016.
 */

public class SearchMoviesScreen extends Screen {

    private EditText mSearchEditText;
    private Button mSearchButton;
    private ListView mNowPlayingListView;
    private ProgressBar mLoaderView;

    private NowPlayingAdapter mNowPlayingAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_movies_series_screen);

        findViews();
        addListeners();

    }

    private void findViews() {
        mSearchEditText = (EditText) findViewById(R.id.search_movies_series_edit_text);
        mSearchButton = (Button) findViewById(R.id.search_movies_series_button);
        mNowPlayingListView = (ListView) findViewById(R.id.search_movies_series_screen_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.search_movies_series_screen_swiperefresh);
        mLoaderView = (ProgressBar) findViewById(R.id.search_movies_series_screen_loader);
    }


    private void addListeners() {

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mSearchButton.setEnabled(true);
                    mSearchButton.setAlpha(1f);
                } else {
                    mSearchButton.setEnabled(false);
                    mSearchButton.setAlpha(0.5f);
                }
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(SearchMoviesScreen.this, GetSearchMoviesAsyncTask.class);
                //intent.putExtra(GetSearchMoviesAsyncTask.SEARCH_KEY, mSearchEditText.toString());
                mLoaderView.setVisibility(View.VISIBLE);
                executeRequestSearchMovies();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        executeRequestSearchMovies();
                    }
                }
        );


    }

    private void executeRequestSearchMovies() {
        new GetSearchMoviesAsyncTask(this) {

            @Override
            protected void onResponseSuccess(MoviesResponse moviesResponse) {
                DLog.d(tag, "onResponseSuccess " + moviesResponse);
                mNowPlayingAdapter = new NowPlayingAdapter(SearchMoviesScreen.this, moviesResponse.getMovies());
                mNowPlayingListView.setAdapter(mNowPlayingAdapter);
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
                v = LayoutInflater.from(SearchMoviesScreen.this).inflate(R.layout.movies_series_item, null);
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

            final Movie movie = getItem(position);

            holder.mMovieImage.setImageResource(R.mipmap.ic_launcher);
            holder.mMovieTitle.setText(movie.getTitle());
            holder.mMovieScore.setText(String.valueOf(movie.getVoteAverage()));
            holder.mMovieDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchMoviesScreen.this, NowPlayingDetailsScreen.class);
                    intent.putExtra(NowPlayingDetailsScreen.MOVIE_PARCELABLE, movie);
                    startActivity(intent);
                }
            });

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

    private abstract class GetSearchMoviesAsyncTask extends ExecuteRequestAsyncTask<MoviesResponse> {

        private static final String PATH = "/search/movie";
        public static final String SEARCH_KEY = "query";
        private static final String LANGUAGE_KEY = "language";
        private static final String LANGUAGE_VALUE = "en";


        public GetSearchMoviesAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected String getPath() {
            return PATH;
        }

        @Override
        protected void addQueryParams(StringBuilder sb) {
            addQueryParam(sb, SEARCH_KEY, mSearchEditText.getText().toString());
        }

        @Override
        protected Class<MoviesResponse> getResponseEntityClass() {
            return MoviesResponse.class;
        }
    }

}
