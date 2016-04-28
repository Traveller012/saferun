package com.teamkernel.saferun;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class ObserverInPosition extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    protected LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer_in_position);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void observerInPosition(View view){

        //update inPosition as true
        Map<String, Object> observerData = new HashMap<String, Object>();
        observerData.put("inPosition", true);
        MyUtils.updateObserver(observerData, this);


        Intent intent = new Intent(this, ObserverMapView.class);
        startActivity(intent);

    }

    public void observerExitRunConfirmation(View view){
        Intent intent = new Intent(this, ObserverExitRunConfirmation.class);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("ss","Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        //update in DB
        MyUtils.updateLocation(location, User.Observer, ObserverInPosition.this);

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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
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
}
