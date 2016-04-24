package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EndRunConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_run_confirmation);
    }

    public void endRun(View view) {
        Intent intent = new Intent(this, FinalSummary.class);
        startActivity(intent);
    }

    public void goBackToRun(View view) {
        Intent intent = new Intent(this, FacilitatorMapView.class);
        startActivity(intent);
    }
}
