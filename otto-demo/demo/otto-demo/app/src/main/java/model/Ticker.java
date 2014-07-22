
package model;

import com.google.gson.annotations.Expose;

public class Ticker {

    @Expose
    private Ticker_ ticker;

    public Ticker_ getTicker() {
        return ticker;
    }

    public void setTicker(Ticker_ ticker) {
        this.ticker = ticker;
    }

    @Override
    public String toString() {
        return "buy: " + ticker.getBuy() + "\n"
                + "high: " + ticker.getHigh() + "\n"
                + "last: " + ticker.getLast() + "\n"
                + "low: " + ticker.getLow() + "\n"
                + "sell: " + ticker.getSell() + "\n"
                + "vol: " + ticker.getVol();
    }

}
