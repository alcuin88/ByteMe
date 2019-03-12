package com.example.adefault.bytemeV3.databaseObjects;

import com.google.android.gms.maps.model.LatLng;

public class PestControlServicesResponse implements Comparable<PestControlServicesResponse> {
    private String name;
    private String distance;
    private String duration;
    private LatLng latLng;
    private boolean opening_hours;

    public boolean isOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(boolean opening_hours) {
        this.opening_hours = opening_hours;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(PestControlServicesResponse pest) {
        if (getDistance() == null || pest.getDistance() == null) {
            return 0;
        }
        return getDistance().compareTo(pest.getDistance());
    }
}
