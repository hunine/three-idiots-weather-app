package com.threeidiots.myapplication.model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherApi {
    @GET("data/2.5/forecast?q=Hue&units=metric&appid=23ceae0f5730cd8bf5dc3b53b13cf212")
    Single<WeatherList> getWeatherList();
}
