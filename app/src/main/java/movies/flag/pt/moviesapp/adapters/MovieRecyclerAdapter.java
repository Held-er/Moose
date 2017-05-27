package movies.flag.pt.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.screens.MovieDetailsScreen;
import movies.flag.pt.moviesapp.utils.BackImageLinks;

/**
 * Recycler Adapter used by most Recycler Views in this app
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {
    private List<Movie> mMovie;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
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
    public MovieRecyclerAdapter(Context mContext, List<Movie> movie) {
        this.mContext = mContext;
        mMovie = movie;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {

        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_series_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder holder = new ViewHolder(v);
        holder.mMovieImage = (ImageView) v.findViewById(R.id.movies_series_item_image);
        holder.mFavoriteStar = (ImageView) v.findViewById(R.id.movies_series_item_favorite_image);
        holder.mMovieTitle = (TextView) v.findViewById(R.id.movies_series_item_title);
        holder.mMovieScore = (TextView) v.findViewById(R.id.movies_series_item_score);
        holder.mMovieLayout = (LinearLayout) v.findViewById(R.id.movies_series_item_layout);

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
            holder.mFavoriteStar.setVisibility(View.VISIBLE);}
        else {
            holder.mFavoriteStar.setVisibility(View.INVISIBLE);
        }

        //load images
        BackImageLinks links = new BackImageLinks();
        Picasso.with(holder.mMovieImage.getContext())
                .load(links.backImage())
                .error(R.drawable.poster_placeholder)
                .into(holder.mMovieImage);

        //set fonts
        final Typeface black_font = Typeface.createFromAsset(mContext.getAssets(),  "fonts/prelo_w01_black.ttf");
        final Typeface book_font = Typeface.createFromAsset(mContext.getAssets(), "fonts/prelo_w01_book.ttf");

        holder.mMovieTitle.setText(movie.getTitle());
        holder.mMovieTitle.setTypeface(book_font);

        holder.mMovieScore.setText(String.valueOf(movie.getVoteAverage()));
        holder.mMovieScore.setTypeface(black_font);

        //onClickListener to Details Screen
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
