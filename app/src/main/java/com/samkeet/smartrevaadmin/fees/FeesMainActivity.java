package com.samkeet.smartrevaadmin.Fees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class FeesMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_main);
    }

    public void BackButton (View v){finish();}

    public void ViewFee (View v){
        Intent intent = new Intent(getApplicationContext(),ViewFeeActivity.class);
        startActivity(intent);
    }

    public  void AddFee (View v){
        Intent intent = new Intent(getApplicationContext(),AddFeeDetailsActivity.class);
        startActivity(intent);
    }
}
