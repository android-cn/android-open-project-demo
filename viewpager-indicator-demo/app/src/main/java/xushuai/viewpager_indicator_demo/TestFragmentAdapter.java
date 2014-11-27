package xushuai.viewpager_indicator_demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

class TestFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

    protected String[] CONTENT = new String[] { "Reading", "Travel", "Sport" };

    protected static final int[] ICONS = new int[] {
            R.drawable.actionbar_bg_user,
            R.drawable.actionbar_bg_music,
            R.drawable.actionbar_bg_cast,
    };

    private int mCount = CONTENT.length;

    public TestFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void  setContent(String[] content){
        CONTENT = content;
    }

    @Override
    public Fragment getItem(int position) {
        return ListFragment.newInstance(CONTENT[position % CONTENT.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}