package com.samkeet.smartrevaadmin.Counselling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class CounclingMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_councling_main);
    }

    public void BackButton (View v){finish();}

    public void AddWallPost (View v){
        Intent intent =new Intent(getApplicationContext(),CounclingAddWallPostActivity.class);
        startActivity(intent);
    }

    public void ViewWallPost (View v){
        Intent intent = new Intent(getApplicationContext(),CounclingViewWallPostsActivity.class);
        startActivity(intent);
    }

    public void ManageReservations (View v){
        Intent intent = new Intent(getApplicationContext(),CounclingManageReservationsActivity.class);
        startActivity(intent);
    }
    public void ViewReservations (View v){
        Intent intent = new Intent(getApplicationContext(),CounclingViewReservationActivity.class);
        startActivity(intent);
    }
    public void MyAppointments (View v){
        Intent intent = new Intent(getApplicationContext(),CounclingMyAppointmentsActivity.class);
        startActivity(intent);
    }
    public void Remarks (View v){
        Intent intent = new Intent(getApplicationContext(),CounclingRemarksActivity.class);
        startActivity(intent);
    }
    public void NotificationTab (View v){
        Intent intent = new Intent(getApplicationContext(),CounclingNotificationActivity.class);
        startActivity(intent);
    }
}
