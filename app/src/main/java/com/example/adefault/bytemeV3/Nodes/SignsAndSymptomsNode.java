package com.example.adefault.bytemeV3.Nodes;

public class SignsAndSymptomsNode {

    private double points;
    private String bugName;
    private int occr;

    public SignsAndSymptomsNode() {
        points = 0;
        bugName = "";
        occr = 0;
    }

    public int getOccr() {
        return occr;
    }

    public void setOccr(int occr) {
        this.occr = occr;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getBugName() {
        return bugName;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }
}
