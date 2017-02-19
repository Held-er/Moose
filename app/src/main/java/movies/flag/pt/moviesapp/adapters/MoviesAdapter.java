package movies.flag.pt.moviesapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.screens.NowPlayingDetailsScreen;
import movies.flag.pt.moviesapp.screens.NowPlayingScreen;
import movies.flag.pt.moviesapp.screens.Screen;

/**
 * Created by Helder Silva on 07/02/2017.
 */

public class MoviesAdapter extends ArrayAdapter<Movie> {

    public MoviesAdapter(Context context, List<Movie> movie) {
        super(context, 0, movie);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        MoviesAdapter.MoviesHolder holder;
        // connection to AdapterItem

        if (v == null) {
            //inflate new views
            //v = LayoutInflater.from(MoviesAdapter.this).inflate(R.layout.movies_series_item, null);
            holder = new MoviesAdapter.MoviesHolder();
            //uses holder to put findViewById in the if loop
            holder.mMovieImage = (ImageView) v.findViewById(R.id.movies_series_item_image);
            holder.mMovieTitle = (TextView) v.findViewById(R.id.movies_series_item_title);
            holder.mMovieScore = (TextView) v.findViewById(R.id.movies_series_item_score);
            holder.mMovieDetailButton = (Button) v.findViewById(R.id.movies_series_item_button);
            v.setTag(holder);
        } else {
            //reuse views
            holder = (MoviesAdapter.MoviesHolder) v.getTag();
        }

        final Movie movie = getItem(position);

        holder.mMovieImage.setImageResource(R.mipmap.ic_launcher);
        holder.mMovieTitle.setText(movie.getTitle());
        holder.mMovieScore.setText(String.valueOf(movie.getVoteAverage()));

        //check if image is already downloaded or not?
        return v;
    }

    //class created to to put findViewById in the if loop
    private class MoviesHolder {
        ImageView mMovieImage;
        TextView mMovieTitle;
        TextView mMovieScore;
        Button mMovieDetailButton;
    }


}
