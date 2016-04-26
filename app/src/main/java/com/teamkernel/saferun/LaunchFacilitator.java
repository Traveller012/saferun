package com.teamkernel.saferun;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;


public class LaunchFacilitator extends AppCompatActivity  implements LocationListener {
    Firebase rootFirebase;
    Firebase runsFirebase;
    Firebase activeRunsFirebase;
    String name;
    protected LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_facilitator);

        //root Firebase
        rootFirebase = new Firebase("https://saferun.firebaseio.com");
        runsFirebase = rootFirebase.child("Runs");
        activeRunsFirebase = rootFirebase.child("ActiveRuns");

        name = MyUtils.getValueFromSharedPrefs("name", this);
    }

    public void organizeRun(View view){

        //create /Runs/RunKey and save RunKey in sharedPref
        Map<String, String> runData = new HashMap<String, String>();
        runData.put("name", name);
        String runKey = MyUtils.createInFirebase(runData, runsFirebase);
        MyUtils.putInSharedPrefs("runKey",runKey, this);//insert key in sharedPrefs

        //create facilitator and save facilitatorKey in sharedPref
        Facilitator facilitator = new Facilitator();
        facilitator.name = name;
        MyUtils.createNewFacilitator(facilitator, this);

        //create/update facilitator location
        updateMyLocation();

        //insert name and runKey into /ActiveRuns/RunKey/
        Run myRun = new Run();
        myRun.runKey = runKey;
        myRun.name = name;
        MyUtils.createNewActiveRun(myRun, this);

        Intent intent = new Intent(this, OrganizeNewRun.class);
        startActivity(intent);
    }


    public void updateMyLocation(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("ss","error");
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            Log.d("ss","Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
            MyUtils.updateLocation(location, User.Facilitator, LaunchFacilitator.this);
        }
        MyUtils.updateLocation(location, User.Facilitator, LaunchFacilitator.this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("ss","Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        //update in DB
        MyUtils.updateLocation(location, User.Facilitator, LaunchFacilitator.this);

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("ss","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("ss","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("ss","status");
    }


    public void goBackToMain(View view){
        finish();
    }

}
