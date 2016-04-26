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

        //insert name and runKey into /ActiveRuns/RunKey/
        Run myRun = new Run();
        myRun.runKey = runKey;
        myRun.name = name;
        MyUtils.createNewActiveRun(myRun, this);

        Intent intent = new Intent(this, OrganizeNewRun.class);
        startActivity(intent);
    }

    public void goBackToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
