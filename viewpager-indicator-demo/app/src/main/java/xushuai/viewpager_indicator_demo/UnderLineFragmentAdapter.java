package xushuai.viewpager_indicator_demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

class UnderLineFragmentAdapter extends FragmentPagerAdapter{

    protected static  String[] CONTENT = new String[] { "This", "Is", "A" };

    private int mCount = CONTENT.length;

    public UnderLineFragmentAdapter(FragmentManager fm) {
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
      return UnderLineFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}