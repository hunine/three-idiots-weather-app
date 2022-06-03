package com.threeidiots.myapplication.viewmodel;

import com.threeidiots.myapplication.model.WeatherApi;
import com.threeidiots.myapplication.model.WeatherList;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiService {
    private static final String BASE_URL = "https://api.openweathermap.org/";
    private WeatherApi api;

    public WeatherApiService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(WeatherApi.class);
    }

    public Single<WeatherList> getWeatherList(double latitude, double longitude) {
        return api.getWeatherList(latitude, longitude);
    }
}
