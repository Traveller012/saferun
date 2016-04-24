package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OrganizeNewRun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize_new_run);
    }

    public void startRunTime(View view){
        Intent intent = new Intent(this, FacilitatorMapView.class);
        startActivity(intent);
    }

    public void exit(View view){
        Intent intent = new Intent(this, ExitRunConfirmation.class);
        startActivity(intent);
    }

    public void refresh(View view){
       //dummy method
    }
}
