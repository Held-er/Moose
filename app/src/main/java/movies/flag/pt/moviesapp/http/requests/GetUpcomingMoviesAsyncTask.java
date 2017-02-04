package movies.flag.pt.moviesapp.http.requests;

import android.content.Context;

import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.entities.UpcomingMoviesResponse;

/**
 * Created by Ricardo Neves on 19/10/2016.
 *
 * Get upcoming movies
 */

public abstract class GetUpcomingMoviesAsyncTask extends ExecuteRequestAsyncTask<UpcomingMoviesResponse> {

    private static final String PATH = "/movie/upcoming";
    private static final String LANGUAGE_KEY = "language";
    private static final String LANGUAGE_VALUE = "en";

    public GetUpcomingMoviesAsyncTask(Context context) {
        super(context);
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected void addQueryParams(StringBuilder sb) {
        addQueryParam(sb, LANGUAGE_KEY, LANGUAGE_VALUE);
    }

    @Override
    protected Class<UpcomingMoviesResponse> getResponseEntityClass() {
        return UpcomingMoviesResponse.class;
    }
}
