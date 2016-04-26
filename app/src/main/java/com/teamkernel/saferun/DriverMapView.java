package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DriverMapView extends FragmentActivity implements OnMapReadyCallback {

    public final static String EXTRA_TRACEBACK = "com.teamkernel.saferun.TRACEBACK";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                //.findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
    }

    public void exitRunConfirmation(View view) {

        Intent intent = new Intent(this, DriverExitRunConfirmation.class);
        startActivity(intent);
    }


    public void driverResolveEmergency(View view) {

        //assuming emergency key is set
        MyUtils.removeEmergency(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("ss", "onKeyDown Called");

            Intent intent = new Intent(this, DriverExitRunConfirmation.class);
            startActivity(intent);

            return true;
        }
        return super.onKeyDown(keyCode, event);
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
   // @Override
   public void onMapReady(GoogleMap googleMap) {

       mMap = googleMap;

        // Add a marker in Sydney and move the camera

      LatLng sydney = new LatLng(-34, 151);
      mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
