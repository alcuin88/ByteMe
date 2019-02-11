package com.example.adefault.bytemeAPP;

public class BugsListResponse {

    private String InsectName;

    private String InsectImage;

    public BugsListResponse(String insectName, String insectImage) {
        this.InsectImage = insectImage;
        this.InsectName = insectName;
    }

    public String getInsectName() {
        return InsectName;
    }

    public String getInsectImage() {
        return InsectImage;
    }


}
