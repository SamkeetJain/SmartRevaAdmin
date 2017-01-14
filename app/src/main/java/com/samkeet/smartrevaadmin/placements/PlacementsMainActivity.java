package com.samkeet.smartrevaadmin.placements;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class PlacementsMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placements_main);
    }

    public void BackButton (View v){finish();}

    public void AddEventP (View v){
        Intent intent =new Intent(getApplicationContext(), PlacementAddEventActivity.class);
        startActivity(intent);
    }
    public void ManageEvents (View v){
        Intent intent = new Intent(getApplicationContext(),PlacementEventActivity.class);
        startActivity(intent);
    }
    public void ViewAcademicDetails (View v){
        Intent intent = new Intent(getApplicationContext(),PlacementViewAcademicDetailsActivity.class);
        startActivity(intent);
    }
    public void SendNotification (View v){
        Intent intent = new Intent(getApplicationContext(),PlacementSendNotificationActivity.class);
        startActivity(intent);
    }
}
