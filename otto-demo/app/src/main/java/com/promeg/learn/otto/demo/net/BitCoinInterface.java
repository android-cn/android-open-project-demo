package com.promeg.learn.otto.demo.net;

import model.Ticker;
import retrofit.Callback;
import retrofit.http.GET;

public interface BitCoinInterface {
    @GET("/api/ticker.do")
    void contributors(Callback<Ticker> cb);
}
