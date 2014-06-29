package cn.android.zonda.retrofit;

import retrofit.RestAdapter;

public class OpenWeatherApiServiceBuilder {

    private volatile static OpenWeatherApiService mInstance;

    public static OpenWeatherApiService build() {

        if (mInstance == null) {

            synchronized (OpenWeatherApiServiceBuilder.class) {

                if (mInstance == null) {

                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(OpenWeatherApiService.BASE_URL)
                            .build();

                    mInstance = restAdapter.create(OpenWeatherApiService.class);
                }
            }
        }
        return mInstance;
    }// end of method build
}
