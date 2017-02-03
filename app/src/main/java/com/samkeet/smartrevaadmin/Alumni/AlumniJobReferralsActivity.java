package com.samkeet.smartrevaadmin.Alumni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class AlumniJobReferralsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_job_referrals);
    }

    public void JobReferalsAcc(View v) {
        Intent intent = new Intent(getApplicationContext(), AlumniJobReferalActivity.class);
        intent.putExtra("TYPE", "getapprove");
        startActivity(intent);
    }

    public void JobReferalsRej(View v) {
        Intent intent = new Intent(getApplicationContext(), AlumniJobReferalActivity.class);
        intent.putExtra("TYPE", "getreject");
        startActivity(intent);
    }

    public void JobReferalsPend(View v) {
        Intent intent = new Intent(getApplicationContext(), AlumniJobReferalActivity.class);
        intent.putExtra("TYPE", "getpending");
        startActivity(intent);
    }

    public void BackButton(View v) {
        finish();
    }
}
