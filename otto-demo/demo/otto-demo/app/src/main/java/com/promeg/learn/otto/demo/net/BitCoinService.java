package com.promeg.learn.otto.demo.net;

import com.promeg.learn.otto.demo.base.GraphRetriever;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import android.content.Context;

import javax.inject.Inject;

import model.Ticker;
import model.event.GetInfoDoneEvent;
import model.event.GetInfoEvent;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by PromeG on 2014/7/8.
 */
public class BitCoinService {
    protected Context                      mContext;

    @Inject
    protected Bus                          mBus;

    @Inject
    protected BitCoinInterface             mBitCoinInterface;

    Ticker                                 mLastTicker = null;

    private volatile static BitCoinService singleton;

    // 单例模式
    public static BitCoinService getInstance(Context context) {
        if (singleton == null) {
            synchronized (BitCoinService.class) {
                if (singleton == null) {
                    singleton = new BitCoinService(context);
                }
            }
        }
        return singleton;
    }

    private BitCoinService(Context context) {
        mContext = context;
        GraphRetriever.from(context).inject(this);
        mBus.register(this);
    }

    @Subscribe
    public void onRequsetGetInfo(GetInfoEvent event) {

        mBitCoinInterface.contributors(new Callback<Ticker>() {
            @Override
            public void success(Ticker ticker, Response response) {
                mLastTicker = ticker;
                // success
                mBus.post(new GetInfoDoneEvent(true, null, ticker));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                mBus.post(new GetInfoDoneEvent(false, retrofitError, null));
            }
        });
    }

    @Produce
    public GetInfoDoneEvent produceAnswer() {
        if (mLastTicker != null)
            return new GetInfoDoneEvent(true, null, mLastTicker);
        else
            return new GetInfoDoneEvent(false, null, null);
    }

}
