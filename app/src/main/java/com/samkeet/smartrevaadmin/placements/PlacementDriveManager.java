package com.samkeet.smartrevaadmin.placements;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samkeet.smartrevaadmin.Constants;
import com.samkeet.smartrevaadmin.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class PlacementDriveManager extends AppCompatActivity {

    public String data;
    public TextView mTitle,mDetails;
    public ImageView mImage;
    public Button mRegister;
    public JSONObject object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_event_manager);

        mImage= (ImageView) findViewById(R.id.logo);
        mTitle = (TextView) findViewById(R.id.title);
        mDetails = (TextView) findViewById(R.id.details);
        mRegister = (Button) findViewById(R.id.register);

        data=getIntent().getStringExtra("DATA");
        try {
            object=new JSONObject(data);

            mTitle.setText(object.getString("comp_name"));
            mDetails.setText(object.getString("details"));
            Picasso.with(getApplicationContext()).load(Constants.URLs.BASE+"uploads/"+object.getString("logoFileName")).into(mImage);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void BackButton (View v){finish();}
}
