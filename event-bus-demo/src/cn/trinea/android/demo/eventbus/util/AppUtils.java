package cn.trinea.android.demo.eventbus.util;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import cn.trinea.android.demo.eventbus.MainActivity;

/**
 * AppUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-9
 */
public class AppUtils {

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static void initActionBar(final Activity activity) {
        if (activity == null) {
            return;
        }

        ActionBar bar = activity.getActionBar();
        if (bar == null) {
            return;
        }

        if (activity instanceof MainActivity) {
            bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM
                    | ActionBar.DISPLAY_SHOW_HOME);
        } else {
            bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP
                    | ActionBar.DISPLAY_SHOW_CUSTOM);
        }
    }
}
