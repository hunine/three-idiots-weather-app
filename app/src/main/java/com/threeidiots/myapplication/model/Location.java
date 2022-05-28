package com.threeidiots.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String city;

    @SerializedName("country")
    private String country;

    public Location(int id, String city, String country) {
        this.id = id;
        this.city = city;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
