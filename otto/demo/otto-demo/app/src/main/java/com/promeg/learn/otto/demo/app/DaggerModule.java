package com.promeg.learn.otto.demo.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.promeg.learn.otto.demo.activity.MainActivity;
import com.promeg.learn.otto.demo.activity.TestProduceActivity;
import com.promeg.learn.otto.demo.net.BitCoinInterface;
import com.promeg.learn.otto.demo.net.BitCoinService;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * 用到injects的类应写入injects数组中，否则运行报错
 */
@Module(library = true, injects = {
        // Application
        OttoDemoApp.class,

        // Activity
        MainActivity.class, TestProduceActivity.class,

        // Service
        BitCoinService.class})
public class DaggerModule {
    /**
     * 提供全局的单例OAuthServer
     */
    @Provides
    @Singleton
    public BitCoinInterface provideGitHubInterface(RestAdapter restAdapter) {
        return restAdapter.create(BitCoinInterface.class);
    }

    /**
     * 提供全局的单例RestAdapter
     */
    @Provides
    @Singleton
    public RestAdapter provideRestAdapter(Gson gson) {
        // give access to the rest api to the entire app
        return new RestAdapter.Builder().setEndpoint("https://www.okcoin.cn").setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL).build();
    }

    /**
     * 提供全局的单例Gson
     */
    @Provides
    @Singleton
    public Gson provideGson() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
        return gson;
    }

    /**
     * 提供全局单例的event bus
     */
    @Provides
    @Singleton
    public Bus provideBus() {
        // our event bus running on any thread
        return new Bus(ThreadEnforcer.ANY);
    }

}
