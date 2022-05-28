package com.threeidiots.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherList {
    @SerializedName("list")
    private List<Weather> weathers;

    @SerializedName("city")
    private Location location;

    public WeatherList(List<Weather> weathers, Location location) {
        this.weathers = weathers;
        this.location = location;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
