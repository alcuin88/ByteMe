package com.example.adefault.bytemeV3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adefault.bytemeV3.GeoFence.GeoFenceResponse;
import com.example.adefault.bytemeV3.GeoFence.GeoFenceProcess;
import com.example.adefault.bytemeV3.GeoFence.Points;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class NavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = "MapsActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View navHeader;
    private Toolbar toolbar = null;
    private AdView mAdview;
    private GoogleSignInClient mGoogleSignInClient;
    private LatLng myLocation = null;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private List<GeoFenceResponse> list;
    private TextView userName;
    private TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refIDs();
        getPoints();
    }

    private void refIDs(){
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHeader = navigationView.inflateHeaderView(R.layout.nav_header_nav_drawer);
        mAuth = FirebaseAuth.getInstance();
        userName = navHeader.findViewById(R.id.userName);
        userEmail = navHeader.findViewById(R.id.userEmail);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userName.setText(currentUser.getDisplayName());
        userEmail.setText(currentUser.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent logOutIntent = new Intent(NavDrawer.this, Login.class);
            logOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mGoogleSignInClient.signOut();
            mGoogleSignInClient.revokeAccess();
            startActivity(logOutIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       switch (id) {
            // Handle the camera action

           case R.id.nav_scan:
               Intent sc = new Intent(NavDrawer.this, ScanModeForm.class);
               startActivity(sc);
               break;

           case R.id.nav_list:
               Intent l = new Intent(NavDrawer.this, InsectFamilyList.class);
               startActivity(l);
               break;

           case R.id.nav_pestcontrolservices:
               Intent p = new Intent(NavDrawer.this, MapsActivity.class);
               startActivity(p);
               break;

           case R.id.nav_settings:
               Intent s = new Intent(NavDrawer.this, Settings.class);
               startActivity(s);
               break;

           case R.id.nav_about:
               Intent a = new Intent(NavDrawer.this, About.class);
               startActivity(a);
               break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting Location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                getDeviceLocation();
            }
            else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current Location");

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: found Location");

                    Location currentLocation = (Location)task.getResult();
                    if(currentLocation != null){
                        myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        GeoFenceProcess process = new GeoFenceProcess();

                        if(process.main(list, myLocation)){
                            MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

                            mAdview = findViewById(R.id.adView);
                            AdRequest adRequest = new AdRequest.Builder().addTestDevice("42367EBF74D6DF0D092C50CA05C7B684").build();
                            mAdview.loadAd(adRequest);
                        }
                    }
                    else
                        Toast.makeText(this, "Unable to get current Location", LENGTH_SHORT).show();
                }
                else{
                    Log.d(TAG, "onComplete: current Location is null");
                    Toast.makeText(NavDrawer.this, "unable to get current Location", LENGTH_SHORT).show();
                }
            });
        }catch (SecurityException e){
            Log.d(TAG, "getDeviceLocation: SecurityException" + e.getMessage());
        }
    }

    public void getPoints(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("GeoFence");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                Log.d(TAG, "GET POINTS");
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    GeoFenceResponse value = dataSnapshot1.getValue(GeoFenceResponse.class);
                    GeoFenceResponse response = new GeoFenceResponse();
                    response.setCity(value.getCity());
                    response.setPoints(value.getPoints());

                    list.add(response);
                }
                getLocationPermission();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read GeoFenceResponse
                Log.w(TAG, "Failed to read GeoFenceResponse.", error.toException());
            }
        });
    }

}
