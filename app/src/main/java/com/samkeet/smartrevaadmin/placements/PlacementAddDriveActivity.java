package com.samkeet.smartrevaadmin.Placements;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.samkeet.smartrevaadmin.Constants;
import com.samkeet.smartrevaadmin.R;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dmax.dialog.SpotsDialog;

public class PlacementAddDriveActivity extends AppCompatActivity {

    public SpotsDialog pd;
    public Context progressDialogContext;
    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";

    public Spinner Backlog_History;
    public Spinner Current_Backlog;
    public String backlogHistory[] = {"YES_BACKLOG_HISTORY", "NO_BACKLOG_HISTORY"};
    public String currentBacklog[] = {"YES_CURRENT_BACKLOG", "NO_CURRENT_BACKLOG"};
    public String options[] = {"YES", "NO"};
    public String bh;
    public String cb;

    public TextView mComp,mRole,mDdate,mDept,mEl_tenth,mEl_twelth,mEl_ug,mDesc;
    public String comp,role,ddate,dept,el_tenth,el_twelth,el_ug,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_add_event);
        progressDialogContext=this;

        Backlog_History = (Spinner) findViewById(R.id.backloghistory);
        Current_Backlog = (Spinner) findViewById(R.id.currentbacklog);

        mComp = (TextView) findViewById(R.id.comp);
        mRole = (TextView) findViewById(R.id.role);
        mDdate = (TextView) findViewById(R.id.ddate);
        mDept = (TextView) findViewById(R.id.dept);
        mEl_tenth = (TextView) findViewById(R.id.tenth);
        mEl_twelth = (TextView) findViewById(R.id.twelth);
        mEl_ug = (TextView) findViewById(R.id.ug);
        mDesc = (TextView) findViewById(R.id.desc);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options);
        Backlog_History.setAdapter(adapter1);
        Backlog_History.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                bh = backlogHistory[position];
                if (position == 1) {
                    Current_Backlog.setVisibility(View.INVISIBLE);
                    cb = currentBacklog[1];
                } else if (position == 0) {
                    Current_Backlog.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options);
        Current_Backlog.setAdapter(adapter2);
        Current_Backlog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                cb = currentBacklog[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void BackButton(View v) {
        finish();
    }

    public void Send(View v){
        comp = mComp.getText().toString();
        role = mRole.getText().toString();
        ddate = mDdate.getText().toString();
        dept = mDept.getText().toString();
        el_tenth = mEl_tenth.getText().toString();
        el_twelth = mEl_twelth.getText().toString();
        el_ug = mEl_ug.getText().toString();
        desc = mDesc.getText().toString();

        AddDrive addDrive = new AddDrive();
        addDrive.execute();

    }

    public class AddDrive extends AsyncTask<Void, Void, Integer> {


        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext,R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {


                URL url = new URL(Constants.URLs.BASE + Constants.URLs.PLACEMENT_DRIVE);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Log.d("POST", "DATA ready to sent");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("type", "put")
                        .appendQueryParameter("comp",comp)
                        .appendQueryParameter("details",desc)
                        .appendQueryParameter("ddate",ddate)
                        .appendQueryParameter("role",role)
                        .appendQueryParameter("dept",dept)
                        .appendQueryParameter("logo","NA")
                        .appendQueryParameter("tenth",el_tenth)
                        .appendQueryParameter("twelth",el_twelth)
                        .appendQueryParameter("ug",el_ug)
                        .appendQueryParameter("cb",cb)
                        .appendQueryParameter("nb",bh);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(_data.build().getEncodedQuery());
                writer.flush();
                writer.close();
                Log.d("POST", "DATA SENT");

                InputStreamReader in = new InputStreamReader(connection.getInputStream());

                StringBuilder jsonResults = new StringBuilder();
                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                connection.disconnect();
                Log.d("return from server", jsonResults.toString());

                authenticationError = jsonResults.toString().contains("Authentication Error");

                if (authenticationError) {
                    errorMessage = jsonResults.toString();
                } else {
                    JSONObject jsonObject = new JSONObject(jsonResults.toString());
                    String status = jsonObject.getString("status");
                    if (status.equals("Failed")) {
                        authenticationError = true;
                        errorMessage = status;
                    } else if (status.equals("success")) {
                        authenticationError = false;
                    }
                }
                return 1;

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {
            if (pd != null) {
                pd.dismiss();
            }
            if (authenticationError) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
