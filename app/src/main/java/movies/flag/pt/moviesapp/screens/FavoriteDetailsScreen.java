package movies.flag.pt.moviesapp.screens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;
import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.adapters.MovieRecyclerAdapter;
import movies.flag.pt.moviesapp.databases.FavoritesDb;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.requests.ExecuteRequestAsyncTask;
import movies.flag.pt.moviesapp.utils.BackImageLinks;

/**
 * Details Screen for Movies Saved in the DB
 */

public class FavoriteDetailsScreen extends Screen {

    public static final String FAVORITES_PARCELABLE = "favorites";

    private ImageView mImageView;
    private TextView mTitle;
    private TextView mVoteAverage;
    private TextView mReleaseDate;
    private TextView mDescription;
    private ImageButton mShareButton;
    private ImageButton mFavoriteButton;
    private ImageButton mFavoriteDisabled;
    public FavoritesDb mFavorites;
    private CoordinatorLayout mCoordinatorLayout;

    private TextView mLabelList;
    private RecyclerView mMovieRecyclerView;
    private RecyclerView.LayoutManager mMovieLayoutManager;
    private MovieRecyclerAdapter mMovieAdapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFavorites = getIntent().getParcelableExtra(FAVORITES_PARCELABLE);

        Select existsInDb = Select.from(FavoritesDb.class)
                .where(Condition.prop("M_ITEM_ID").eq(mFavorites.mItemId));
        long countExistInDb = existsInDb.count();

        setContentView(R.layout.movies_series_details);

        mContext = this;

        findViews();

        // check if item exists in DB
        if (countExistInDb == 1) {
            mFavoriteButton.setVisibility(View.INVISIBLE);
            mFavoriteDisabled.setVisibility(View.VISIBLE);
        } else {
            mFavoriteButton.setVisibility(View.VISIBLE);
            mFavoriteDisabled.setVisibility(View.INVISIBLE);
        }

        addListeners();

        executeRequestRecommended();
    }

    private void findViews() {
        //set fonts
        Typeface black_font = Typeface.createFromAsset(getAssets(), "fonts/prelo_w01_black.ttf");
        Typeface book_font = Typeface.createFromAsset(getAssets(), "fonts/prelo_w01_book.ttf");

        //load images
        mImageView = (ImageView) findViewById(R.id.movies_series_details_image);
        BackImageLinks links = new BackImageLinks();
        Picasso.with(mImageView.getContext())
                .load(links.backImage())
                .error(R.drawable.poster_placeholder)
                .into(mImageView);

        mTitle = (TextView) findViewById(R.id.movies_series_details_title);
        mTitle.setText(String.valueOf(mFavorites.mTitle));
        mTitle.setTypeface(black_font);

        mVoteAverage = (TextView) findViewById(R.id.movies_series_details_subtitle1);
        mVoteAverage.setText(String.valueOf(mFavorites.mVoteAverage));
        mVoteAverage.setTypeface(black_font);

        mReleaseDate = (TextView) findViewById(R.id.movies_series_details_subtitle2);
        mReleaseDate.setText(getText(R.string.release_date_label) + " " + String.valueOf(mFavorites.mReleaseDate));
        mReleaseDate.setTypeface(book_font);

        mDescription = (TextView) findViewById(R.id.movies_series_details_description);
        mDescription.setText(String.valueOf(mFavorites.mOverview));
        mDescription.setTypeface(book_font);

        mShareButton = (ImageButton) findViewById(R.id.movies_series_details_share);
        mFavoriteButton = (ImageButton) findViewById(R.id.movies_series_details_favorites);
        mFavoriteDisabled = (ImageButton) findViewById(R.id.movies_series_details_favorites_disabled);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.movies_series_details_coordinator_layout);

        mLabelList = (TextView) findViewById(R.id.movies_series_details_label_list);
        mLabelList.setText(R.string.movie_details_reccommended);
        mLabelList.setTypeface(black_font);

        mMovieRecyclerView = (RecyclerView) findViewById(R.id.movies_series_details_recycler);
    }


    private void executeRequestRecommended() {
        new GetRecommendedMoviesAsyncTask(this) {

            @Override
            protected void onResponseSuccess(MoviesResponse moviesResponse) {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mLabelList.setVisibility(View.VISIBLE);
                mMovieRecyclerView.setHasFixedSize(false);

                // use a linear layout manager
                mMovieLayoutManager = new LinearLayoutManager(FavoriteDetailsScreen.this, LinearLayoutManager.HORIZONTAL, false);
                mMovieRecyclerView.setLayoutManager(mMovieLayoutManager);

                // specify an adapter (see also next example)
                mMovieAdapter = new MovieRecyclerAdapter(mContext, moviesResponse.getMovies());
                mMovieRecyclerView.setAdapter(mMovieAdapter);
            }

            @Override
            protected void onNetworkError() {
                // Here i know that some error occur when processing the request,
                // possible my internet connection if turned off
            }
        }.execute();
    }

    private void addListeners() {
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the text message with a string
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.details_share_message)+ "\n\n" +
                        getText(R.string.title_label) + " " + String.valueOf(mFavorites.mTitle) + "\n\n" +
                                getText(R.string.vote_average_label) + " " + String.valueOf(mFavorites.mVoteAverage) + "\n\n" +
                                getText(R.string.release_date_label) + " " + String.valueOf(mFavorites.mReleaseDate) + "\n\n" +
                                getText(R.string.description_label) + " " + String.valueOf(mFavorites.mOverview));
                sendIntent.setType("text/plain");

                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }

            }
        });

        //disabled button in case item already exists in DB
        mFavoriteDisabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFavorites.deleteAll(FavoritesDb.class, "M_ITEM_ID = ?", String.valueOf(mFavorites.mItemId));
                mFavoriteButton.setVisibility(View.VISIBLE);
                mFavoriteDisabled.setVisibility(View.INVISIBLE);

                Snackbar snackbar = Snackbar
                        .make(mCoordinatorLayout, R.string.movie_details_removed_from_favorites, Snackbar.LENGTH_LONG)
                        .setAction(R.string.snackbar_undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final FavoritesDb favoritesDb = new FavoritesDb(
                                        mFavorites.getItemId(), mFavorites.getTitle(), mFavorites.getVoteAverage(),
                                        mFavorites.getPosterPath(), mFavorites.getReleaseDate(), mFavorites.getOverview());
                                favoritesDb.save();
                                Snackbar snackbar = Snackbar.make(mCoordinatorLayout, R.string.movies_details_returned_to_favorites, Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                mFavoriteButton.setVisibility(View.INVISIBLE);
                                mFavoriteDisabled.setVisibility(View.VISIBLE);
                            }
                        });
                snackbar.show();
            }

        });

        //save button in case item doesn't exist in DB
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FavoritesDb favoritesDb = new FavoritesDb(
                        mFavorites.getItemId(), mFavorites.getTitle(), mFavorites.getVoteAverage(),
                        mFavorites.getPosterPath(), mFavorites.getReleaseDate(), mFavorites.getOverview());
                favoritesDb.save();
                mFavoriteButton.setVisibility(View.INVISIBLE);
                mFavoriteDisabled.setVisibility(View.VISIBLE);

                Snackbar snackbar = Snackbar
                        .make(mCoordinatorLayout, R.string.movies_details_added_to_favorites, Snackbar.LENGTH_LONG)
                        .setAction(R.string.snackbar_undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                favoritesDb.findById(FavoritesDb.class, favoritesDb.mItemId);
                                favoritesDb.delete();
                                Snackbar snackbar = Snackbar.make(mCoordinatorLayout, R.string.movie_details_removed_from_favorites, Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                mFavoriteButton.setVisibility(View.VISIBLE);
                                mFavoriteDisabled.setVisibility(View.INVISIBLE);
                            }
                        });
                snackbar.show();
            }
        });
    }

    /**
     * AsyncTask to get recommended movies from Server
     **/

    private abstract class GetRecommendedMoviesAsyncTask extends ExecuteRequestAsyncTask<MoviesResponse> {
        private final String PATH = "/movie/" + mFavorites.mItemId + "/recommendations";
        public static final String SEARCH_KEY = "query";

        public GetRecommendedMoviesAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected String getPath() {
            return PATH;
        }

        @Override
        protected void addQueryParams(StringBuilder sb) {
        }

        @Override
        protected Class<MoviesResponse> getResponseEntityClass() {
            return MoviesResponse.class;
        }
    }
}
