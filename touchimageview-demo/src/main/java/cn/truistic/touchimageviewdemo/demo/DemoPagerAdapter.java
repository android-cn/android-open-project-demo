package cn.truistic.touchimageviewdemo.demo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import cn.truistic.touchimageviewdemo.lib.TouchImageView;

/**
 * ViewPager PagerAdapter
 */
public class DemoPagerAdapter extends PagerAdapter {

    private Context context;
    private int[] imgs;

    public DemoPagerAdapter(Context context, int[] imgs) {
        this.context = context;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView tImgView = new TouchImageView(context);
        tImgView.setImageResource(imgs[position]);
        tImgView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        container.addView(tImgView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        return tImgView;
    }

}
