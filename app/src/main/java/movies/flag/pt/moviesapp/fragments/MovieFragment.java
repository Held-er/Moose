package movies.flag.pt.moviesapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.adapters.MovieRecyclerAdapter;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.requests.GetNowPlayingMoviesAsyncTask;
import movies.flag.pt.moviesapp.http.requests.GetUpcomingMoviesAsyncTask;
import movies.flag.pt.moviesapp.screens.SearchMoviesScreen;

public class MovieFragment extends BaseFragment {

    private TextView mLabelList1;
    private TextView mLabelList2;

    private RecyclerView mMovieRecyclerView1;
    private RecyclerView mMovieRecyclerView2;

    private RecyclerView.LayoutManager mMovieLayoutManager1;
    private RecyclerView.LayoutManager mMovieLayoutManager2;

    private MovieRecyclerAdapter mMovieAdapter1;
    private MovieRecyclerAdapter mMovieAdapter2;

    private ProgressBar mLoaderView;
    private Button mSearchButton;
    private ImageView mMooseIcon;
    private CoordinatorLayout mCoordinatorLayout;

    private Context mContext;

    public static MovieFragment newInstance() {
        Bundle args = new Bundle();
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();

        View v = inflater.inflate(R.layout.movies_series_recycler_screen, null);
        findViews(v);
        addListeners();

        return v;
    }

    private void findViews(View v) {
        Typeface black_font = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/prelo_w01_black.ttf");
        Typeface book_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/prelo_w01_book.ttf");

        mSearchButton = (Button) v.findViewById(R.id.movies_series_recycler_screen_search_button);
        mSearchButton.setText(R.string.movies_fragment_search_movies);
        mSearchButton.setTypeface(book_font);

        mLabelList1 = (TextView) v.findViewById(R.id.movies_series_recycler_screen_label_list1);
        mLabelList1.setText(R.string.movies_fragment_now_playing);
        mLabelList1.setTypeface(black_font);

        mLabelList2 = (TextView) v.findViewById(R.id.movies_series_recycler_screen_label_list2);
        mLabelList2.setText(R.string.movies_fragment_upcoming);
        mLabelList2.setTypeface(black_font);

        mMovieRecyclerView1 = (RecyclerView) v.findViewById(R.id.movies_series_recycler_screen_1);
        mMovieRecyclerView2 = (RecyclerView) v.findViewById(R.id.movies_series_recycler_screen_2);
        mLoaderView = (ProgressBar) v.findViewById(R.id.movies_series_recycler_screen_loader);
        mMooseIcon = (ImageView) v.findViewById(R.id.movies_series_recycler_moose_icon);
        mCoordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.movies_series_recycler_screen_coordinator_layout);
    }

    private void addListeners() {
        mLoaderView.setVisibility(View.VISIBLE);
        mMooseIcon.setVisibility(View.VISIBLE);
        executeRequestNowPlaying();
        executeRequestUpcoming();

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchMoviesScreen.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });
    }

    private void executeRequestNowPlaying() {
        new GetNowPlayingMoviesAsyncTask(getActivity()) {

            @Override
            protected void onResponseSuccess(MoviesResponse moviesResponse) {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mMooseIcon.setVisibility(View.INVISIBLE);
                mLoaderView.setVisibility(View.GONE);
                mLabelList1.setVisibility(View.VISIBLE);
                mSearchButton.setVisibility(View.VISIBLE);

                mMovieRecyclerView1.setHasFixedSize(false);

                // use a linear layout manager
                mMovieLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mMovieRecyclerView1.setLayoutManager(mMovieLayoutManager1);

                // specify an adapter (see also next example)
                mMovieAdapter1 = new MovieRecyclerAdapter(mContext, moviesResponse.getMovies());
                mMovieRecyclerView1.setAdapter(mMovieAdapter1);
            }
            @Override
            protected void onNetworkError() {
                // Here i know that some error occur when processing the request,
                // possible my internet connection if turned off
                Snackbar snackbar = Snackbar
                        .make(mCoordinatorLayout, R.string.on_network_error_movies, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.snackbar_retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar = Snackbar.make(mCoordinatorLayout, R.string.on_network_error_fetching, Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                executeRequestNowPlaying();
                                executeRequestUpcoming();
                            }
                        });
                snackbar.show();
            }
        }.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        executeRequestNowPlaying();
        executeRequestUpcoming();
    }

    private void executeRequestUpcoming() {
        new GetUpcomingMoviesAsyncTask(getActivity()) {

            @Override
            protected void onResponseSuccess(MoviesResponse moviesResponse) {
                mLabelList2.setVisibility(View.VISIBLE);
                mLoaderView.setVisibility(View.GONE);
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mMovieRecyclerView2.setHasFixedSize(false);

                // use a linear layout manager
                mMovieLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mMovieRecyclerView2.setLayoutManager(mMovieLayoutManager2);

                // specify an adapter (see also next example)
                mMovieAdapter2 = new MovieRecyclerAdapter(mContext, moviesResponse.getMovies());
                mMovieRecyclerView2.setAdapter(mMovieAdapter2);
            }
            @Override
            protected void onNetworkError() {
                // Here i know that some error occur when processing the request,
                // possible my internet connection if turned off
            }
        }.execute();
    }
}