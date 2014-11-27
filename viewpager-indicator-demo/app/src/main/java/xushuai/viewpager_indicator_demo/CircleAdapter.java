package xushuai.viewpager_indicator_demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class CircleAdapter extends FragmentPagerAdapter {
    private static final int[] mDrawableResIds = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};

        public CircleAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CircleFragment.newInstance(mDrawableResIds[position]);
        }

        @Override
        public int getCount() {
            return mDrawableResIds.length;
        }
    }