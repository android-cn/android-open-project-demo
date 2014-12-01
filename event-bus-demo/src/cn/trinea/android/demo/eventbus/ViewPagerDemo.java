package cn.trinea.android.demo.eventbus;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.trinea.android.common.util.ListUtils;
import cn.trinea.android.common.util.TimeUtils;
import cn.trinea.android.demo.eventbus.util.AppUtils;
import cn.trinea.android.demo.eventbus.util.TextUtils;
import de.greenrobot.event.EventBus;

/**
 * ViewPager with Fragment
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-11-14
 */
public class ViewPagerDemo extends BaseFragmentActivity {

    private static int     TOTAL_COUNT            = 3;

    private EditText       eventET;
    private Button         sendBtn;
    private CheckBox       cancelEventCB;

    private RelativeLayout viewPagerContainer;
    private ViewPager      viewPager;

    boolean                isCancelAfterFirstTime = false;

    private void initView() {
        eventET = (EditText)findViewById(R.id.event_content);
        sendBtn = (Button)findViewById(R.id.send_event);
        cancelEventCB = (CheckBox)findViewById(R.id.event_cancel);
        sendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(TextUtils.getHintIfTextIsNull(eventET));
            }
        });

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        List<String> titleList = new ArrayList<String>();
        for (int i = 0; i < TOTAL_COUNT; i++) {
            TextFragment viewPagerFragment1 = new TextFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            viewPagerFragment1.setArguments(bundle);
            titleList.add("title " + i);
            fragmentList.add(viewPagerFragment1);
        }

        // to let show more than one fragment at the same time, see
        // http://www.trinea.cn/android/viewpager-multi-fragment-effect/
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        viewPagerContainer = (RelativeLayout)findViewById(R.id.pager_layout);
        viewPagerContainer.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
                return viewPager.dispatchTouchEvent(event);
            }
        });
        viewPager.setOffscreenPageLimit(TOTAL_COUNT);
        int pageSpace = getResources().getDimensionPixelSize(R.dimen.page_margin);
        viewPager.setPageMargin(pageSpace);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
        viewPager.setCurrentItem(1);

        int margin = (AppUtils.getScreenWidth(getApplicationContext()) + pageSpace * (TOTAL_COUNT - 1)) / TOTAL_COUNT;
        LayoutParams viewPagerParams = (LayoutParams)viewPager.getLayoutParams();
        viewPagerParams.setMargins(margin, 0, margin, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_send_ordered_event);

        initView();
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {}

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // to refresh frameLayout
            if (viewPagerContainer != null) {
                viewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private List<String>   titleList;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int arg0) {
            return ListUtils.isEmpty(fragmentList) ? null : fragmentList.get(arg0);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (titleList.size() > position) ? titleList.get(position) : "";
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }

    private class TextFragment extends Fragment {

        private int      index;
        private TextView infoTV;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_text_view, container, false);
            infoTV = (TextView)v.findViewById(R.id.view_pager_text);

            Bundle bundle = getArguments();
            if (bundle != null) {
                index = bundle.getInt("index");
                infoTV.setText("Fragment " + index);
            }

            // Register
            EventBus.getDefault().register(this, index);
            return v;
        }

        @Override
        public void onDestroyView() {
            // Unregister
            EventBus.getDefault().unregister(this);

            super.onDestroyView();
        }

        // Receive Event
        public void onEvent(String event) {
            infoTV.setText(event + "\r\nPriority is:" + (index + 1) + "\r\nTime is:\r\n"
                    + TimeUtils.getCurrentTimeInString());
            if (index == TOTAL_COUNT - 1 && cancelEventCB.isChecked()) {
                EventBus.getDefault().cancelEventDelivery(event);
                infoTV.setText(infoTV.getText() + "\r\nCancel further event delivery");
            }
        }
    }
}
