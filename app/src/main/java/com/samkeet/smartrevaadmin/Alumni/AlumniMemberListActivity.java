package com.samkeet.smartrevaadmin.Alumni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

import java.util.concurrent.RejectedExecutionException;

public class AlumniMemberListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_member_list);
    }
    public void BackButton (View v){finish();}

    public void MemberPend (View v){
        Intent intent=new Intent(getApplicationContext(),AlumniMemberRequestActivity.class);
        intent.putExtra("TYPE","getpending");

        startActivity(intent);
    }
    public void MemberAcc (View v){
        Intent intent=new Intent(getApplicationContext(),AlumniMemberRequestActivity.class);
        intent.putExtra("TYPE","getapproved");

        startActivity(intent);
    }
    public void MemberRej (View v){
        Intent intent=new Intent(getApplicationContext(),AlumniMemberRequestActivity.class);
        intent.putExtra("TYPE","getrejected");
        startActivity(intent);
    }
}
