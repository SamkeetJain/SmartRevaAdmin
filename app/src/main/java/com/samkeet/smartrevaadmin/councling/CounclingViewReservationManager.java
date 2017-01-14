package com.samkeet.smartrevaadmin.councling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class CounclingViewReservationManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_councling_view_reservation_manager);
    }
    public void BackButton (View v){finish();}
}
