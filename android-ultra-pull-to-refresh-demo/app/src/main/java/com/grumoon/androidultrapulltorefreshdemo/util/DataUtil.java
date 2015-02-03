package com.grumoon.androidultrapulltorefreshdemo.util;

import com.grumoon.androidultrapulltorefreshdemo.ui.AutoRefreshFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.ContentGridViewFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.ContentImageViewFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.ContentListViewFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.ContentRecyclerViewFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.ContentScrollViewFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.ContentTextViewFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.ContentWebViewFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.CustomHeaderFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.GetMoreListViewFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.MaterialHeaderFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.PullToRefreshFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.ReleaseToRefreshFragment;
import com.grumoon.androidultrapulltorefreshdemo.ui.StoreHouseHeaderFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by grumoon on 15/1/11.
 */
public class DataUtil {

    public static final String MENU_KEY_GROUP_NAME = "menu_key_group_name";

    public static final String MENU_KEY_ITEM_NAME = "menu_key_item_name";

    public static final String MENU_KEY_FRAGMENT = "menu_key_fragment";


    public static List<HashMap<String, Object>> getMenuList() {
        List<HashMap<String, Object>> menuList = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_GROUP_NAME, "包含内容");
        menuItem.put(MENU_KEY_ITEM_NAME, "TextView");
        menuItem.put(MENU_KEY_FRAGMENT, new ContentTextViewFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_ITEM_NAME, "ImageView");
        menuItem.put(MENU_KEY_FRAGMENT, new ContentImageViewFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_ITEM_NAME, "ScrollView");
        menuItem.put(MENU_KEY_FRAGMENT, new ContentScrollViewFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_ITEM_NAME, "ListView");
        menuItem.put(MENU_KEY_FRAGMENT, new ContentListViewFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_ITEM_NAME, "GridView");
        menuItem.put(MENU_KEY_FRAGMENT, new ContentGridViewFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_ITEM_NAME, "WebView");
        menuItem.put(MENU_KEY_FRAGMENT, new ContentWebViewFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_ITEM_NAME, "RecyclerView");
        menuItem.put(MENU_KEY_FRAGMENT, new ContentRecyclerViewFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_GROUP_NAME, "刷新类型");
        menuItem.put(MENU_KEY_ITEM_NAME, "释放刷新");
        menuItem.put(MENU_KEY_FRAGMENT, new ReleaseToRefreshFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_ITEM_NAME, "下拉刷新");
        menuItem.put(MENU_KEY_FRAGMENT, new PullToRefreshFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_ITEM_NAME, "自动刷新");
        menuItem.put(MENU_KEY_FRAGMENT, new AutoRefreshFragment());

        menuList.add(menuItem);


        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_GROUP_NAME, "头部");
        menuItem.put(MENU_KEY_ITEM_NAME, "StoreHouse风格");
        menuItem.put(MENU_KEY_FRAGMENT, new StoreHouseHeaderFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_ITEM_NAME, "Material风格");
        menuItem.put(MENU_KEY_FRAGMENT, new MaterialHeaderFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_ITEM_NAME, "自定义头部");
        menuItem.put(MENU_KEY_FRAGMENT, new CustomHeaderFragment());

        menuList.add(menuItem);

        menuItem = new HashMap<String, Object>();
        menuItem.put(MENU_KEY_GROUP_NAME, "加载更多");
        menuItem.put(MENU_KEY_ITEM_NAME, "ListView自定义加载更多");
        menuItem.put(MENU_KEY_FRAGMENT, new GetMoreListViewFragment());

        menuList.add(menuItem);


        return menuList;
    }

}
