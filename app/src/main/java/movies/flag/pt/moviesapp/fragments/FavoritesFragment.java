package movies.flag.pt.moviesapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;
import java.util.List;
import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.databases.FavoritesDb;
import movies.flag.pt.moviesapp.screens.FavoriteDetailsScreen;
import movies.flag.pt.moviesapp.utils.BackImageLinks;

public class FavoritesFragment extends BaseFragment {
    private TextView mLabelList;
    private RecyclerView mFavoritesRecyclerView;
    private GridLayoutManager mFavoritesLayoutManager;
    private FavoritesAdapter mFavoritesAdapter;

    private ImageView mNoFavoriteImage;
    private TextView mNoFavoriteText;
    Context mContext;
    private List<FavoritesDb> mFavoritesDbs;


    public static FavoritesFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Select existsInDb = Select.from(FavoritesDb.class)
                .where(Condition.prop("M_ITEM_ID").gt(0));
        long countExistsInDb = existsInDb.count();

        View v = inflater.inflate(R.layout.favorites_recycler_screen, null);
        executeTaskToGetFavoritesFromDataBase();
        findViews(v);

        //validate if there are favorites in the DB
        if (countExistsInDb == 0) {
            mNoFavoriteImage.setVisibility(View.VISIBLE);
            mNoFavoriteText.setVisibility(View.VISIBLE);
        } else {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mFavoritesRecyclerView.setHasFixedSize(false);

            // use a grid layout manager
            mFavoritesLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_rows));
            mFavoritesRecyclerView.setHasFixedSize(false);
            mFavoritesRecyclerView.setLayoutManager(mFavoritesLayoutManager);
        }
        return v;
    }

    //instantiates async task
    public void executeTaskToGetFavoritesFromDataBase() {
        InitAdapterAsyncTask task = new InitAdapterAsyncTask();
        task.execute();
    }

    private void findViews(View v) {
        Typeface black_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/prelo_w01_black.ttf");
        Typeface book_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/prelo_w01_book.ttf");

        mLabelList = (TextView) v.findViewById(R.id.favorites_recycler_screen_label_list);
        mLabelList.setTypeface(black_font);
        mFavoritesRecyclerView = (RecyclerView) v.findViewById(R.id.favorites_recycler_screen_recycler_view);

        mNoFavoriteImage = (ImageView) v.findViewById(R.id.fragment_no_screen_image);
        mNoFavoriteText = (TextView) v.findViewById(R.id.fragment_no_screen_text);
        mNoFavoriteText.setTypeface(book_font);
    }

    @Override
    public void onResume() {
        super.onResume();

        //change views depending on whether there are items in the DB or not
        Select existsInDb = Select.from(FavoritesDb.class)
                .where(Condition.prop("M_ITEM_ID").gt(0));
        long countExistsInDb = existsInDb.count();
        if (countExistsInDb == 0) {
            mNoFavoriteImage.setVisibility(View.VISIBLE);
            mNoFavoriteText.setVisibility(View.VISIBLE);
            mLabelList.setVisibility(View.INVISIBLE);
        } else {
            mNoFavoriteImage.setVisibility(View.INVISIBLE);
            mNoFavoriteText.setVisibility(View.INVISIBLE);
            mLabelList.setVisibility(View.VISIBLE);
            mFavoritesRecyclerView.setHasFixedSize(false);
            mFavoritesLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_rows));
            mFavoritesRecyclerView.setHasFixedSize(false);
            mFavoritesRecyclerView.setLayoutManager(mFavoritesLayoutManager);
          }
        executeTaskToGetFavoritesFromDataBase();
    }

    /**
     * Async Task to get all Favorites from Db
     **/

    private class InitAdapterAsyncTask extends AsyncTask<Void, Void, List<FavoritesDb>> {

        @Override
        protected List<FavoritesDb> doInBackground(Void... params) {
            // Query to database must be done in a background thread
            return FavoritesDb.listAll(FavoritesDb.class);
        }

        @Override
        protected void onPostExecute(List<FavoritesDb> favorites) {
            mFavoritesAdapter = new FavoritesAdapter(mContext, favorites);
            mFavoritesRecyclerView.setAdapter(mFavoritesAdapter);
        }
    }

    /**
     * Favorites Adapter
     **/

    private class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

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
            public TextView mMovieTitle;
            public TextView mMovieScore;
            public ImageView mFavoriteStar;
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public FavoritesAdapter(Context mContext, List<FavoritesDb> favorites) {
            FavoritesFragment.this.mContext = mContext;
            mFavoritesDbs = favorites;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
            // create a new view
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_movies_series_item, parent, false);

            // set the view's size, margins, paddings and layout parameters
            ViewHolder holder = new ViewHolder(v);
            holder.mMovieImage = (ImageView) v.findViewById(R.id.grid_movies_series_item_image);
            holder.mMovieTitle = (TextView) v.findViewById(R.id.grid_movies_series_item_title);
            holder.mMovieScore = (TextView) v.findViewById(R.id.grid_movies_series_item_score);
            holder.mMovieLayout = (LinearLayout) v.findViewById(R.id.grid_movies_series_item_layout);
            holder.mFavoriteStar = (ImageView) v.findViewById(R.id.grid_movies_series_item_favorite_image);
            return holder;
        }


        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final FavoritesDb favorites = mFavoritesDbs.get(position);
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            //load images
            BackImageLinks links = new BackImageLinks();
            Picasso.with(holder.mMovieImage.getContext())
                    .load(links.backImage())
                    .error(R.drawable.poster_placeholder)
                    .into(holder.mMovieImage);

            //set fonts
            Typeface black_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/prelo_w01_black.ttf");
            Typeface book_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/prelo_w01_book.ttf");

            holder.mMovieTitle.setText(favorites.getTitle());
            holder.mMovieTitle.setTypeface(book_font);

            holder.mMovieScore.setText(String.valueOf(favorites.getVoteAverage()));
            holder.mMovieScore.setTypeface(black_font);

            //onClickListener to Details Screen
            holder.mMovieLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), FavoriteDetailsScreen.class);
                    intent.putExtra(FavoriteDetailsScreen.FAVORITES_PARCELABLE, favorites);
                    view.getContext().startActivity(intent);
                }
            });

            holder.mFavoriteStar.setVisibility(View.VISIBLE);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mFavoritesDbs.size();
        }
    }

}