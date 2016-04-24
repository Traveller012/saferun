package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LaunchObserver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_observer);
    }

    public void observerJoinRun(View view){
        Intent intent = new Intent(this, ObserverInPosition.class);
        startActivity(intent);
    }

    public void observerGoBackToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
