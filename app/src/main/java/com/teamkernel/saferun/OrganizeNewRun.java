package com.teamkernel.saferun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

public class OrganizeNewRun extends AppCompatActivity {
    Firebase rootFirebase;
    Firebase myCurrentRunFirebase;
    Firebase myCurrentDriversFirebase;
    Firebase myCurrentObserversFirebase;
    HashMap<String, Driver> driversMap = new HashMap<>();//driverKey vs Driver
    HashMap<String, Observer> observersMap = new HashMap<>();//observerKey vs Observer
    TextView drivers_text_view;
    TextView observers_text_view;
    String runKey;

    //Facilitator 2 activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize_new_run);

        drivers_text_view = (TextView) findViewById(R.id.list_of_drivers);
        observers_text_view = (TextView) findViewById(R.id.list_of_observers);

        runKey = MyUtils.getValueFromSharedPrefs("runKey",this);
        if(runKey.isEmpty()){
            //something is seriously wrong
            finish();
        }

        //root Firebase
        rootFirebase = new Firebase("https://saferun.firebaseio.com");
        myCurrentRunFirebase = rootFirebase.child("Runs/"+runKey);

        myCurrentDriversFirebase = myCurrentRunFirebase.child("Drivers");
        myCurrentObserversFirebase = myCurrentRunFirebase.child("Observers");


        //for each new driver added
        myCurrentDriversFirebase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Log.d("ss", "snap: " + snapshot.getValue());
                Driver driver = snapshot.getValue(Driver.class);

                //add new
                driversMap.put(driver.key,driver);
                //update
                updateDriversText();
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {

                Log.d("ss", "snap: " + snapshot.getValue());

                Driver driver = snapshot.getValue(Driver.class);

                if(driversMap.get(driver.key)!=null){    //found

                    //remove old and add new
                    driversMap.remove(driver.key);
                    driversMap.put(driver.key,driver);
                    //update
                    updateDriversText();
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                Log.d("ss", "snap: " + snapshot.getValue());

                Driver driver = snapshot.getValue(Driver.class);

                if(driversMap.get(driver.key)!=null){    //found

                    //remove
                    driversMap.remove(driver.key);
                    //update
                    updateDriversText();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError error) {

            }

        });


        //for each new observer added
        myCurrentObserversFirebase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Log.d("ss", "snap: " + snapshot.getValue());
                Observer observer = snapshot.getValue(Observer.class);

                //add new
                observersMap.put(observer.key,observer);
                //update
                updateObserversText();
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {

                Log.d("ss", "snap: " + snapshot.getValue());

                Observer observer = snapshot.getValue(Observer.class);

                if(observersMap.get(observer.key)!=null){    //found

                    //remove old and add new
                    observersMap.remove(observer.key);
                    observersMap.put(observer.key,observer);
                    //update
                    updateObserversText();
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                Log.d("ss", "snap: " + snapshot.getValue());

                Observer observer = snapshot.getValue(Observer.class);

                if(observersMap.get(observer.key)!=null){    //found

                    //remove
                    observersMap.remove(observer.key);
                    //update
                    updateObserversText();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError error) {

            }

        });


    }

    public void updateDriversText(){

        //reset text in text view
        String drivers_list = "Drivers:<br/>";

        for (String key : driversMap.keySet()) {
            Log.d("ss", "key: " + key + " value: " + driversMap.get(key));
            drivers_list+= "<font color='#00FF00'>" + driversMap.get(key).name + "</font><br/>";//green
        }
        drivers_text_view.setText(Html.fromHtml(drivers_list));

    }

    public void updateObserversText(){

        //reset text in text view
        String observers_list = "Observers:<br/>";

        for (String key : observersMap.keySet()) {
            Log.d("ss", "key: " + key + " value: " + observersMap.get(key));

            if(observersMap.get(key).inPosition)
                observers_list+= "<font color='#00FF00'>" + observersMap.get(key).name + "</font><br/>";//green
            else
                observers_list+= "<font color='#FF0000'>" + observersMap.get(key).name + "</font><br/>";//red

        }
        observers_text_view.setText(Html.fromHtml(observers_list));

    }

    public void startRunTime(View view){
        Intent intent = new Intent(this, FacilitatorMapView.class);
        startActivity(intent);
    }

    public void exit(View view){
        Intent intent = new Intent(this, FacilitatorExitRunConfirmation.class);
        startActivity(intent);
    }

    public void refresh(View view){
       //dummy method
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("ss", "onKeyDown Called");

            Intent intent = new Intent(this, FacilitatorExitRunConfirmation.class);
            startActivity(intent);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
