package com.samkeet.smartrevaadmin.Placement2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class Placement2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement2_main);
    }

    public void GetFilterList(View v){
        Intent intent = new Intent(getApplicationContext(),Placement2FilterList.class);
        startActivity(intent);
    }

    public void PostNewDrive(View v){
        Intent intent = new Intent(getApplicationContext(),Placement2PostInviteActivity.class);
        startActivity(intent);
    }

    public void ManageDrives(View v){

    }

    public void BackButton(View v){
        finish();
    }
}
