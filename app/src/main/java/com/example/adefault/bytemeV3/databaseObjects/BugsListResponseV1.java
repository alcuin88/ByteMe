package com.example.adefault.bytemeV3.databaseObjects;

import java.util.List;

public class BugsListResponseV1 {

    private String Key;
    private String BugImage;
    private String BugName;
    private String Description;
    private List<SignsResponse> Signs;
    private List<SymptomsResponse> Symptoms;
//    private List<TreatmentResponse> Treatment;
//    private List<GetRidResponse> GetRid;

//    public List<GetRidResponse> getGetRid() {
//        return GetRid;
//    }
//
//    public void setGetRid(List<GetRidResponse> getRid) {
//        GetRid = getRid;
//    }

    public BugsListResponseV1(){}

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getBugImage() {
        return BugImage;
    }

    public void setBugImage(String bugImage) {
        BugImage = bugImage;
    }

    public String getBugName() {
        return BugName;
    }

    public void setBugName(String bugName) {
        this.BugName = bugName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<SignsResponse> getSigns() {
        return Signs;
    }

    public void setSigns(List<SignsResponse> signs) {
        Signs = signs;
    }

    public List<SymptomsResponse> getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(List<SymptomsResponse> symptoms) {
        Symptoms = symptoms;
    }

//    public List<TreatmentResponse> getTreatment() {
//        return Treatment;
//    }
//
//    public void setTreatment(List<TreatmentResponse> treatment) {
//        Treatment = treatment;
//    }
}
