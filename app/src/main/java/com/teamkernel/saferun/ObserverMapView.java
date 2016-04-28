package com.teamkernel.saferun;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ObserverMapView extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    protected LocationManager locationManager;
    Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer_map_view);


        timer = (Chronometer) findViewById(R.id.chronometer3);
        timer.setBase(SystemClock.elapsedRealtime());
        timer.setFormat("Elapsed Time: %s");
        timer.start();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
               .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void observerExitRunConfirmation(View view) {
        Intent intent = new Intent(this, ObserverExitRunConfirmation.class);
        startActivity(intent);
    }

   /* public void observerInPosition(View view){
        TextView text = (TextView)findViewById(R.id.observer_map_view_bottom_text);
        text.setText("Time Elapsed: ");
    } */

    public void observerCreateEmergency(View view) {


        Button emergency_button = (Button) findViewById(R.id.observer_create_emergency);


        if(emergency_button.getText().toString().equalsIgnoreCase("EMERGENCY!")){

            //create the emergency
            /*String name = MyUtils.getValueFromSharedPrefs("name",this);
            Location location = getMyLocation();

            if(location==null){

                return;//something is wrong
            }

            MyUtils.createNewEmergency(new Emergency(name,location.getLatitude()+"",location.getLongitude()+""),this); */

            //if "Create Emergency"
            emergency_button.setText("Clear Emergency");


            /*TextView text = (TextView)findViewById(R.id.observer_map_view_bottom_text);
            text.setText("Driver Notified!\nElapsed Time:");*/


        }
        else{

            //clear the emergency
          /*  MyUtils.removeEmergency(this);*/

            //reset emergency button
            emergency_button.setText("Emergency!");


            /*TextView text = (TextView)findViewById(R.id.observer_map_view_bottom_text);
            text.setText("Elapsed Time:");*/

        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            Intent intent = new Intent(this, ObserverExitRunConfirmation.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }


    public Location getMyLocation(){
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
            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            Log.d("ss","Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        }

        return location;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(40.809527, -73.960269);
        mMap.addMarker(new MarkerOptions().position(sydney).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
