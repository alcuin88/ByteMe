package com.example.adefault.bytemeAPP;

import java.util.ArrayList;

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

    private static int lastBugId = 0;

    public static ArrayList<BugsListResponse> createBugsList(String insectName, String insectImage) {
        ArrayList<BugsListResponse> list = new ArrayList<BugsListResponse>();


        list.add(new BugsListResponse(insectName, insectImage));


        return list;
    }

}
