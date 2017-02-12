package com.samkeet.smartrevaadmin.Placement2;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.samkeet.smartrevaadmin.Constants;
import com.samkeet.smartrevaadmin.ExcelAPI;
import com.samkeet.smartrevaadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dmax.dialog.SpotsDialog;
import jxl.write.WriteException;

public class Placement2ManageDriveManager extends AppCompatActivity {

    public static final int STORAGE_PERMISSION = 101;
    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";
    public SpotsDialog pd;
    public Context progressDialogContext;

    public TextView mName, mDate, mCourse, mBranch, mDetails, mMin10, mMin12, mMinug, mMinpg, mMaxhb, mMaxab;
    public String id, name, ddate, course, branch, details, min10, min12, minug, minpg, maxhb, maxab;
    public Button delete, getlist;
    public JSONObject jsonObject;

    public JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement2_manage_drive_manager);
        progressDialogContext = this;

        mName = (TextView) findViewById(R.id.name);
        mDate = (TextView) findViewById(R.id.ddate);
        mCourse = (TextView) findViewById(R.id.course);
        mBranch = (TextView) findViewById(R.id.branch);
        mDetails = (TextView) findViewById(R.id.details);
        mMin10 = (TextView) findViewById(R.id.min10);
        mMin12 = (TextView) findViewById(R.id.min12);
        mMinug = (TextView) findViewById(R.id.minug);
        mMinpg = (TextView) findViewById(R.id.minpg);
        mMaxhb = (TextView) findViewById(R.id.maxhb);
        mMaxab = (TextView) findViewById(R.id.maxab);

        String temp = getIntent().getStringExtra("DATA");
        try {
            jsonObject = new JSONObject(temp);
            id = jsonObject.getString("ID");
            name = jsonObject.getString("comp_name");
            ddate = jsonObject.getString("ddate");
            course = jsonObject.getString("course");
            branch = jsonObject.getString("branch");
            details = jsonObject.getString("details");
            min10 = jsonObject.getString("tenth");
            min12 = jsonObject.getString("twelth");
            minug = jsonObject.getString("minug");
            minpg = jsonObject.getString("minpg");
            maxhb = jsonObject.getString("maxhb");
            maxab = jsonObject.getString("maxab");

            ddate = "On " + ddate;
            course = "Course - " + course.toUpperCase();
            branch = "Branch - " + branch.toUpperCase();
            min10 = "Min 10 - " + min10;
            min12 = "Min 12 - " + min12;
            minug = "Min UG - " + minug;
            minpg = "Min PG - " + minpg;
            maxhb = "Max HB - " + maxhb;
            maxab = "Max AB - " + maxab;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mName.setText(name);
        mDate.setText(ddate);
        mCourse.setText(course);
        mBranch.setText(branch);
        mDetails.setText(details);
        mMin10.setText(min10);
        mMin12.setText(min12);
        mMinug.setText(minug);
        mMinpg.setText(minpg);
        mMaxhb.setText(maxhb);
        mMaxab.setText(maxab);

        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteDrive deleteDrive = new DeleteDrive();
                deleteDrive.execute();
            }
        });
        getlist = (Button) findViewById(R.id.list);
        getlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getmemberslist();
            }
        });

    }

    public void getmemberslist() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            return;
        }
        if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {

            GetList getList = new GetList();
            getList.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                GetList getList = new GetList();
                getList.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Oops you have denied the permission. Please give storage permission to proceed further", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BackButton(View v) {
        finish();
    }

    private class DeleteDrive extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {
                URL url = new URL(Constants.URLs.PLACEMENT_BASE + Constants.URLs.PLACEMENT_MANAGE_DRIVE);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Log.d("POST", "DATA ready to sent");


                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN()).
                        appendQueryParameter("placement_id", id)
                        .appendQueryParameter("requestType", "delete");
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
                    if (status.equals("failed")) {
                        authenticationError = true;
                        errorMessage = status;
                    } else if (status.equals("success")) {
                        authenticationError = false;
                        errorMessage = status;
                    }
                    authenticationError = false;
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
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    private class GetList extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {
                URL url = new URL(Constants.URLs.PLACEMENT_BASE + Constants.URLs.PLACEMENT_MANAGE_DRIVE);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Log.d("POST", "DATA ready to sent");


                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN()).
                        appendQueryParameter("placement_id", id)
                        .appendQueryParameter("requestType", "getlist");
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
                    jsonArray = new JSONArray(jsonResults.toString());
                    authenticationError = false;
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
                String[] title = {"UserID", "name", "email", "PhoneNo"};
                ExcelAPI excelAPI = new ExcelAPI(name + " members", jsonArray.toString(), title);
                try {
                    excelAPI.write();
                    Toast.makeText(getApplicationContext(), "Location: Download/ " + name + "members", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
