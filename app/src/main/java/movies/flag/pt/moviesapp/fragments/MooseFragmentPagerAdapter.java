package movies.flag.pt.moviesapp.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Pager Adapter to coordinate Fragments' position
 */

public class MooseFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] {
            "Movies", "Favorites", "Cinemas Nearby"};

    public MooseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //Content PagerAdapter
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MovieFragment();
        } else if (position == 1) {
            return new FavoritesFragment();
        } else {
            return new MapsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
