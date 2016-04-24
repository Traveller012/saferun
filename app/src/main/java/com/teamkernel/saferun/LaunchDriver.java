package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class LaunchDriver extends AppCompatActivity {
    Firebase rootFirebase;
    Firebase runsFirebase;
    Firebase activeRunsFirebase;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_driver);

        //root Firebase
        rootFirebase = new Firebase("https://saferun.firebaseio.com");
        runsFirebase = rootFirebase.child("Runs");
        activeRunsFirebase = rootFirebase.child("ActiveRuns");

        rootFirebase.child("message").setValue("Do you have data? You'll love.");

        name = MyUtils.getValueFromSharedPrefs("name", this);


        //for each new run added
        activeRunsFirebase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("ss", "snap: " + snapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError error) {

            }

        });

    }
    public void driverJoinRun(View view){

        //get RunID

        //create Driver

        //store Driver Key



        Intent intent = new Intent(this, DriverMapView.class);
        startActivity(intent);
    }

    public void driverGoBackToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

