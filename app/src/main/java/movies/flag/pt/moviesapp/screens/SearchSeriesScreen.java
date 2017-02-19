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
import movies.flag.pt.moviesapp.http.entities.Series;
import movies.flag.pt.moviesapp.http.entities.SeriesResponse;
import movies.flag.pt.moviesapp.http.requests.ExecuteRequestAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by Ricardo Neves on 20/10/2016.
 */

public class SearchSeriesScreen extends Screen {

    private EditText mSearchEditText;
    private Button mSearchButton;
    private ListView mSeriesListView;
    private ProgressBar mLoaderView;

    private SeriesAdapter mSeriesAdapter;

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
        mSeriesListView = (ListView) findViewById(R.id.search_movies_series_screen_list);
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
                executeSearchSeries();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {


                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        executeSearchSeries();
                    }
                }
        );

    }

    private void executeSearchSeries() {
        new GetSearchSeriesAsyncTask(this) {

            @Override
            protected void onResponseSuccess(SeriesResponse seriesResponse) {
                DLog.d(tag, "onResponseSuccess " + seriesResponse);
                mSeriesAdapter = new SeriesAdapter(SearchSeriesScreen.this, seriesResponse.getSeries());
                mSeriesListView.setAdapter(mSeriesAdapter);
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
    private class SeriesAdapter extends ArrayAdapter<Series> {
        public SeriesAdapter(Context context, List<Series> series) {
            super(context, 0, series);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            SeriesHolder holder;
            // connection to AdapterItem

            if (v == null) {
                //inflate new views
                v = LayoutInflater.from(SearchSeriesScreen.this).inflate(R.layout.movies_series_item, null);
                holder = new SeriesHolder();
                //uses holder to put findViewById in the if loop
                holder.mSeriesImage = (ImageView) v.findViewById(R.id.movies_series_item_image);
                holder.mSeriesTitle = (TextView) v.findViewById(R.id.movies_series_item_title);
                holder.mSeriesScore = (TextView) v.findViewById(R.id.movies_series_item_score);
                holder.mSeriesDetailButton = (Button) v.findViewById(R.id.movies_series_item_button);
                v.setTag(holder);
            } else {
                //reuse views
                holder = (SearchSeriesScreen.SeriesHolder) v.getTag();
            }

            final Series series = getItem(position);

            holder.mSeriesImage.setImageResource(R.mipmap.ic_launcher);
            holder.mSeriesTitle.setText(series.getName());
            holder.mSeriesScore.setText(String.valueOf(series.getVoteAverage()));
            holder.mSeriesDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchSeriesScreen.this, SeriesDetailsScreen.class);
                    intent.putExtra(SeriesDetailsScreen.SERIES_PARCELABLE, series);
                    startActivity(intent);
                }
            });

            //check if image is already downloaded or not?
            return v;
        }
    }

    //class created to to put findViewById in the if loop
    private class SeriesHolder {
        ImageView mSeriesImage;
        TextView mSeriesTitle;
        TextView mSeriesScore;
        Button mSeriesDetailButton;
    }

    public abstract class GetSearchSeriesAsyncTask extends ExecuteRequestAsyncTask<SeriesResponse> {

        private static final String PATH = "/search/tv";
        public static final String SEARCH_KEY = "query";
        private static final String LANGUAGE_KEY = "language";
        private static final String LANGUAGE_VALUE = "en";

        public GetSearchSeriesAsyncTask(Context context) {
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
        protected Class<SeriesResponse> getResponseEntityClass() {
            return SeriesResponse.class;
        }
    }

}
