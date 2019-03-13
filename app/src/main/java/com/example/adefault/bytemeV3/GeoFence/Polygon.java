package com.example.adefault.bytemeV3.GeoFence;

public class Polygon {
    private Points[] points;

    public Polygon(Points[] points){
        this.points = points;
    }

    public Points[] getPoints(){
        return points;
    }
}
