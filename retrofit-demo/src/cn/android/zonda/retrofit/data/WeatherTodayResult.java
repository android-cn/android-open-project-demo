package cn.android.zonda.retrofit.data;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class WeatherTodayResult {

    private CoordModel              coord;

    @SerializedName("weather")
    private ArrayList<WeatherModel> weathers;

    private WindModel               wind;

    @SerializedName("dt")
    private int                     date;

    private String                  id;

    public final CoordModel getCoord() {
        return coord;
    }

    public final void setCoord(CoordModel coord) {
        this.coord = coord;
    }

    public final ArrayList<WeatherModel> getWeathers() {
        return weathers;
    }

    public final void setWeathers(ArrayList<WeatherModel> weathers) {
        this.weathers = weathers;
    }

    public final WindModel getWind() {
        return wind;
    }

    public final void setWind(WindModel wind) {
        this.wind = wind;
    }

    public final int getDate() {
        return date;
    }

    public final void setDate(int date) {
        this.date = date;
    }

    public final String getId() {
        return id;
    }

    public final void setId(String id) {
        this.id = id;
    }
}
