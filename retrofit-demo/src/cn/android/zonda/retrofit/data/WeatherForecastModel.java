package cn.android.zonda.retrofit.data;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class WeatherForecastModel {

    @SerializedName("dt")
    private int                     date;

    @SerializedName("weather")
    private ArrayList<WeatherModel> weathers;

    public final int getDate() {
        return date;
    }

    public final void setDate(int date) {
        this.date = date;
    }

    public final ArrayList<WeatherModel> getWeathers() {
        return weathers;
    }

    public final void setWeathers(ArrayList<WeatherModel> weathers) {
        this.weathers = weathers;
    }
}
