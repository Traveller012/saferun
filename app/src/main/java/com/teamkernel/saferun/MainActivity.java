package com.teamkernel.saferun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    private String sUsername;

    public final static String EXTRA_USERNAME = "com.teamkernel.saferun.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //let this be first
        Firebase.setAndroidContext(this);

        //clear any run key
        MyUtils.removeFromSharedPrefs("runKey",this);


        //clear any run key
        String name = MyUtils.getValueFromSharedPrefs("name",this);

        EditText usernameEditText = (EditText) findViewById(R.id.user_name);

        if (usernameEditText != null) {

            usernameEditText.setText(name, TextView.BufferType.EDITABLE);

            usernameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MyUtils.putInSharedPrefs("name", s.toString(), MainActivity.this);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    public void selectFacilitator(View view){
        Intent intent = new Intent(this, LaunchFacilitator.class);
        startActivity(intent);
    }

    public void selectDriver(View view){
        Intent intent = new Intent(this, LaunchDriver.class);
        startActivity(intent);
    }

    public void selectObserver(View view){
        Intent intent = new Intent(this, LaunchObserver.class);
        startActivity(intent);
    }


}
