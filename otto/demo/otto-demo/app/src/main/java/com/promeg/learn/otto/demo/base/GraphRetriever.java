package com.promeg.learn.otto.demo.base;

import android.content.Context;
import dagger.ObjectGraph;

public class GraphRetriever {

    public interface GraphApplication {
        ObjectGraph getGraph();
    }

    public static ObjectGraph from(Context context) {
        GraphApplication application = (GraphApplication)context.getApplicationContext();
        return application.getGraph();
    }

}
