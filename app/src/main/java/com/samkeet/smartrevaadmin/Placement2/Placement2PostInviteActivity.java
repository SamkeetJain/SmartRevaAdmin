package com.samkeet.smartrevaadmin.Placement2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.samkeet.smartrevaadmin.R;

public class Placement2PostInviteActivity extends AppCompatActivity {

    public String comp_name, ddate, course, branch, min10, min12, minug, minpg, maxhb, maxab, details;
    public EditText mComp_name, mDdate, mMin10, mMin12, mMinug, mMinpg, mMaxhb, mMaxab, mDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement2_post_invite);

        mComp_name = (EditText) findViewById(R.id.comp_name);
        mDdate = (EditText) findViewById(R.id.ddate);
        mMin10 = (EditText) findViewById(R.id.min10th);
        mMin12 = (EditText) findViewById(R.id.min12);
        mMinug = (EditText) findViewById(R.id.minug);
        mMinpg = (EditText) findViewById(R.id.minpg);
        mMaxhb = (EditText) findViewById(R.id.maxhb);
        mMaxab = (EditText) findViewById(R.id.maxab);
        mDetails = (EditText) findViewById(R.id.detailsview);
    }

    public void BackButton(View v) {
        finish();
    }

}
