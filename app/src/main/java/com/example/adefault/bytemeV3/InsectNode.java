package com.example.adefault.bytemeV3;

class InsectNode {
    private String insectName;
    private double confidence;
    private int counter;
    private InsectNode next;

    InsectNode(){
        insectName = "";
        confidence = 0;
        counter = 0;
        next = null;
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

    InsectNode GetNexLink(){
        return next;
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

    void SetNexLink(InsectNode next){
        this.next = next;
    }
}
