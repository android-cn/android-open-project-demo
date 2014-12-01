package cn.android.zonda.retrofit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import cn.android.zonda.retrofit.data.WeatherForecastResult;
import cn.android.zonda.retrofit.data.WeatherHistoryResult;

public class RetrofitDemoActivity extends Activity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        tv = (TextView)findViewById(R.id.demo_tv);

        OpenWeatherApiService openWeatherApiService = OpenWeatherApiServiceBuilder.build();

        openWeatherApiService.getForecastWeather("china,guangzhou", "zh_cn", "10",
                new Callback<WeatherForecastResult>() {

                    @Override
                    public void success(WeatherForecastResult weatherForecastResult, Response response) {

                        String tipText = new StringBuilder("the response \n \n  url : ").append(response.getUrl())
                                .append("\n \n  status: ").append(response.getStatus()).append("\n \n reult: ")
                                .append(weatherForecastResult.toJson()).toString();

                        tv.setText(tipText);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        tv.setText(error.getMessage());
                    }
                });
        
//        new RetrofitDemoTask().execute();
    }
    
    class RetrofitDemoTask extends AsyncTask<Void, Void, WeatherHistoryResult>{

        @Override
        protected WeatherHistoryResult doInBackground(Void... params) {
            
            return OpenWeatherApiServiceBuilder.build().getHistoryWeather("china,guangzhou");
        }
        
        @Override
        protected void onPostExecute(WeatherHistoryResult result) {

            super.onPostExecute(result);
            
            String tipText = new StringBuilder("result: \n \n ")
                    .append(result.toJson()).toString();

            tv.setText(tipText);
        }
    } 

}
