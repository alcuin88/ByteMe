package com.example.adefault.bytemeV3;

class InsectNode {
    private String insectName;
    private double confidence;
    private int counter;

    InsectNode(){
        insectName = "";
        confidence = 0;
        counter = 0;
    }

    String GetInsectName(){
        return insectName;
    }

    double GetConfidence(){
        return confidence;
    }

    int GetCount(){
        return counter;
    }

    void SetInsectName(String insectName){
        this.insectName = insectName;
    }

    void SetConfidence(double confidence){
        this.confidence = confidence;
    }

    void SetCounter(int counter){
        this.counter = counter;
    }
}
