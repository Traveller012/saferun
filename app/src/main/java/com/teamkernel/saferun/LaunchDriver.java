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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import java.util.HashMap;

public class LaunchDriver extends AppCompatActivity implements LocationListener {
    Firebase rootFirebase;
    Firebase runsFirebase;
    Firebase activeRunsFirebase;
    String name;
    HashMap<String, String> activeRunsMap = new HashMap<>();
    protected LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_driver);

        //clear any run key
        MyUtils.removeFromSharedPrefs("runKey", this);

        //root Firebase
        rootFirebase = new Firebase("https://saferun.firebaseio.com");
        runsFirebase = rootFirebase.child("Runs");
        activeRunsFirebase = rootFirebase.child("ActiveRuns");

        name = MyUtils.getValueFromSharedPrefs("name", this);

        final RadioGroup runs_available = (RadioGroup) findViewById(R.id.choices);
        final LinearLayout.LayoutParams radiogroupparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        if (runs_available != null) {
            runs_available.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
                    // This will get the radiobutton that has changed in its check state
                    RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(checkedId);
                    // This puts the value (true/false) into the variable
                    boolean isChecked = checkedRadioButton.isChecked();
                    // If the radiobutton that has changed in check state is now checked...
                    if (isChecked) {
                        // Changes the textview's text to "Checked: example radiobutton text"

                        String facilitator_name = checkedRadioButton.getText().toString();
                        Log.d("ss", "Checked:" + facilitator_name);

                        String runKey = activeRunsMap.get(facilitator_name);
                        Log.d("ss", "Checked key:" + runKey);

                        MyUtils.putInSharedPrefs("runKey", runKey, LaunchDriver.this);

                    }
                }
            });
        }

        //for each new run added
        activeRunsFirebase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("ss", "snap: " + snapshot.getValue());

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Run myRun = postSnapshot.getValue(Run.class);

                    if (!myRun.name.trim().isEmpty()) {

                        RadioButton rb = new RadioButton(LaunchDriver.this);
                        rb.setText(myRun.name);
                        runs_available.addView(rb, 0, radiogroupparams);
                        activeRunsMap.put(myRun.name, myRun.runKey);

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {

            }

        });

    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d("ss","Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        //update in DB
        MyUtils.updateLocation(location, User.Driver, LaunchDriver.this);

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
            MyUtils.updateLocation(location, User.Driver, LaunchDriver.this);
        }
        MyUtils.updateLocation(location, User.Driver, LaunchDriver.this);
    }

    public void driverJoinRun(View view) {

        //create driver
        boolean result = MyUtils.createNewDriver(new Driver(name), this);

        if (result) {

            updateMyLocation();

            //start next activity
            Intent intent = new Intent(this, DriverMapView.class);
            startActivity(intent);
        } else {
            //toast user to choose a run first
            Toast.makeText(this, "Please choose a run first", Toast.LENGTH_SHORT).show();
        }

    }

    public void driverGoBackToMain(View view){

        finish();

    }

    public void displayJoinRun(View view) {
        TextView selectedFacilitator = (TextView) view;
        String text = selectedFacilitator.getText().toString();
        String message = "Join " + text + "'s Run!";
        Button b = new Button(this);
        b.setText(message);
       // b.setLayoutParams();




    }
}

