package com.example.adefault.bytemeV3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private List<HashMap<String, String>> getPlaces(JSONArray googleDirectionsJson){
        List<HashMap<String, String>> googleDirectionsMap = new ArrayList<>();
        HashMap<String, String> map;
        String lat;
        String lng;
        String name = "";
        String opening_hours;

        for(int i = 0; i < googleDirectionsJson.length(); i++) {
            try {
                map = new HashMap<>();

                name = googleDirectionsJson.getJSONObject(i).getString("name");
                lat = googleDirectionsJson.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
                lng = googleDirectionsJson.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
                opening_hours = googleDirectionsJson.getJSONObject(i).getString("opening_hours");

                map.put("name", name);
                map.put("lat", lat);
                map.put("lng", lng);
                map.put("opening_hours", opening_hours);

                googleDirectionsMap.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return googleDirectionsMap;
    }

    private List<HashMap<String, String>> getDirections(JSONArray googleDirectionsJson){
        List<HashMap<String, String>> googleDirectionsMap = new ArrayList<>();
        HashMap<String, String> map;
        String duration = "";
        String distance = "";

        try{
            map = new HashMap<>();
            duration = googleDirectionsJson.getJSONObject(0).getJSONObject("duration").getString("text");
            distance = googleDirectionsJson.getJSONObject(0).getJSONObject("distance").getString("text");

            map.put("duration", duration);
            map.put("distance", distance);
            googleDirectionsMap.add(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googleDirectionsMap;
    }


    public List<HashMap<String, String>> parseDirections(String jsonData, String type){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            if(type.equalsIgnoreCase("places"))
                jsonArray = jsonObject.getJSONArray("results");
            else
                jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(type.equalsIgnoreCase("places"))
            return getPlaces(jsonArray);
        else
            return getDirections(jsonArray);
    }
}
