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

    public static String createInFirebase(Map<String, String> data, Firebase myFirebasePath) {

        Firebase myNewFirebase = myFirebasePath.push(); //get a new push ID

        myNewFirebase.setValue(data);

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
