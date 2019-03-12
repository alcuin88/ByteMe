package com.example.adefault.bytemeV3;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.example.adefault.bytemeV3.Adapters.PestControlServicesAdapter;
import com.example.adefault.bytemeV3.databaseObjects.PestControlServicesResponse;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    private String API_KEY = "";

    private RecyclerView mPest;
    private GoogleMap mMap;
    private String url, directionsUrl;
    private String googlePlacesData, googleDirectionsData;
    private String duration, distance;
    private List<PestControlServices> list;
    private LatLng orig;
    private ArrayList<PestControlServicesResponse> thisList;
    private Context context;
    private BottomSheetBehavior bottomSheetBehavior;
    private Button showList;
    private TextView currentRadius;
    MapsActivity mapsActivity;

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
        currentRadius = (TextView) objects[8];

        DownloadUrl downloadUrl = new DownloadUrl();
        url = getRequestUrl(orig);
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        thisList = new ArrayList<>();
        List<HashMap<String, String>> placesList = null;
        DataParser parser = new DataParser();
        placesList = parser.parseDirections(googlePlacesData, "places");
        PestControlServicesResponse value;

        for(int i = 0; i < placesList.size(); i++){
            googleDirectionsData = "";
            value = new PestControlServicesResponse();
            value.setName(placesList.get(i).get("name"));
            value.setOpening_hours(placesList.get(i).get("opening_hours").equalsIgnoreCase("true"));
            double lat, lng;
            lat = Double.parseDouble(placesList.get(i).get("lat"));
            lng = Double.parseDouble(placesList.get(i).get("lng"));
            LatLng latLng = new LatLng(lat, lng);

            directionsUrl = getRequestDirectionsUrl(orig, latLng);

            downloadDirections downloadDirections = new downloadDirections();
            downloadDirections.execute();

            while(googleDirectionsData == ""){};

            List<HashMap<String, String>> directionsList = null;
            directionsList = parser.parseDirections(googleDirectionsData, "directions");

            duration = directionsList.get(0).get("duration");
            distance = directionsList.get(0).get("distance");
            value.setDuration("Duration: " + duration);
            value.setDistance("Distance: " + distance);

            value.setLatLng(latLng);
            thisList.add(value);
        }
        Display();
    }

    class downloadDirections extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            DownloadUrl downloadUrl = new DownloadUrl();
            try {
                googleDirectionsData = downloadUrl.readUrl(directionsUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void Display(){
        Collections.sort(thisList);
        Object[] transferData = new Object[9];

        List<PestControlServicesResponse> newList = new ArrayList<>();
        double radiusLimit = Double.parseDouble(currentRadius.getText().toString().substring(0, currentRadius.getText().toString().length()-3));
        for(int i = 0; i < thisList.size(); i++){
            double distance = Double.parseDouble(thisList.get(i).getDistance());
            if(distance <= radiusLimit){
                newList.add(thisList.get(i));
            }
        }
        transferData[0] = newList;
        transferData[1] = context;
        transferData[2] = orig;
        transferData[3] = mMap;
        transferData[4] = bottomSheetBehavior;
        transferData[5] = showList;
        transferData[6] = API_KEY;
        transferData[7] = currentRadius;
        PestControlServicesAdapter adapter = new PestControlServicesAdapter(transferData);
        RecyclerView.LayoutManager recyce = new GridLayoutManager(context,1);
        mPest.setLayoutManager(recyce);
        mPest.setAdapter(adapter);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
    }

    private String getRequestUrl(LatLng origin){
        String str_org = "location=" + origin.latitude + "," + origin.longitude;
        String query = "query=pest+control+services";
        String key = "key="+API_KEY;
        String radius = "radius=" + currentRadius.getText().toString()
                .substring(0, currentRadius.getText().toString().length() - 3);
        String param = query + "&" + str_org + "&" + radius + "&" + key;
        String output = "json";
        return "https://maps.googleapis.com/maps/api/place/textsearch/" + output + "?" + param;
    }

    private String getRequestDirectionsUrl(LatLng origin, LatLng dest){
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


