package com.samkeet.smartrevaadmin.councling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.samkeet.smartrevaadmin.R;
import com.samkeet.smartrevaadmin.alumni.MemberRequestAdapter;

public class MyAppointmentsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public String[] mTitles={"dsfasdf","sdafasdf","asdfasdfas"};
    public String[] mDesc={"asdfasdfasdf","asdfasdfsd","dfhgdfh"};
    public String[] mDateText={"dfghfdgdfg","dfghfdgdfg","dfghdfgdfg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAppointmentAdapter(mTitles,mDateText,mDateText);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void BackButton (View v){finish();}



}
