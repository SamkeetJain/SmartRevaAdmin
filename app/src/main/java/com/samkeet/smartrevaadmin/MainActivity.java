package com.samkeet.smartrevaadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.Alumni.AlumniMainActivity;
import com.samkeet.smartrevaadmin.Counselling.CounclingMainActivity;
import com.samkeet.smartrevaadmin.Fees.FeesMainActivity;
import com.samkeet.smartrevaadmin.Placements.PlacementsMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void Placements(View v) {
        Intent intent = new Intent(getApplicationContext(), PlacementsMainActivity.class);
        startActivity(intent);
    }

    public void Councling(View v) {
        Intent intent = new Intent(getApplicationContext(), CounclingMainActivity.class);
        startActivity(intent);
    }

    public void Alumni(View v){
        Intent intent = new Intent(getApplicationContext(),AlumniMainActivity.class);
        startActivity(intent);
    }

    public void Fees (View v){
        Intent intent = new Intent(getApplicationContext(), FeesMainActivity.class);
        startActivity(intent);
    }
}
