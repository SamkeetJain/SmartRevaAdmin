package com.samkeet.smartrevaadmin.councling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.samkeet.smartrevaadmin.R;

public class ViewReservationActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public String[] mSrn={"dsfasdf","sdafasdf","asdfasdfas"};
    public String[] mName={"asdfasdfasdf","asdfasdfsd","dfhgdfh"};
    public String[] mDate={"dfghfdgdfg","dfghfdgdfg","dfghdfgdfg"};
    public  String[] mTime={"asdfasdf","sdgdfgdfg","fsgdsfgsdf"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservation);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ViewReservationAdapter(mSrn,mName,mDate,mTime);
        mRecyclerView.setAdapter(mAdapter);

    }
    public void BackButton (View v){finish();}


}
