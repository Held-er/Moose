package movies.flag.pt.moviesapp.screens;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.constants.BroadcastConstants;
import movies.flag.pt.moviesapp.helpers.ImagesHelper;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.Series;

/**
 * Created by Helder Silva on 04/02/2017.
 */

public class SeriesDetailsScreen extends Screen {

    public static final String SERIES_PARCELABLE = "series";

    private ImageView mImageView;
    private TextView mTitle;
    private TextView mVoteAverage;
    private TextView mReleaseDate;
    private TextView mDescription;
    private ImageButton mShare;
    private Series mSeries;

    private SeriesDetailsScreen.ImageBroadcastReceiver imageBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_series_details);

        mSeries = getIntent().getParcelableExtra(SERIES_PARCELABLE);

        findViews();
        addListeners();

        imageBroadcastReceiver = new SeriesDetailsScreen.ImageBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(imageBroadcastReceiver,
                new IntentFilter(BroadcastConstants.BROADCAST_IMAGE_ACTION));

        String url = "https://image.tmdb.org/t/p/w500/" + mSeries.getPosterPath();
        executeDownloadAsyncTask(url);
    }

    private void findViews() {
        mImageView = (ImageView) findViewById(R.id.movies_series_details_image);

        mTitle = (TextView) findViewById(R.id.movies_series_details_title);
        mTitle.setText(mSeries.getName());

        mVoteAverage = (TextView) findViewById(R.id.movies_series_details_subtitle1);
        mVoteAverage.setText(String.valueOf(mSeries.getVoteAverage()));

        mReleaseDate = (TextView) findViewById(R.id.movies_series_details_subtitle2);
        mReleaseDate.setText(mSeries.getFirstAirDate());

        mDescription = (TextView) findViewById(R.id.movies_series_details_description);
        mDescription.setText(mSeries.getOverview());

        mShare = (ImageButton) findViewById(R.id.movies_series_details_fab);
    }

    private void addListeners() {
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the text message with a string
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Title: " + mSeries.getName() + "\n" + "Vote Average: "
                        + mSeries.getVoteAverage() + "\n" + "Release date: " + mSeries.getFirstAirDate() +
                        "\n" + "Description: " + mSeries.getOverview());
                sendIntent.setType("text/plain");

                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }

            }
        });

    }

    private void executeDownloadAsyncTask(String url){
        SeriesDetailsScreen.DownloadImageAsyncTask task = new SeriesDetailsScreen.DownloadImageAsyncTask();
        task.execute(url);
    }


    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private final String TAG = SeriesDetailsScreen.DownloadImageAsyncTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
            //mLoaderView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.d(TAG, "doInBackground");

            String url = params[0];

            return ImagesHelper.convertByteArrayToBitmap(ImagesHelper.downloadImageUrl(url));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute");
            //mLoaderView.setVisibility(View.GONE);
            mImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).
                unregisterReceiver(imageBroadcastReceiver);
        super.onDestroy();
    }

    private class ImageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //mLoaderView.setVisibility(View.GONE);
            byte[] imageByteArray = intent.getByteArrayExtra(BroadcastConstants.BITMAP_KEY_EXTRA);
            Bitmap bitmap = ImagesHelper.convertByteArrayToBitmap(imageByteArray);
            mImageView.setImageBitmap(bitmap);
        }

    }
}
