package com.samkeet.smartrevaadmin.Counselling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class CounclingNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_councling_notification);
    }
    public void BackButton (View v){finish();}

}
