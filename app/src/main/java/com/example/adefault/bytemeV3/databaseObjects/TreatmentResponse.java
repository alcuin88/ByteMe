package com.example.adefault.bytemeV3.databaseObjects;

import java.util.List;

public class TreatmentResponse {

    private String Description;
    private String Image;
    private List<StepsResponse> Steps;
    private int size;

    public List<StepsResponse> getSteps() {
        return Steps;
    }

    public void setSteps(List<StepsResponse> steps) {
        Steps = steps;
    }

    public int getSize() {
        return Steps.size();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public TreatmentResponse(){

    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
