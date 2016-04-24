package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LaunchDriver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_driver);

    }
    public void driverJoinRun(View view){
        Intent intent = new Intent(this, DriverMapView.class);
        startActivity(intent);
    }

    public void driverGoBackToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

