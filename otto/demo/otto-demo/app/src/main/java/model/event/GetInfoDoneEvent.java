package model.event;

import model.Ticker;
import retrofit.RetrofitError;

/**
 * Created by PromeG on 2014/7/8.
 */
public class GetInfoDoneEvent {

    public boolean       isSucess = false;

    public RetrofitError netError;

    public Ticker        ticker;

    public GetInfoDoneEvent(boolean isSucess, RetrofitError netError, Ticker ticker) {
        this.isSucess = isSucess;
        this.netError = netError;
        this.ticker = ticker;
    }
}
