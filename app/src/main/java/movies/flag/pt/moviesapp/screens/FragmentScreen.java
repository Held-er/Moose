package movies.flag.pt.moviesapp.screens;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.master.permissionhelper.PermissionHelper;
import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.fragments.MapsFragment;
import movies.flag.pt.moviesapp.fragments.MooseFragmentPagerAdapter;
import movies.flag.pt.moviesapp.utils.LockableViewPager;

/**
 * Activity that includes all the fragments in the App
 */

public class FragmentScreen extends FragmentActivity  {

    private TabLayout mTabLayout;
    private View mRootLayout;
    private LinearLayout mRootView;
    private LockableViewPager mViewPager;
    private PermissionHelper mPermissionHelper;

    private static final String TAG = MapsFragment.class.getSimpleName();
    public static String POSITION = "POSITION";

    private int[] tabIcons = {
            R.drawable.ic_now_playing,
            R.drawable.ic_favorite,
            R.drawable.ic_maps,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the view pager that will allow the user to swipe between fragments
        mViewPager = (LockableViewPager) findViewById(R.id.activity_main_lockable_view_pager);
        mViewPager.setmSwipable(false);

        // Create an adapter that knows which fragment should be shown on each page
        MooseFragmentPagerAdapter adapter = new MooseFragmentPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        mViewPager.setAdapter(adapter);

        // Set TabLayout
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);

        mRootLayout = (CoordinatorLayout) findViewById(R.id.movies_series_recycler_screen_coordinator_layout);
        mRootView = (LinearLayout) findViewById(R.id.activity_main_root_view);

        //Request Location Permission
        mPermissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        mPermissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                Log.d(TAG, "onPermissionGranted() called");
            }

            @Override
            public void onPermissionDenied() {
                Log.d(TAG, "onPermissionDenied() called");

            }

            @Override
            public void onPermissionDeniedBySystem() {
                Log.d(TAG, "onPermissionDeniedBySystem() called");
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, mTabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mViewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }
}
 