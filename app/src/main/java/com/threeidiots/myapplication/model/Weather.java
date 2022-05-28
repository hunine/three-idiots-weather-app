package com.threeidiots.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {
    @SerializedName("dt")
    private int dt;

    @SerializedName("main")
    private WeatherMain weatherMain;

    @SerializedName("weather")
    private List<WeatherInfo> weatherInfoList;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("dt_txt")
    private String dateTimeForecasted;

    public Weather(int dt, WeatherMain weatherMain, List<WeatherInfo> weatherInfoList, Wind wind, String dateTimeForecasted) {
        this.dt = dt;
        this.weatherMain = weatherMain;
        this.weatherInfoList = weatherInfoList;
        this.wind = wind;
        this.dateTimeForecasted = dateTimeForecasted;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public WeatherMain getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(WeatherMain weatherMain) {
        this.weatherMain = weatherMain;
    }

    public List<WeatherInfo> getWeatherInfoList() {
        return weatherInfoList;
    }

    public void setWeatherInfoList(List<WeatherInfo> weatherInfoList) {
        this.weatherInfoList = weatherInfoList;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getDateTimeForecasted() {
        return dateTimeForecasted;
    }

    public void setDateTimeForecasted(String dateTimeForecasted) {
        this.dateTimeForecasted = dateTimeForecasted;
    }
}
