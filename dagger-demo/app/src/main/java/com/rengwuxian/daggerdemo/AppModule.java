package com.rengwuxian.daggerdemo;

import com.rengwuxian.daggerdemo.model.Boss;
import com.rengwuxian.daggerdemo.model.Coder;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rengw_000 on 2014/6/29 0029.
 */
@Module(injects = MainActivity.class)
public class AppModule {
    @Provides
    Coder provideCoder(Boss boss) {
        return new Coder(boss);
    }
}
