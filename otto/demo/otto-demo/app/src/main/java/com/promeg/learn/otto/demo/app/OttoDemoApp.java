package com.promeg.learn.otto.demo.app;

import com.promeg.learn.otto.demo.BuildConfig;
import com.promeg.learn.otto.demo.base.GraphRetriever;
import com.promeg.learn.otto.demo.net.BitCoinService;
import com.squareup.otto.Bus;

import android.app.Application;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by PromeG on 2014/7/8.
 */
public class OttoDemoApp extends Application implements GraphRetriever.GraphApplication {

    // singleton
    private static OttoDemoApp globalContext = null;

    @Inject
    public Bus                 mBus;

    private ObjectGraph        objectGraph;

    private BitCoinService     mBitCoinService;

    @Override
    public void onCreate() {
        super.onCreate();

        globalContext = this;

        // initial request to build the graph
        objectGraph = ObjectGraph.create(getModules().toArray());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        objectGraph.inject(this);
        mBus.register(this);

        // initial service
        mBitCoinService = BitCoinService.getInstance(this);
    }

    public static OttoDemoApp getInstance() {
        return globalContext;
    }

    // we create a list containing the MyModule Dagger module
    // later we can add any other modules to it (for testing)
    protected ArrayList<Object> getModules() {
        ArrayList<Object> modules = new ArrayList<Object>(1);
        modules.add(new DaggerModule());
        return modules;
    }

    public <T> T inject(T object) {
        objectGraph = ObjectGraph.create(getModules().toArray());
        return objectGraph.inject(object);
    }

    @Override
    public ObjectGraph getGraph() {
        return objectGraph;
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.HollowTree {

        @Override
        public void i(String message, Object... args) {
            i(message, args);// Just add to the log.
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            i(message, args); // Just add to the log.
        }

        @Override
        public void e(String message, Object... args) {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            e(message, args);
            // TODO e.g., use umeng's reportError() function;
        }
    }
}
