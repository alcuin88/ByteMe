package com.example.adefault.bytemeV3;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    private String API_KEY = "";

    private RecyclerView mPest;
    private GoogleMap mMap;
    private String url;
    private String[] googleDirectionsData;
    private String[] name;
    private String duration, distance;
    private List<PestControlServices> list;
    private LatLng orig, dest;
    private LatLng[] destination;
    private ArrayList<PestControlServicesResponse> thisList = new ArrayList<>();
    private Context context;
    private BottomSheetBehavior bottomSheetBehavior;
    private Button showList;

    @Override
    protected String doInBackground(Object... objects) {
        list = (List<PestControlServices>) objects[0];
        mPest = (RecyclerView)objects[1];
        orig = (LatLng) objects[2];
        context = (Context) objects[3];
        mMap = (GoogleMap) objects[4];
        bottomSheetBehavior = (BottomSheetBehavior) objects[5];
        showList = (Button) objects[6];
        API_KEY = (String) objects[7];

        googleDirectionsData = new String[list.size()];
        name = new String[list.size()];
        destination = new LatLng[list.size()];

        int ctr = 0;
        for (PestControlServices myList:list) {
            DownloadUrl downloadUrl = new DownloadUrl();
            dest = new LatLng( myList.getLatitude(), myList.getLongitude());
            name[ctr] = myList.getName();
            destination[ctr] = dest;

            url = getRequestUrl(orig,dest);
            try {
                googleDirectionsData[ctr] = downloadUrl.readUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ctr++;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        for(int i = 0; i < googleDirectionsData.length; i++){
            HashMap<String, String> directionsList = null;
            DataParser parser = new DataParser();
            directionsList = parser.parseDirections(googleDirectionsData[i]);
            duration = directionsList.get("duration");
            distance = directionsList.get("distance");

            PestControlServicesResponse value = new PestControlServicesResponse();
            value.setName(name[i]);
            value.setDuration("Duration: " + duration);
            value.setDistance("Distance: " + distance);
            value.setLatLng(destination[i]);

            thisList.add(value);
        }

        Display();
    }

    public void Display(){
        PestControlServicesAdapter adapter = new PestControlServicesAdapter(thisList, context, orig, mMap, bottomSheetBehavior, showList, API_KEY);
        RecyclerView.LayoutManager recyce = new GridLayoutManager(context,1);
        mPest.setLayoutManager(recyce);
        mPest.setAdapter(adapter);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
    }

    private String getRequestUrl(LatLng origin, LatLng dest){
        String str_org = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String key = "key="+API_KEY;
        String param = str_org + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;
        String output = "json";
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;
    }
}
