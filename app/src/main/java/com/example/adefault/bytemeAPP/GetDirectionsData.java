package com.example.adefault.bytemeAPP;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    private static final String API_KEY = "AIzaSyACOex929TfptSnad16icgpy-QXh-hgJCM";

    RecyclerView mPest;
    String url;
    String[] googleDirectionsData;
    String[] name;
    String duration, distance;
    List<PestControlServices> list;
    LatLng orig, dest;
    LatLng[] destination;
    ArrayList<PestControlServicesResponse> thisList = new ArrayList<>();
    Context context;

    @Override
    protected String doInBackground(Object... objects) {
        list = (List<PestControlServices>) objects[0];
        mPest = (RecyclerView)objects[1];
        orig = (LatLng) objects[2];
        context = (Context) objects[3];
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
        PestControlServicesAdapter adapter = new PestControlServicesAdapter(thisList, context, orig);
        RecyclerView.LayoutManager recyce = new GridLayoutManager(context,1);
        mPest.setLayoutManager(recyce);
        mPest.setAdapter(adapter);
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
