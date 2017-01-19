package com.samkeet.smartrevaadmin.Fees;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class ViewFeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fee);
    }
    public void BackButton (View v){finish();}
}
