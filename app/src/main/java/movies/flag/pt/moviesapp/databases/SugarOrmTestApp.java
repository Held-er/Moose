package movies.flag.pt.moviesapp.databases;
import android.content.res.Configuration;
import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * DB Entity Configuration
 */

public class SugarOrmTestApp extends SugarApp {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
        FavoritesDb.findById(FavoritesDb.class, (long) 1);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

