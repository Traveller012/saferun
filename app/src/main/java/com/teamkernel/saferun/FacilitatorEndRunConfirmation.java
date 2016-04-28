package com.teamkernel.saferun;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class FacilitatorEndRunConfirmation extends AppCompatActivity {

    long time_elapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilitator_end_run_confirmation);

        Bundle bundle = getIntent().getExtras();
        time_elapsed = bundle.getLong("elapsed_time");
    }

    public void endRun(View view) {

        MyUtils.removeUser(User.Facilitator, this);
        MyUtils.removeRun(this);

        //pop all
        Intent intent = new Intent(this, FinalSummary.class);
        Bundle b = new Bundle();
        b.putLong("elapsed_time", time_elapsed);
        intent.putExtras(b);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void goBackToRun(View view) {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
