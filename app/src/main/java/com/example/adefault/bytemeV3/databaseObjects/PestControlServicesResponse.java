package com.example.adefault.bytemeV3.databaseObjects;

import com.google.android.gms.maps.model.LatLng;

public class PestControlServicesResponse {
    private String name;
    private String distance;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    private LatLng latLng;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private String duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
