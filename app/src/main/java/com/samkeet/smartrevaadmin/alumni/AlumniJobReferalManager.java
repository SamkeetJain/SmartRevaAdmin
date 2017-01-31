package com.samkeet.smartrevaadmin.Alumni;

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dmax.dialog.SpotsDialog;

public class AlumniJobReferalManager extends AppCompatActivity {

    public TextView mName, mMobile, mComp, mType, mRole, mDesc, mEmail;
    public String name, mobile, comp, type, role, desc, email,ID;

    public JSONObject object;
    public String data;

    public SpotsDialog pd;
    public Context progressDialogContext;
    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";

    public String requestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_job_referal_manager);
        progressDialogContext=this;

        mName = (TextView) findViewById(R.id.name);
        mMobile = (TextView) findViewById(R.id.mobileno);
        mComp = (TextView) findViewById(R.id.comp);
        mType = (TextView) findViewById(R.id.jobtype);
        mRole = (TextView) findViewById(R.id.jobrole);
        mDesc = (TextView) findViewById(R.id.desc);
        mEmail = (TextView) findViewById(R.id.email);

        data = getIntent().getStringExtra("DATA");

        try {
            object = new JSONObject(data);
            name = object.getString("name");
            mobile = object.getString("mobile_no");
            email = object.getString("email");
            comp = object.getString("company_name");
            type = object.getString("job_type");
            role = object.getString("job_role");
            desc = object.getString("description");
            ID = object.getString("Id");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mName.setText(name);
        mMobile.setText(mobile);
        mEmail.setText(email);
        mComp.setText(comp);
        mType.setText(type);
        mRole.setText(role);
        mDesc.setText(desc);
    }

    public void BackButton(View v) {
        finish();
    }

    public void AcceptButton(View v){
        authenticationError=true;
        errorMessage = "Data Corrupted";
        requestType = "approve";
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.execute();

    }
    public void RejectButton(View v){
        authenticationError=true;
        errorMessage = "Data Corrupted";
        requestType = "reject";
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.execute();

    }

    private class MemberRequest extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {
                URL url = new URL(Constants.URLs.ALUMNI_BASE + Constants.URLs.ALUMNI_JOB_REFER);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Log.d("POST", "DATA ready to sent");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("requestType",requestType).appendQueryParameter("ID",ID);
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
                    if(status.equals("failed")){
                        authenticationError=true;
                        errorMessage=status;
                    }else if(status.equals("success")){
                        authenticationError=false;
                        errorMessage=status;
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

}
