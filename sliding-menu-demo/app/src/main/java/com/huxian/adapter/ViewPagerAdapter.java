package com.huxian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huxian.R;
import com.huxian.fragment.BaseFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int[] resIds = new int[]{ R.drawable.viewpager1, R.drawable.viewpager2, R.drawable.viewpager3 };

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return BaseFragment.newInstance(resIds[position]);
    }

    @Override
    public int getCount() {
        return resIds.length;
    }
}
