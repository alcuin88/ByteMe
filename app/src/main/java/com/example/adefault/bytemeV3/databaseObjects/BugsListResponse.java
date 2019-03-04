package com.example.adefault.bytemeV3.databaseObjects;


public class BugsListResponse {

    private String key;
    private String BugName;
    private String BugImage;
    private String Description;
    private String HomeTreatment;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getHomeTreatment() {
        return HomeTreatment;
    }

    public void setHomeTreatment(String homeTreatment) {
        HomeTreatment = homeTreatment;
    }

    public String getBugName() {
        return BugName;
    }

    public void setBugName(String bugName) {
        BugName = bugName;
    }

    public String getBugImage() {
        return BugImage;
    }

    public void setBugImage(String bugImage) {
        BugImage = bugImage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
