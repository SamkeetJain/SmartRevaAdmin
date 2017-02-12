package com.samkeet.smartrevaadmin.Placement2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.samkeet.smartrevaadmin.Constants;
import com.samkeet.smartrevaadmin.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class Placement2PostInviteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public String comp_name, ddate, course, branch, min10, min12, minug, minpg, maxhb, maxab, details;
    public EditText mComp_name, mMin10, mMin12, mMinug, mMinpg, mMaxhb, mMaxab, mDetails;
    public TextView mDdate, mBranch;
    public Button send, branchButton;

    public SpotsDialog pd;
    public Context progressDialogContext;
    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";
    public ArrayList<String> cList = new ArrayList<String>();
    public ArrayList<String> bList = new ArrayList<String>();
    public ArrayList<String> list = new ArrayList<String>();
    public Spinner mCourse;

    public String[] courseList = {"course"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement2_post_invite);

        progressDialogContext = this;

        mCourse = (Spinner) findViewById(R.id.course);
        mComp_name = (EditText) findViewById(R.id.comp_name);
        mDdate = (TextView) findViewById(R.id.ddate);
        mMin10 = (EditText) findViewById(R.id.min10th);
        mMin12 = (EditText) findViewById(R.id.min12);
        mMinug = (EditText) findViewById(R.id.minug);
        mMinpg = (EditText) findViewById(R.id.minpg);
        mMaxhb = (EditText) findViewById(R.id.maxhb);
        mMaxab = (EditText) findViewById(R.id.maxab);
        mDetails = (EditText) findViewById(R.id.detailsview);
        mBranch = (TextView) findViewById(R.id.sbranch);

        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInvites();
            }
        });

        mDdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        Placement2PostInviteActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        branchButton = (Button) findViewById(R.id.selectBranch);
        branchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Placement2SelectBranchActivity.class);
                intent.putStringArrayListExtra("DATA", list);
                startActivityForResult(intent, 101);
            }
        });

        GetCourseDeptDetails getCourseDeptDetails = new GetCourseDeptDetails();
        getCourseDeptDetails.execute();
    }

    public void BackButton(View v) {
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String fullDate;
        String dayofmonth = "" + dayOfMonth;
        String monthofyear = "" + monthOfYear;
        if (dayOfMonth < 10)
            dayofmonth = "0" + dayOfMonth;
        if (++monthOfYear < 10) {
            monthofyear = "0" + monthOfYear;
        }
        fullDate = year + "-" + (monthofyear) + "-" + dayofmonth;
        mDdate.setText(fullDate);
    }

    public void sendInvites() {
        comp_name = mComp_name.getText().toString().trim();
        ddate = mDdate.getText().toString().trim();
        min10 = mMin10.getText().toString().trim();
        min12 = mMin12.getText().toString().trim();
        minug = mMinug.getText().toString().trim();
        minpg = mMinpg.getText().toString().trim();
        maxhb = mMaxhb.getText().toString().trim();
        maxab = mMaxab.getText().toString().trim();
        details = mDetails.getText().toString().trim();
        branch = mBranch.getText().toString().trim();

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
                Log.d("data", "data ready to send");
                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("course", course)
                        .appendQueryParameter("branch", branch)
                        .appendQueryParameter("tenth", min10)
                        .appendQueryParameter("twelth", min12)
                        .appendQueryParameter("minug", minug)
                        .appendQueryParameter("minpg", minpg)
                        .appendQueryParameter("maxhb", maxhb)
                        .appendQueryParameter("maxab", maxab)
                        .appendQueryParameter("requestType", "SENDINVITE")
                        .appendQueryParameter("comp_name", comp_name)
                        .appendQueryParameter("details", details)
                        .appendQueryParameter("ddate", ddate);
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
                    JSONObject jsonObject = new JSONObject(jsonResults.toString());
                    String status = jsonObject.getString("status");
                    if (status.equals("failed")) {
                        authenticationError = true;
                        errorMessage = status;
                    } else if (status.equals("success")) {
                        authenticationError = false;
                        errorMessage = status;
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
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                finish();
            }

        }
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
                course = courseList[position];
                initDeptList();
                mBranch.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                course = courseList[0];
            }
        });
    }

    public void initDeptList() {

        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < cList.size(); i++) {
            if (cList.get(i).equals(course)) {
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
                mBranch.setText(result);
            }
        }
    }

}
