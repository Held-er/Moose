package movies.flag.pt.moviesapp.http.requests;

import android.content.Context;

import movies.flag.pt.moviesapp.http.entities.Series;
import movies.flag.pt.moviesapp.http.entities.SeriesResponse;

/**
 * Created by Ricardo Neves on 19/10/2016.
 *
 * Get most popular series
 */

public abstract class GetSeriesAsyncTask extends ExecuteRequestAsyncTask<SeriesResponse> {

    private static final String PATH = "/tv/popular";
    private static final String LANGUAGE_KEY = "language";
    private static final String LANGUAGE_VALUE = "en";

    public GetSeriesAsyncTask(Context context) {
        super(context);
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected void addQueryParams(StringBuilder sb) {
        //addQueryParam(sb, LANGUAGE_KEY, LANGUAGE_VALUE);
    }

    @Override
    protected Class<SeriesResponse> getResponseEntityClass() {
        return SeriesResponse.class;
    }
}
