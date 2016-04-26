package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

public class LaunchObserver extends AppCompatActivity {
    Firebase rootFirebase = new Firebase("https://saferun.firebaseio.com");
    Firebase runsFirebase  = rootFirebase.child("Runs");
    Firebase activeRunsFirebase = rootFirebase.child("ActiveRuns");

    String name;
    HashMap<String,String> activeRunsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_observer);


        //clear any run key
        MyUtils.removeFromSharedPrefs("runKey",this);

        name = MyUtils.getValueFromSharedPrefs("name", this);

        final RadioGroup runs_available = (RadioGroup) findViewById(R.id.observer_choices);
        final LinearLayout.LayoutParams radiogroupparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        if (runs_available != null) {
            runs_available.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup rGroup, int checkedId)
                {
                    // This will get the radiobutton that has changed in its check state
                    RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedId);
                    // This puts the value (true/false) into the variable
                    boolean isChecked = checkedRadioButton.isChecked();
                    // If the radiobutton that has changed in check state is now checked...
                    if (isChecked)
                    {
                        // Changes the textview's text to "Checked: example radiobutton text"

                        String facilitator_name = checkedRadioButton.getText().toString();
                        Log.d("ss", "Checked:" + facilitator_name);

                        String runKey = activeRunsMap.get(facilitator_name);
                        Log.d("ss", "Checked key:" + runKey);

                        MyUtils.putInSharedPrefs("runKey",runKey,LaunchObserver.this);

                    }
                }
            });
        }

        //for each new run added
        activeRunsFirebase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("ss", "snap: " + snapshot.getValue());

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Run myRun = postSnapshot.getValue(Run.class);

                    if(!myRun.name.trim().isEmpty()){

                        RadioButton rb = new RadioButton(LaunchObserver.this);
                        rb.setText(myRun.name);
                        runs_available.addView(rb, 0, radiogroupparams);
                        activeRunsMap.put(myRun.name, myRun.runKey);

                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError error) {

            }

        });
    }

    public void observerJoinRun(View view){

        //create observer
        boolean result = MyUtils.createNewObserver(new Observer(name, false),this);

        if(result){
            //start next activity
            Intent intent = new Intent(this, ObserverInPosition.class);
            startActivity(intent);
        }
        else {
            //toast user to choose a run first
            Toast.makeText(this, "Please choose a run first", Toast.LENGTH_SHORT).show();
        }

    }

    public void observerGoBackToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
