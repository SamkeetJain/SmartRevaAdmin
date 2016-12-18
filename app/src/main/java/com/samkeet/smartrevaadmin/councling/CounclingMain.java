package com.samkeet.smartrevaadmin.councling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class CounclingMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_councling_main);
    }

    public void BackButton (View v){finish();}

    public void AddWallPost (View v){
        Intent intent =new Intent(getApplicationContext(),AddWallPostActivity.class);
        startActivity(intent);
    }

    public void ViewWallPost (View v){
        Intent intent = new Intent(getApplicationContext(),ViewWallPostsActivity.class);
        startActivity(intent);
    }

    public void ManageReservations (View v){
        Intent intent = new Intent(getApplicationContext(),ManageReservationsActivity.class);
        startActivity(intent);
    }
    public void ViewReservations (View v){
        Intent intent = new Intent(getApplicationContext(),ViewReservationActivity.class);
        startActivity(intent);
    }
    public void MyAppointments (View v){
        Intent intent = new Intent(getApplicationContext(),MyAppointmentsActivity.class);
        startActivity(intent);
    }
    public void Remarks (View v){
        Intent intent = new Intent(getApplicationContext(),RemarksActivity.class);
        startActivity(intent);
    }
    public void NotificationTab (View v){
        Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
        startActivity(intent);
    }
}
