package com.threeidiots.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class WeatherInfo {
    @SerializedName("id")
    private int id;

    @SerializedName("main")
    private String name;

    @SerializedName("description")
    private String description;

    public WeatherInfo(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
