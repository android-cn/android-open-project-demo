package xushuai.viewpager_indicator_demo;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.viewpagerindicator.PageIndicator;

import java.util.Random;

public abstract class BaseSampleActivity extends FragmentActivity {
    TestFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
}
