package com.samkeet.smartrevaadmin.placements;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;
import com.samkeet.smartrevaadmin.fees.ViewFeeActivity;

public class PlacementsMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placements_main);
    }

    public void BackButton (View v){finish();}
}
