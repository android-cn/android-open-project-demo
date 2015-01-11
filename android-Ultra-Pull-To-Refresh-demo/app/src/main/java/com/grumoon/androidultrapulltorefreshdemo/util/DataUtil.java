package com.grumoon.androidultrapulltorefreshdemo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by grumoon on 15/1/11.
 */
public class DataUtil {

    public static final String MENU_KEY_GROUP_NAME = "menu_key_group_name";

    public static final String MENU_KEY_ITEM_NAME = "menu_key_item_name";

    public static final String MENU_KEY_FRAGMENT_NAME = "menu_key_fragment_name";


    public static List<HashMap<String, String>> getMenuList() {
        List<HashMap<String, String>> menuList = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_GROUP_NAME, "包含内容");
        menuItem.put(MENU_KEY_ITEM_NAME, "TextView");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_ITEM_NAME, "ImageView");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_ITEM_NAME, "ScrollView");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_ITEM_NAME, "ListView");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_ITEM_NAME, "GridView");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_ITEM_NAME, "WebView");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_GROUP_NAME, "刷新类型");
        menuItem.put(MENU_KEY_ITEM_NAME, "释放刷新");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_ITEM_NAME, "下拉刷新");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_ITEM_NAME, "自动刷新");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);


        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_GROUP_NAME, "头部");
        menuItem.put(MENU_KEY_ITEM_NAME, "StoreHouse风格");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_ITEM_NAME, "Material风格");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_ITEM_NAME, "自定义头部");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);

        menuItem = new HashMap<String, String>();
        menuItem.put(MENU_KEY_GROUP_NAME, "加载更多");
        menuItem.put(MENU_KEY_ITEM_NAME, "ListView自定义加载更多");
        menuItem.put(MENU_KEY_FRAGMENT_NAME, "");

        menuList.add(menuItem);


        return menuList;
    }
}
