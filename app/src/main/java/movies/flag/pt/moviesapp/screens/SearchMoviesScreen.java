package movies.flag.pt.moviesapp.screens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;
import java.util.List;
import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.databases.FavoritesDb;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.requests.ExecuteRequestAsyncTask;
import movies.flag.pt.moviesapp.utils.BackImageLinks;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Activity that Searches movies when mSearchButton in MovieFragment is clicked
 */

public class SearchMoviesScreen extends Screen {

    private EditText mSearchEditText;
    private ProgressBar mLoaderView;

    private RecyclerView mMoviesRecyclerView;
    private GridLayoutManager mMoviesLayoutManager;
    private SearchMovieRecyclerAdapter mMoviesAdapter;
    private ImageView mMooseIcon;
    private TextView mNoResponseText;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.search_movies_series_screen);

        findViews();
        addListeners();
    }

    private void findViews() {
        Typeface book_font = Typeface.createFromAsset(getAssets(), "fonts/prelo_w01_book.ttf");

        mSearchEditText = (EditText) findViewById(R.id.search_movies_series_edit_text);
        mSearchEditText.setTypeface(book_font);

        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.search_movies_series_screen_list);
        mLoaderView = (ProgressBar) findViewById(R.id.search_movies_series_screen_loader);
        mMooseIcon = (ImageView) findViewById(R.id.search_movies_series_moose_icon);
        mNoResponseText = (TextView) findViewById(R.id.search_movies_series_screen_text);
        mNoResponseText.setTypeface(book_font);
    }


    private void addListeners() {

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mMoviesRecyclerView.removeAllViewsInLayout();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mLoaderView.setVisibility(View.VISIBLE);
                    mMooseIcon.setVisibility(View.INVISIBLE);
                    executeRequestSearchMovies();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

    }

    private void executeRequestSearchMovies() {
        new GetSearchMoviesAsyncTask(this) {

            @Override
            protected void onResponseSuccess(MoviesResponse moviesResponse) {
                DLog.d(tag, "onResponseSuccess " + moviesResponse);
                // use a linear layout manager
                mMoviesLayoutManager = new GridLayoutManager(SearchMoviesScreen.this, getResources().getInteger(R.integer.grid_rows));
                mMoviesRecyclerView.setHasFixedSize(false);
                mMoviesRecyclerView.setLayoutManager(mMoviesLayoutManager);

                // specify an adapter (see also next example)
                mMoviesAdapter = new SearchMovieRecyclerAdapter(mContext, moviesResponse.getMovies());
                mMoviesRecyclerView.setAdapter(mMoviesAdapter);

                mLoaderView.setVisibility(View.INVISIBLE);
                mMooseIcon.setVisibility(View.INVISIBLE);
            }
            //}

            @Override
            protected void onNetworkError() {
                DLog.d(tag, "onNetworkError ");
                mLoaderView.setVisibility(View.INVISIBLE);
                mMooseIcon.setVisibility(View.INVISIBLE);
            }
        }.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        executeRequestSearchMovies();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        executeRequestSearchMovies();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
    }

    private abstract class GetSearchMoviesAsyncTask extends ExecuteRequestAsyncTask<MoviesResponse> {

        private static final String PATH = "/search/movie";
        public static final String SEARCH_KEY = "query";


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

    private class SearchMovieRecyclerAdapter extends RecyclerView.Adapter<SearchMovieRecyclerAdapter.ViewHolder> {
        private List<Movie> mMovie;
        private Context mContext;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public LinearLayout mMovieLayout;

            public ViewHolder(LinearLayout v) {
                super(v);
                mMovieLayout = v;
            }

            public ImageView mMovieImage;
            public ImageView mFavoriteStar;
            public TextView mMovieTitle;
            public TextView mMovieScore;
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public SearchMovieRecyclerAdapter(Context mContext, List<Movie> movie) {
            this.mContext = mContext;
            mMovie = movie;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public SearchMovieRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {

            // create a new view
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_movies_series_item, parent, false);

            // set the view's size, margins, paddings and layout parameters
            ViewHolder holder = new ViewHolder(v);
            holder.mMovieImage = (ImageView) v.findViewById(R.id.grid_movies_series_item_image);
            holder.mFavoriteStar = (ImageView) v.findViewById(R.id.grid_movies_series_item_favorite_image);
            holder.mMovieTitle = (TextView) v.findViewById(R.id.grid_movies_series_item_title);
            holder.mMovieScore = (TextView) v.findViewById(R.id.grid_movies_series_item_score);
            holder.mMovieLayout = (LinearLayout) v.findViewById(R.id.grid_movies_series_item_layout);

            // load images
            BackImageLinks links = new BackImageLinks();
            Picasso.with(holder.mMovieImage.getContext())
                    .load(links.backImage())
                    .error(R.drawable.poster_placeholder)
                    .into(holder.mMovieImage);

            return holder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Movie movie = mMovie.get(position);

            //validate if item is already in the DB
            Select existsInDb = Select.from(FavoritesDb.class)
                    .where(Condition.prop("M_ITEM_ID").eq(movie.getId()));
            long countExistInDb = existsInDb.count();
            if (countExistInDb == 1) {
                holder.mFavoriteStar.setVisibility(View.VISIBLE);
            } else {
                holder.mFavoriteStar.setVisibility(View.INVISIBLE);
            }

            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final Typeface black_font = Typeface.createFromAsset(mContext.getAssets(), "fonts/prelo_w01_black.ttf");
            final Typeface book_font = Typeface.createFromAsset(mContext.getAssets(), "fonts/prelo_w01_book.ttf");

            holder.mMovieTitle.setText(movie.getTitle());
            holder.mMovieTitle.setTypeface(book_font);

            holder.mMovieScore.setText(String.valueOf(movie.getVoteAverage()));
            holder.mMovieScore.setTypeface(black_font);

            holder.mMovieLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MovieDetailsScreen.class);
                    intent.putExtra(MovieDetailsScreen.MOVIE_PARCELABLE, movie);
                    view.getContext().startActivity(intent);
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mMovie.size();
        }
    }
}
