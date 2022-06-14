package com.threeidiots.myapplication.model;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("data/2.5/forecast?units=metric&appid=23ceae0f5730cd8bf5dc3b53b13cf212&")
    Single<WeatherList> getWeatherList(
            @Query("lat") double latitude, @Query("lon") double longitude
    );

    @GET("data/2.5/forecast?units=metric&appid=23ceae0f5730cd8bf5dc3b53b13cf212&")
    Single<WeatherList> getWeatherList(@Query("q") String city);
}
