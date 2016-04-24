package com.teamkernel.saferun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private String sUsername;

    public final static String EXTRA_USERNAME = "com.teamkernel.saferun.USERNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void selectFacilitator(View view){
        EditText usernameEditText = (EditText) findViewById(R.id.user_name);
        sUsername = usernameEditText.getText().toString();
        if (!sUsername.matches("")) {
            Intent intent = new Intent(this, LaunchFacilitator.class);
            intent.putExtra(EXTRA_USERNAME, sUsername);
            startActivity(intent);
        }
    }

    public void selectDriver(View view){
        EditText usernameEditText = (EditText) findViewById(R.id.user_name);
        sUsername = usernameEditText.getText().toString();
        if (!sUsername.matches("")) {
            Intent intent = new Intent(this, LaunchDriver.class);
            intent.putExtra(EXTRA_USERNAME, sUsername);
            startActivity(intent);
        }
    }

    public void selectObserver(View view){
        EditText usernameEditText = (EditText) findViewById(R.id.user_name);
        sUsername = usernameEditText.getText().toString();
        if (!sUsername.matches("")) {
            Intent intent = new Intent(this, LaunchObserver.class);
            intent.putExtra(EXTRA_USERNAME, sUsername);
            startActivity(intent);
        }
    }


}
