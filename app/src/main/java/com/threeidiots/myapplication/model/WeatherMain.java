package com.threeidiots.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class WeatherMain {
    @SerializedName("temp")
    private double temperature;

    @SerializedName("feels_like")
    private double feelsLike;

    @SerializedName("humidity")
    private int humidity;

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    @SerializedName("pressure")
    private int pressure;

    public WeatherMain(double temperature, double feelsLike, int humidity, int pressure) {
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
