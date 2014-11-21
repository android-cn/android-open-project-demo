package com.lidroid.xutils.view.annotation.event;

import android.widget.AbsListView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: wyouflf
 * Date: 13-9-12
 * Time: 下午11:25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = AbsListView.OnScrollListener.class,
        listenerSetter = "setOnScrollListener",
        methodName = "onScrollStateChanged")
public @interface OnScrollStateChanged {
    int[] value();

    int[] parentId() default 0;
}
