package com.huxian.activity;

import android.os.Bundle;

import com.huxian.R;
import com.huxian.fragment.MenuLeftFragment;
import com.huxian.fragment.MenuRightFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBehindContentView(R.layout.menu_left);
        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.setSecondaryShadowDrawable(R.drawable.shadowright);
        menu.setSecondaryMenu(R.layout.menu_right);

        getSupportFragmentManager().beginTransaction().replace(R.id.layout_menu_left,
                new MenuLeftFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_menu_right,
                new MenuRightFragment()).commit();

    }
}
