package com.example.adefault.bytemeV3.GeoFence;

public class Line {
    private Points from;
    private Points to;

    public Line(Points from, Points to){
        this.from = from;
        this.to = to;
    }

    public Points getFrom(){
        return from;
    }

    public Points getTo(){
        return to;
    }
}
