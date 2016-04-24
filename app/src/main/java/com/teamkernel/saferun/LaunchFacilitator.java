package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class LaunchFacilitator extends AppCompatActivity {
    Firebase rootFirebase;
    Firebase runsFirebase;
    Firebase activeRunsFirebase;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_facilitator);

        //root Firebase
        rootFirebase = new Firebase("https://saferun.firebaseio.com");
        runsFirebase = rootFirebase.child("Runs");
        activeRunsFirebase = rootFirebase.child("ActiveRuns");

        rootFirebase.child("message").setValue("Do you have data? You'll love.");

        name = MyUtils.getValueFromSharedPrefs("name", this);
    }

    public void organizeRun(View view){

        //create /Runs/RunKey and save RunKey in sharedPref
        Map<String, String> runData = new HashMap<String, String>();
        runData.put("name", name);
        String runKey = MyUtils.createInFirebase(runData, runsFirebase);
        MyUtils.putInSharedPrefs("runKey",runKey, this);//insert key in sharedPrefs

        //create /Runs/RunKey/Facilitators/FacilitatorKey and save facilitatorKey in sharedPref
        Firebase myFacilitatorFirebase = runsFirebase.child(runKey+"/Facilitators");
        Map<String, String> facilitatorData = new HashMap<String, String>();
        facilitatorData.put("name", name);
        facilitatorData.put("isActive", "1");
        String facilitatorKey = MyUtils.createInFirebase(facilitatorData, myFacilitatorFirebase);
        MyUtils.putInSharedPrefs("facilitatorKey",facilitatorKey, this);//insert key in sharedPrefs


        //insert name and runKey into /ActiveRuns/RunKey/
        Map<String, String> data = new HashMap<String, String>();
        data.put("runKey", runKey);
        data.put("name", name);
        activeRunsFirebase.child(runKey).setValue(data);

        Intent intent = new Intent(this, OrganizeNewRun.class);
        startActivity(intent);
    }

}
