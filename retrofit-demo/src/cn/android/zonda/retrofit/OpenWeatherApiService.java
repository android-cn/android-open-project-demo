package cn.android.zonda.retrofit;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import cn.android.zonda.retrofit.data.WeatherForecastResult;
import cn.android.zonda.retrofit.data.WeatherHistoryResult;
import cn.android.zonda.retrofit.data.WeatherTodayResult;
// @GET("/history/city")
public interface OpenWeatherApiService {

    public static final String BASE_URL     = "http://api.openweathermap.org/data/2.5/";

    public static final String BASE_IMG_URL = "http://openweathermap.org/img/w/";

    @GET("/weather")
    public void getTodayWeather(@Query("q") String queryInfo, @Query("lang") String language,
            Callback<WeatherTodayResult> callback);

    @GET("/forecast/daily")
    public void getForecastWeather(@Query("q") String queryInfo, @Query("lang") String language,
            @Query("cnt") String dayNumber, Callback<WeatherForecastResult> callback);

    @GET("/history/city")
    public WeatherHistoryResult getHistoryWeather(@Query("q") String queryInfo);
    
    @GET("/weather")
    public WeatherTodayResult getTodayWeather(@Query("q") String queryInfo, @Query("lang") String language);
}
