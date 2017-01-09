package com.samkeet.smartrevaadmin.placements;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;
import com.samkeet.smartrevaadmin.alumni.*;
import com.samkeet.smartrevaadmin.alumni.AddEventActivity;
import com.samkeet.smartrevaadmin.fees.ViewFeeActivity;

public class PlacementsMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placements_main);
    }

    public void BackButton (View v){finish();}

    public void AddEvents (View v){
        Intent intent =new Intent(getApplicationContext(), AddEventActivity.class);
        startActivity(intent);
    }
    public void ManageEvents (View v){
        Intent intent = new Intent(getApplicationContext(),ManageEventActivity.class);
        startActivity(intent);
    }
    public void ViewAcademicDetails (View v){
        Intent intent = new Intent(getApplicationContext(),ViewAcademicDetailsActivity.class);
        startActivity(intent);
    }
    public void SendNotification (View v){
        Intent intent = new Intent(getApplicationContext(),SendNotificationActivity.class);
        startActivity(intent);
    }
}
