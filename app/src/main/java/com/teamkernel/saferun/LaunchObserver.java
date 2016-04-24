package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LaunchObserver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_observer);
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(username);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.launchObserver);
        layout.addView(textView);
    }
}
