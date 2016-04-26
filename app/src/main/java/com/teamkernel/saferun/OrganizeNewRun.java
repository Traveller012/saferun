package com.teamkernel.saferun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class OrganizeNewRun extends AppCompatActivity {

    //Facilitator 2 activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize_new_run);

        //get all data

        //make on data change listeners

        //display it

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
