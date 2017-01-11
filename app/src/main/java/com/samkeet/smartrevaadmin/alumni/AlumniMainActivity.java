package com.samkeet.smartrevaadmin.alumni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class AlumniMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_main);
    }

    public void BackButton (View v){finish();}

    public void MemberRequest (View v){
        Intent intent = new Intent(getApplicationContext(),MemberRequestActivity.class);
        startActivity(intent);
    }
    public void AddEvents (View v){
        Intent intent = new Intent(getApplicationContext(),AddEventActivity.class);
        startActivity(intent);
    }
    public void EventManager (View v)
    {
        Intent intent = new Intent(getApplicationContext(),EventListActivity.class);
        startActivity(intent);
    }

    public void Blog (View v){
        Intent intent= new Intent(getApplicationContext(),BlogActivity.class);
        startActivity(intent);
    }
    public void JobReferals (View v){
        Intent intent=new Intent(getApplicationContext(),JobReferalActivity.class);
        startActivity(intent);
    }
    public void ViewProfile(View v){
        Intent intent=new Intent(getApplicationContext(),ViewProfileActivity.class);
        startActivity(intent);
    }
}
