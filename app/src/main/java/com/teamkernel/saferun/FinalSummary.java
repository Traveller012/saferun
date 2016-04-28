package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FinalSummary extends AppCompatActivity {

    long time_elapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_summary);

        Bundle bundle = getIntent().getExtras();
        time_elapsed = bundle.getLong("elapsed_time");

        String time_elapsed_string = formatString(time_elapsed);

        TextView v = (TextView) findViewById(R.id.total_time);
        v.setText(time_elapsed_string);
        v.setTextSize(23);
        v.setTextColor(getResources().getColor(R.color.colorGreyFont));



    }

    public String formatString(long time_elapsed){

        String formatted_string = "";

        int mins = (int)time_elapsed / (60 * 1000);
        int secs = (int) (time_elapsed % (60 * 1000)) / 1000;

        String minutes = Integer.toString(mins);
        String seconds = Integer.toString(secs);
        if (mins < 10)
            if (secs < 10) {
                formatted_string += "Time Elapsed: 00:0" + minutes + ":0" + seconds;
            } else {
               formatted_string += "Time Elapsed: 00:0" + minutes + ":" + seconds;
            }
        else if (secs < 10) {
           formatted_string += "Time Elapsed: 00:" + minutes + ":0" + seconds;
        }
        else {
            formatted_string += "Time Elapsed: 00:" + minutes + ":" + seconds;
        }

        return formatted_string;
    }

    public void finalExit(View view){

        //pop all
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
