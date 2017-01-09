package com.samkeet.smartrevaadmin.councling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.samkeet.smartrevaadmin.R;
import com.samkeet.smartrevaadmin.alumni.MemberRequestAdapter;

public class MyAppointmentsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public String[] mTitles={"Title 1","Title 2","Title 3"};
    public String[] mDesc={"Desc 1","Desc 2","Desc 3"};
    public String[] mDateText={"Date 1","Date 2","Date 3"};
    public String[] mTime={"Time 1","Time 2","Time 3"};
    public String[] mName={"Name 1","Name 2","Name 3"};
    public String[] mSrn={"Srn 1","Srn 2","Srn 3"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAppointmentAdapter(mTitles,mDesc,mDateText,mName,mSrn,mTime);
        mRecyclerView.setAdapter(mAdapter);

        final GestureDetector mGestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent motionEvent) {

                View child = mRecyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int temp = mRecyclerView.getChildPosition(child);
                    Intent intent = new Intent(getApplicationContext(), MyAppointmentManager.class);
                    intent.putExtra("DATA", "");
                    startActivity(intent);

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }


    public void BackButton (View v){finish();}



}
