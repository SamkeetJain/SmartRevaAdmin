package com.samkeet.smartrevaadmin.Placements;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class PlacementNewDriveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_placement_add_drive);
    }
    public void BackButton (View v){finish();}

    public void BranchButton(View v){
        Intent intent =new Intent(getApplicationContext(),PlacementNewDriveActivity.class);
        startActivity(intent);
    }

}
