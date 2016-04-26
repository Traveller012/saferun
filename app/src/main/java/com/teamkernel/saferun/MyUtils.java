package com.teamkernel.saferun;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

        String key = MyUtils.createInFirebase(facilitator, myFirebase);

        //save facilitatorKey in sharedPref
        MyUtils.putInSharedPrefs("facilitatorKey", key, context);

    }

    public static boolean createNewDriver(Driver driver, Context context) {

        String runKey = getValueFromSharedPrefs("runKey", context);

        if(runKey.trim().isEmpty()){
            return false;
        }

        Firebase myFirebase = rootFirebase.child("/Runs/" + runKey + "/Drivers");

        String key = MyUtils.createInFirebase(driver, myFirebase);

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

        String key = MyUtils.createInFirebase(observer, myFirebase);

        //save facilitatorKey in sharedPref
        MyUtils.putInSharedPrefs("observerKey", key, context);

        return true;

    }

    public static void createNewActiveRun(Run myRun, Context context) {

        //PUT myRun in root/ActiveRuns/RunKey/
        String runKey = getValueFromSharedPrefs("runKey", context);
        Firebase myFacilitatorFirebase = rootFirebase.child("/ActiveRuns/"+runKey);

        //insert name and runKey into /ActiveRuns/RunKey/
        myFacilitatorFirebase.setValue(myRun);

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

    public static void updateLocation(String latitude, String longitude, Firebase myFirebasePath) {
        Map<String, Object> myLocationData = new HashMap<String, Object>();
        myLocationData.put("lat", latitude);
        myLocationData.put("long", longitude);
        myFirebasePath.updateChildren(myLocationData);
    }


}

class Facilitator{

    public Facilitator() {
    }

    public String name;

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

    public String name;

    public Driver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


class Observer{

    public String name;
    public boolean inPosition;

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