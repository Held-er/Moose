package movies.flag.pt.moviesapp.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Class used to avoid swiping between fragments (not to interfere with the swipes of the RecyclerView)
 */

public class LockableViewPager extends ViewPager {

    private boolean mSwipeable;

    public LockableViewPager(Context context) {
        super(context);
    }

    public LockableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSwipeable = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mSwipeable) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.mSwipeable) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setmSwipable(boolean mSwipeable) {
        this.mSwipeable = mSwipeable;
    }
}