package com.samkeet.smartrevaadmin.Alumni;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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

public class AlumniNotificationActivity extends AppCompatActivity {

    public EditText mTitle, mMessage;
    public String title, message;

    public SpotsDialog pd;
    public Context progressDialogContext;

    public boolean authenticationError = true;
    public String errorMessage = "Data Corupted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_notification);
        progressDialogContext=this;

        mTitle = (EditText) findViewById(R.id.title);
        mMessage = (EditText) findViewById(R.id.message);


    }

    public void BackButton(View v) {
        finish();
    }

    public void Send(View v) {

        title = mTitle.getText().toString().trim();
        message = mMessage.getText().toString().trim();

        validate();


    }

    public void validate(){
        if (!validTitle()){
            return ;
        }
        if (!validateMessage()){
            return ;
        }
        Sendnotification sendnotification = new Sendnotification();
        sendnotification.execute();

        SendPush sendPush = new SendPush();
        sendPush.execute();

    }
    private boolean validTitle(){
        if (title.length()>100){
            Toast.makeText(getApplicationContext(),"Title cannot be more than 100 charecters",Toast.LENGTH_SHORT).show();
            requestFocus(mTitle);
            return false;
        }if (title.isEmpty()){
            Toast.makeText(getApplicationContext(),"Invalid Title",Toast.LENGTH_SHORT).show();
            requestFocus(mTitle);
            return false;
        }if (Constants.Methods.checkForSpecial(title)){
            Toast.makeText(getApplicationContext(),"Remove special charecters from the title field",Toast.LENGTH_SHORT).show();
            requestFocus(mTitle);
            return false;
        }
        return true;
    }

    private boolean validateMessage(){
        if (message.length()>1000){
            Toast.makeText(getApplicationContext(),"Message cannot be more than 1000 charecters",Toast.LENGTH_SHORT).show();
            requestFocus(mMessage);
            return false;
        }if (message.isEmpty()){
            Toast.makeText(getApplicationContext(),"Invalid Message",Toast.LENGTH_SHORT).show();
            requestFocus(mMessage);
            return false;
        }if (Constants.Methods.checkForSpecial(message)){
            Toast.makeText(getApplicationContext(),"Remove special charecters from the Message field",Toast.LENGTH_SHORT).show();
            requestFocus(mMessage);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public class Sendnotification extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {

                URL url = new URL(Constants.URLs.ALUMNI_BASE + Constants.URLs.ALUMNI_NOTIFICATION);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("requestType", "put")
                        .appendQueryParameter("title", title)
                        .appendQueryParameter("message", message);
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
                    JSONObject jsonObject = new JSONObject(jsonResults.toString());
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        authenticationError = false;
                        errorMessage = status;
                    } else {
                        authenticationError = true;
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
                mTitle.setText("");
                mMessage.setText("");
            }
        }

    }

    public class SendPush extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
        }

        protected Integer doInBackground(Void... params) {
            try {

                URL url = new URL(Constants.URLs.ALUMNI_BASE + Constants.URLs.ALUMNI_PUSH);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Uri.Builder _data = new Uri.Builder().appendQueryParameter("message", message);
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
                return 1;

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {

        }

    }

}
