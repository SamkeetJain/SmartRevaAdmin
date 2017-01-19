package com.samkeet.smartrevaadmin.Alumni;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.samkeet.smartrevaadmin.Constants;
import com.samkeet.smartrevaadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dmax.dialog.SpotsDialog;

public class AlumniEventListManager extends AppCompatActivity {

    public TextView mName, mType, mDesc, mDate;
    public String name,type,desc,ddate;

    public String data;
    public JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_event_list_manager);

        data = getIntent().getStringExtra("DATA");

        try {
            object=new JSONObject(data);
            name = object.getString("name");
            type = object.getString("type");
            ddate = object.getString("edate");
            desc = object.getString("description");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mName = (TextView) findViewById(R.id.name);
        mType = (TextView) findViewById(R.id.type);
        mDate = (TextView) findViewById(R.id.date);
        mDesc = (TextView) findViewById(R.id.desc);

        mName.setText(name);
        mType.setText(type);
        mDate.setText(ddate);
        mDesc.setText(desc);

    }

    public void BackButton (View v) {finish();}

}
