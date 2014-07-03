package cn.android.zonda.retrofit.data;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class WeatherHistoryModel {

    private WindModel               wind;

    @SerializedName("weather")
    private ArrayList<WeatherModel> weathers;

    public final WindModel getWind() {
        return wind;
    }

    public final void setWind(WindModel wind) {
        this.wind = wind;
    }

    public final ArrayList<WeatherModel> getWeathers() {
        return weathers;
    }

    public final void setWeathers(ArrayList<WeatherModel> weathers) {
        this.weathers = weathers;
    }
}
