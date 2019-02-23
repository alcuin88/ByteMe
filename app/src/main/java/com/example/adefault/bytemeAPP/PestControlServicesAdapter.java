package com.example.adefault.bytemeAPP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class PestControlServicesAdapter extends
        RecyclerView.Adapter<PestControlServicesAdapter.ViewHolder>{

    private static final String API_KEY = "AIzaSyACOex929TfptSnad16icgpy-QXh-hgJCM";
    private Context context;
    private List<PestControlServicesResponse> mPestList;
    private LatLng orig;
    private GoogleMap mMap;
    private BottomSheetBehavior bottomSheetBehavior;
    private Button showList;

    public PestControlServicesAdapter(List<PestControlServicesResponse> PestsList, Context context,
                                      LatLng orig, GoogleMap mMap, BottomSheetBehavior bottomSheetBehavior, Button showList) {
        mPestList = PestsList;
        this.context = context;
        this.orig = orig;
        this.mMap = mMap;
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.showList = showList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.pest_control_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        PestControlServicesResponse pestControlServicesResponse = mPestList.get(position);

        // Set item views based on your views and data model

        TextView pestControlName = viewHolder.pestControlName;
        TextView distance = viewHolder.distance;
        TextView duration = viewHolder.duration;

        CardView cardView = viewHolder.cardView;

        pestControlName.setText(pestControlServicesResponse.getName());
        distance.setText(pestControlServicesResponse.getDistance());
        duration.setText(pestControlServicesResponse.getDuration());

        cardView.setOnClickListener(v -> {
            mMap.clear();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            MarkerOptions options = new MarkerOptions()
                    .position(pestControlServicesResponse.getLatLng())
                    .title(pestControlServicesResponse.getName());
            mMap.addMarker(options);
            nav(pestControlServicesResponse.getLatLng());
        });

    }

    private void nav(LatLng dest){
        String url = getRequestUrl(orig, dest);
        TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
        taskRequestDirections.execute(url);
    }

    @SuppressLint("StaticFieldLeak")
    public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try{
                DownloadUrl downloadUrl = new DownloadUrl();
                responseString = downloadUrl.readUrl(strings[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MapsActivity.TaskParser taskParser = new MapsActivity.TaskParser();
            taskParser.execute(s);
        }
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

    @Override
    public int getItemCount() {
        int arr = 0;

        try{
            if(this.mPestList.size()==0){

                arr = 0;

            }
            else{

                arr=this.mPestList.size();
            }



        }catch (Exception e){

            Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();

        }

        return arr;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pestControlName;
        public TextView distance;
        public TextView duration;


        public CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            pestControlName = itemView.findViewById(R.id.pest_service_name);
            distance = itemView.findViewById(R.id.distance);
            duration = itemView.findViewById(R.id.duration);
            cardView = itemView.findViewById(R.id.pest_card_view);
        }
    }
}
