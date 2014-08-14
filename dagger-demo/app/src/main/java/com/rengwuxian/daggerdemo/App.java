package com.rengwuxian.daggerdemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.rengwuxian.daggerdemo.model.Coder;

import dagger.ObjectGraph;

/**
 * Created by rengw_000 on 2014/6/29 0029.
 */
public class App extends Application implements Application.ActivityLifecycleCallbacks {
    int activityCount = 0; // 打开的Activity总数

    ObjectGraph objectGraph;

    public <T> void inject(T target) {
        getObjectGraph().inject(target);
    }

    private ObjectGraph getObjectGraph() {
        if (objectGraph == null) {
            objectGraph = ObjectGraph.create(new AppModule());
        }
        return objectGraph;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityCount++;
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityCount--;
        if (activityCount == 0) {
            // 当一个Activity关闭并且activityCount为0时，表示Activity栈已空，那么重置ObjectGraph和Coder的数量
            objectGraph = null;
            Coder.resetTotal();
        }
    }
}
