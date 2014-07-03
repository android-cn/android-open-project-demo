package com.rengwuxian.daggerdemo;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by rengw_000 on 2014/6/29 0029.
 */
public class App extends Application {
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
}
