package com.teamkernel.saferun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button mFacilitatorButton;
    private Button mDriverButton;
    private Button mObserverButton;
    private String sUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFacilitatorButton = (Button)findViewById(R.id.facilitator_btn);
        mFacilitatorButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                checkTextEdit();
            }

        });

        mDriverButton = (Button)findViewById(R.id.driver_btn);
        mDriverButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                checkTextEdit();
            }

        });

        mObserverButton = (Button) findViewById(R.id.observer_btn);
        mObserverButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                checkTextEdit();
            }
        });
    }


    private void checkTextEdit() {
        EditText usernameEditText = (EditText) findViewById(R.id.user_name);
        sUsername = usernameEditText.getText().toString();

        if (sUsername.matches("")) {
            mDriverButton.setEnabled(true);
            mObserverButton.setEnabled(true);
            mFacilitatorButton.setEnabled(true);

        } else {
            mDriverButton.setEnabled(false);
            mObserverButton.setEnabled(false);
            mFacilitatorButton.setEnabled(false);
        }

    }


}
