package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LaunchFacilitator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_facilitator);

    }

    public void organizeRun(View view){
            Intent intent = new Intent(this, OrganizeNewRun.class);
            //intent.putExtra(EXTRA_USERNAME, sUsername);
            startActivity(intent);
    }
}
