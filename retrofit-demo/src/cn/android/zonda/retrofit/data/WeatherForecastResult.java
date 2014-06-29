package cn.android.zonda.retrofit.data;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class WeatherForecastResult {

    private String                          cod;

    private String                          message;

    private CityModel                       city;

    @SerializedName("list")
    private ArrayList<WeatherForecastModel> weatherForecastArray;

    public final String getCod() {
        return cod;
    }

    public final void setCod(String cod) {
        this.cod = cod;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final CityModel getCity() {
        return city;
    }

    public final void setCity(CityModel city) {
        this.city = city;
    }

    public final ArrayList<WeatherForecastModel> getWeatherForecastArray() {
        return weatherForecastArray;
    }

    public final void setWeatherForecastArray(ArrayList<WeatherForecastModel> weatherForecastArray) {
        this.weatherForecastArray = weatherForecastArray;
    }
    public String toJson(){
        
        Gson gson = new GsonBuilder().create();
        
        Type type = new TypeToken<WeatherForecastResult>(){}.getType();
        
        return gson.toJson(this, type);
    }
}
