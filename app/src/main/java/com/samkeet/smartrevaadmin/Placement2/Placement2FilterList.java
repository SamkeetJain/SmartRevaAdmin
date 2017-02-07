package com.samkeet.smartrevaadmin.Placement2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.samkeet.smartrevaadmin.Alumni.AlumniEventListManager;
import com.samkeet.smartrevaadmin.Constants;
import com.samkeet.smartrevaadmin.ExcelAPI;
import com.samkeet.smartrevaadmin.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import jxl.write.WriteException;

public class Placement2FilterList extends AppCompatActivity {

    public SpotsDialog pd;
    public Context progressDialogContext;
    public boolean authenticationError;
    public String errorMessage;
    public static final int STORAGE_PERMISSION = 101;

    public ArrayList<String> cList = new ArrayList<String>();
    public ArrayList<String> bList = new ArrayList<String>();

    public String[] courseList = {"course"};
    public ArrayList<String> list = new ArrayList<String>();
    public Spinner mCourse;
    public String selectedCourse;
    public Button mSelectBranch;
    public TextView sbranch;
    public EditText min10, min12, minug, minpg, maxhb, maxab;
    public String smin10, smin12, sminug, sminpg, smaxhb, smaxab, branch;
    public JSONArray jsonArray;
    public String tilesdata;
    public String[] tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement2_filter_list);
        progressDialogContext = this;

        mCourse = (Spinner) findViewById(R.id.course);
        mSelectBranch = (Button) findViewById(R.id.selectBranch);
        sbranch = (TextView) findViewById(R.id.sbranch);
        mSelectBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Placement2SelectBranchActivity.class);
                intent.putStringArrayListExtra("DATA", list);
                startActivityForResult(intent, 101);
            }
        });

        min10 = (EditText) findViewById(R.id.min10th);
        min12 = (EditText) findViewById(R.id.min12);
        minug = (EditText) findViewById(R.id.minug);
        minpg = (EditText) findViewById(R.id.minpg);
        maxhb = (EditText) findViewById(R.id.maxhb);
        maxab = (EditText) findViewById(R.id.maxab);


        GetCourseDeptDetails getCourseDeptDetails = new GetCourseDeptDetails();
        getCourseDeptDetails.execute();
    }

    private class GetCourseDeptDetails extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {
                java.net.URL url = new URL(Constants.URLs.PLACEMENT_BASE + Constants.URLs.PLACEMENT_COURSE_DEPT);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(_data.build().getEncodedQuery());
                writer.flush();
                writer.close();

                InputStreamReader in = new InputStreamReader(connection.getInputStream());

                StringBuilder jsonResults = new StringBuilder();
                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                connection.disconnect();

                authenticationError = jsonResults.toString().contains("Authentication Error");

                if (authenticationError) {
                    errorMessage = jsonResults.toString();
                } else {
                    JSONArray jsonArray = new JSONArray(jsonResults.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String coursetemp = jsonObject.getString("course");
                        String depttemp = jsonObject.getString("dept");
                        cList.add(coursetemp);
                        bList.add(depttemp);
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
                initCourseList();
            }

        }
    }

    public void initCourseList() {

        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < cList.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < temp.size(); j++) {
                if (temp.get(j).equals(cList.get(i))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                temp.add(cList.get(i));
            }
        }
        courseList = null;
        courseList = temp.toArray(new String[temp.size()]);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, courseList);
        mCourse.setAdapter(adapter1);
        mCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCourse = courseList[position];
                initDeptList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedCourse = courseList[0];
            }
        });
    }

    public void initDeptList() {

        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < cList.size(); i++) {
            if (cList.get(i).equals(selectedCourse)) {
                temp.add(bList.get(i));
            }
        }

        list = temp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("DATA");
                sbranch.setText(result);
            }
        }
    }

    public void Send(View v) {
        smin10 = min10.getText().toString().trim();
        smin12 = min12.getText().toString().trim();
        sminug = minug.getText().toString().trim();
        sminpg = minpg.getText().toString().trim();
        smaxhb = maxhb.getText().toString().trim();
        smaxab = maxab.getText().toString().trim();
        branch = sbranch.getText().toString().trim();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            return;
        }
        GetList getList = new GetList();
        getList.execute();

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
                java.net.URL url = new URL(Constants.URLs.PLACEMENT_BASE + Constants.URLs.PLACEMENT_GETFILTERLIST);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("course", selectedCourse)
                        .appendQueryParameter("branch", branch)
                        .appendQueryParameter("tenth", smin10)
                        .appendQueryParameter("twelth", smin12)
                        .appendQueryParameter("minug", sminug)
                        .appendQueryParameter("minpg", sminpg)
                        .appendQueryParameter("maxhb", smaxhb)
                        .appendQueryParameter("maxab", smaxab)
                        .appendQueryParameter("requestType", "GETLIST");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(_data.build().getEncodedQuery());
                writer.flush();
                writer.close();

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
                GetTiles getTiles = new GetTiles();
                getTiles.execute();
            }

        }
    }

    private class GetTiles extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {
                java.net.URL url = new URL(Constants.URLs.PLACEMENT_BASE + Constants.URLs.PLACEMENT_EXCELDATA);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Uri.Builder _data = new Uri.Builder().appendQueryParameter("course", selectedCourse);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(_data.build().getEncodedQuery());
                writer.flush();
                writer.close();

                InputStreamReader in = new InputStreamReader(connection.getInputStream());

                StringBuilder jsonResults = new StringBuilder();
                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                connection.disconnect();

                tilesdata = jsonResults.toString().trim();
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
                tiles = tilesdata.split("\\|");

                ExcelAPI excelAPI = new ExcelAPI("LIST", jsonArray.toString(), tiles);
                try {
                    excelAPI.write();
                    Toast.makeText(getApplicationContext(), "Location: Download/List", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

            }

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


}
