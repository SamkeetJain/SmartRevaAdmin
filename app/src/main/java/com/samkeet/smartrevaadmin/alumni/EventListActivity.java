package com.samkeet.smartrevaadmin.alumni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.samkeet.smartrevaadmin.R;

import static com.samkeet.smartrevaadmin.R.id.desc;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public String[] name= {"djsdj","kadshjfsl","dflsk"};
    public String[] type= {"asdf","sadfasfa","sadfasdf"};
    public String[] desc= {"adfsadf","dadfasdf","sdfasf"};
    public String[] date= {"asdfa","asfasdf","asdfasf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new EventListAdapter(name,type,date,desc);
        mRecyclerView.setAdapter(mAdapter);


    }
}
