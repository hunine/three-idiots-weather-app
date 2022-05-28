package com.threeidiots.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.threeidiots.myapplication.model.Weather;
import com.threeidiots.myapplication.model.WeatherList;
import com.threeidiots.myapplication.viewmodel.WeatherApiService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private WeatherApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = new WeatherApiService();

        apiService.getWeatherList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherList>() {
                    @Override
                    public void onSuccess(@NonNull WeatherList weatherList) {
                        Log.d("DEBUG1", "Success");

                        for (Weather weather: weatherList.getWeathers()) {
                            Log.d("SPACE", "----------------------------------------");
                            Log.d("Datetime Forecasted", weather.getDateTimeForecasted());
                            Log.d("DT", Integer.toString(weather.getDt()));
                            Log.d("Temperature", Double.toString(weather.getWeatherMain().getTemperature()));
                            Log.d("Feels like", Double.toString(weather.getWeatherMain().getFeelsLike()));
                            Log.d("Humidity", Integer.toString(weather.getWeatherMain().getHumidity()));
                            Log.d("Weather name", weather.getWeatherInfoList().get(0).getName());
                            Log.d("Description", weather.getWeatherInfoList().get(0).getDescription());
                            Log.d("Wind speed", Double.toString(weather.getWind().getSpeed()));

                        }

                        Log.d("City", weatherList.getLocation().getCity());
                        Log.d("Country", weatherList.getLocation().getCountry());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                            Log.d("DEBUG1", "Fail " + e.getMessage());
                    }
                });
    }
}