package com.example.yyb.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.example.yyb.myapplication.MyFragment;
import com.example.yyb.myapplication.R;

/**
 * Created by yyb on 15-3-8.
 */
public class FragmentPagterIconTabAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    public FragmentPagterIconTabAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return MyFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getPageIconResId(int position) {
        int iconId = R.drawable.ic_action_messages_highlighted;
        switch (position) {
            case 0:
                iconId = R.drawable.ic_action_messages_highlighted;
                break;
            case 1:
                iconId = R.drawable.ic_action_news_highlighted;
                break;
            case 2:
                iconId = R.drawable.ic_action_users_highlighted;
                break;


        }
        return iconId;
    }
}
