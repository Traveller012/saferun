package com.teamkernel.saferun;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by suhani on 4/24/16.
 */
public class MyUtils {

    public static Firebase rootFirebase = new Firebase("https://saferun.firebaseio.com");

    public static void createNewFacilitator(Facilitator facilitator, Context context) {

        String runKey = getValueFromSharedPrefs("runKey", context);
        Firebase myFirebase = rootFirebase.child("/Runs/" + runKey + "/Facilitators");


        Firebase myNewFirebase = myFirebase.push(); //get a new push ID
        String key = myNewFirebase.getKey();
        //add key to object
        facilitator.key = key;
        myNewFirebase.setValue(facilitator); //set object in Firebase

        //save facilitatorKey in sharedPref
        MyUtils.putInSharedPrefs("facilitatorKey", key, context);

    }

    public static boolean createNewDriver(Driver driver, Context context) {

        String runKey = getValueFromSharedPrefs("runKey", context);

        if(runKey.trim().isEmpty()){
            return false;
        }

        Firebase myFirebase = rootFirebase.child("/Runs/" + runKey + "/Drivers");


        Firebase myNewFirebase = myFirebase.push(); //get a new push ID
        String key = myNewFirebase.getKey();

        //add key to object
        driver.key = key;

        myNewFirebase.setValue(driver); //set object in Firebase


        //save facilitatorKey in sharedPref
        MyUtils.putInSharedPrefs("driverKey", key, context);

        return true;

    }

    public static boolean createNewObserver(Observer observer, Context context) {

        String runKey = getValueFromSharedPrefs("runKey", context);

        if(runKey.trim().isEmpty()){
            return false;
        }

        Firebase myFirebase = rootFirebase.child("/Runs/" + runKey + "/Observers");

        Firebase myNewFirebase = myFirebase.push(); //get a new push ID
        String key = myNewFirebase.getKey();

        //add key to object
        observer.key = key;

        myNewFirebase.setValue(observer); //set object in Firebase


        //save facilitatorKey in sharedPref
        MyUtils.putInSharedPrefs("observerKey", key, context);

        return true;

    }

    public static boolean updateObserver(Map<String, Object> observerData, Context context) {

        //get path
        String runKey = getValueFromSharedPrefs("runKey", context);
        String observerKey = MyUtils.getValueFromSharedPrefs("observerKey", context);
        if(runKey.trim().isEmpty() || observerKey.trim().isEmpty()){
            return false;
        }
        Firebase myFirebase = rootFirebase.child("/Runs/" + runKey + "/Observers/" + observerKey);

        //update data
        myFirebase.updateChildren(observerData);

        return true;

    }


    public static boolean removeRun(Context context) {

        String runKey = getValueFromSharedPrefs("runKey", context);
        if(runKey.trim().isEmpty()){
            return false;
        }
        Firebase myFirebase = rootFirebase.child("/Runs/" + runKey);

        myFirebase.removeValue();


        //also remove from ActiveRuns
        myFirebase = rootFirebase.child("/ActiveRuns/" + runKey);

        myFirebase.removeValue();


        return true;
    }

    public static boolean removeUser(User user, Context context) {

        /*
            http://saferun.firebaseio.com/Runs/RunKey/Facilitators/FacilitatorKey
            http://saferun.firebaseio.com/Runs/RunKey/Observers/ObserverKey
            http://saferun.firebaseio.com/Runs/RunKey/Drivers/DriverKey
        */
        String runKey = getValueFromSharedPrefs("runKey", context);
        if(runKey.trim().isEmpty()){
            return false;
        }
        Firebase myFirebase = rootFirebase.child("/Runs/" + runKey);

        switch (user){

            case Facilitator:
                String facilitatorKey = getValueFromSharedPrefs("facilitatorKey", context);

                if(facilitatorKey.isEmpty()) return false;

                myFirebase = myFirebase.child("/Facilitators/"+facilitatorKey);
                break;

            case Observer:

                String observerKey = getValueFromSharedPrefs("observerKey", context);

                if(observerKey.isEmpty()) return false;

                myFirebase = myFirebase.child("/Observers/"+observerKey);

                break;

            case Driver:

                String driverKey = getValueFromSharedPrefs("driverKey", context);

                if(driverKey.isEmpty()) return false;

                myFirebase = myFirebase.child("/Drivers/"+driverKey);

                break;
        }

        //update data
        myFirebase.removeValue();

        return true;

    }

    public static void createNewActiveRun(Run myRun, Context context) {

        //PUT myRun in root/ActiveRuns/RunKey/
        String runKey = getValueFromSharedPrefs("runKey", context);
        Firebase myFacilitatorFirebase = rootFirebase.child("/ActiveRuns/"+runKey);

        //insert name and runKey into /ActiveRuns/RunKey/
        myFacilitatorFirebase.setValue(myRun);

    }


    public static boolean createNewEmergency(Emergency myEmergency, Context context) {

        String runKey = getValueFromSharedPrefs("runKey", context);

        if(runKey.trim().isEmpty()){
            return false;
        }

        //PUT emergency in root/Runs/RunKey/Emergencies
        Firebase myFirebase = rootFirebase.child("/Runs/"+runKey+"/Emergencies");

        Firebase myNewFirebase = myFirebase.push(); //get a new push ID
        String key = myNewFirebase.getKey();
        //add key to object
        myEmergency.key = key;
        myNewFirebase.setValue(myEmergency); //set object in Firebase

        //save facilitatorKey in sharedPref
        MyUtils.putInSharedPrefs("emergencyKey", key, context);

        return true;
    }

    public static String getValueFromSharedPrefs(String key, Context c){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        String name = preferences.getString(key, "");

        return name;
    }

    public static void putInSharedPrefs(String key, String value, Context c) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static void removeFromSharedPrefs(String key, Context c) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static String createInFirebase(Object data, Firebase myFirebasePath) {

        Firebase myNewFirebase = myFirebasePath.push(); //get a new push ID

        myNewFirebase.setValue(data); //set object in Firebase

        String key = myNewFirebase.getKey();//get key generated for new path
        return key;

    }

    public static boolean updateLocation(Location location, User user, Context context) {

        /*
            http://saferun.firebaseio.com/Runs/RunKey/Facilitators/FacilitatorKey
            http://saferun.firebaseio.com/Runs/RunKey/Observers/ObserverKey
            http://saferun.firebaseio.com/Runs/RunKey/Drivers/DriverKey
        */

        String runKey = getValueFromSharedPrefs("runKey", context);
        if(runKey.trim().isEmpty()){
            return false;
        }
        Firebase myFirebase = rootFirebase.child("/Runs/" + runKey);

        switch (user){

            case Facilitator:
                String facilitatorKey = getValueFromSharedPrefs("facilitatorKey", context);

                if(facilitatorKey.isEmpty()) return false;

                myFirebase = myFirebase.child("/Facilitators/"+facilitatorKey);
                break;

            case Observer:

                String observerKey = getValueFromSharedPrefs("observerKey", context);

                if(observerKey.isEmpty()) return false;

                myFirebase = myFirebase.child("/Observers/"+observerKey);

                break;

            case Driver:

                String driverKey = getValueFromSharedPrefs("driverKey", context);

                if(driverKey.isEmpty()) return false;

                myFirebase = myFirebase.child("/Drivers/"+driverKey);

                break;
        }

        Map<String, Object> myLocationData = new HashMap<String, Object>();
        myLocationData.put("latitude", location.getLatitude());
        myLocationData.put("longitude", location.getLongitude());
        myFirebase.updateChildren(myLocationData);

        return true;
    }


}
enum User {
    Facilitator, Observer, Driver
}

class Facilitator{

    public Facilitator() {
    }

    public String key;
    public String name;
    public String latitude;
    public String longitude;


    public Facilitator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Driver{

    public Driver() {
    }

    public String key;
    public String name;
    public String latitude;
    public String longitude;


    public Driver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


class Observer{

    public String key;
    public String name;
    public boolean inPosition;
    public String latitude;
    public String longitude;


    public Observer() {
    }

    public Observer(String name, boolean inPosition) {
        this.name = name;
        this.inPosition = inPosition;
    }

    public boolean getInPosition() {
        return inPosition;
    }

    public String getName() {
        return name;
    }
}

class Run {
    public Run(String name, String runKey) {
        this.name = name;
        this.runKey = runKey;
    }

    //attributes must match the ones stored in firebase
    public String name;
    public String runKey;

    public Run() {
    }
}


class Emergency {

    public Emergency(String name, String latitude, String longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //attributes must match the ones stored in firebase
    public String key;
    public String name;
    public String latitude;
    public String longitude;

    public Emergency() {
    }
}

