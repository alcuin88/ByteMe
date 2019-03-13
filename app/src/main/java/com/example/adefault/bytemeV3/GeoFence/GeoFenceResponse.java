package com.example.adefault.bytemeV3.GeoFence;

import java.util.List;

public class GeoFenceResponse {
    private String City;
    private List<Points> Points;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public List<Points> getPoints() {
        return Points;
    }

    public void setPoints(List<Points> value) {
        this.Points = value;
    }
}
