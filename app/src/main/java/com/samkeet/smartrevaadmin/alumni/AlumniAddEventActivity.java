package com.samkeet.smartrevaadmin.Alumni;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.samkeet.smartrevaadmin.Constants;
import com.samkeet.smartrevaadmin.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class AlumniAddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public SpotsDialog pd;
    public Context progressDialogContext;
    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";

    public EditText mTitle, mType, mDesc, mDate;
    public String title, type, desc, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_add_event);

        progressDialogContext = this;
        mTitle = (EditText) findViewById(R.id.title);
        mType = (EditText) findViewById(R.id.type);
        mDesc = (EditText) findViewById(R.id.desc);
        mDate = (EditText) findViewById(R.id.date);


    }

    public void BackButton(View v) {
        finish();
    }

    public void Send(View v) {
        title = mTitle.getText().toString().trim();
        type = mType.getText().toString().trim();
        desc = mDesc.getText().toString().trim();
        date = mDate.getText().toString().trim();

        validate();


    }

    public void validate() {
        if (!validTitle()) {
            return;
        }
        if (!validType()) {
            return;
        }
        if (!validDesc()) {
            return;
        }
        if(date.isEmpty()){
            Toast.makeText(getApplicationContext(),"Date is invalid",Toast.LENGTH_SHORT);
            return;
        }
        if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {

            AddEvent addEvent = new AddEvent();
            addEvent.execute();
        }
    }

    private boolean validTitle() {
        if (title.length() > 60) {
            Toast.makeText(getApplicationContext(), "Title cannot be more than 60 charecters", Toast.LENGTH_SHORT).show();
            requestFocus(mTitle);
            return false;
        }
        if (title.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Invalid Title", Toast.LENGTH_SHORT).show();
            requestFocus(mTitle);
            return false;
        }
        if (Constants.Methods.checkForSpecial(title)) {
            Toast.makeText(getApplicationContext(), "Remove special charecters from the Title field", Toast.LENGTH_SHORT).show();
            requestFocus(mTitle);
            return false;
        }
        return true;
    }

    private boolean validType() {
        if (type.length() > 60) {
            Toast.makeText(getApplicationContext(), "Type cannot be more than 60 charecters", Toast.LENGTH_SHORT).show();
            requestFocus(mType);
            return false;
        }
        if (type.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Invalid Type", Toast.LENGTH_SHORT).show();
            requestFocus(mType);
            return false;
        }
        if (Constants.Methods.checkForSpecial(type)) {
            Toast.makeText(getApplicationContext(), "Remove special charecters from the Type field", Toast.LENGTH_SHORT).show();
            requestFocus(mType);
            return false;
        }
        return true;
    }

    private boolean validDesc() {
        if (desc.length() > 5000) {
            Toast.makeText(getApplicationContext(), "Description cannot be more than 5000 charecters", Toast.LENGTH_SHORT).show();
            requestFocus(mDesc);
            return false;
        }
        if (desc.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Invalid Description", Toast.LENGTH_SHORT).show();
            requestFocus(mDesc);
            return false;
        }
        if (Constants.Methods.checkForSpecial(desc)) {
            Toast.makeText(getApplicationContext(), "Remove special charecters from the Description field", Toast.LENGTH_SHORT).show();
            requestFocus(mDesc);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String fullDate;
        String day, month;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        else
            day = "" + dayOfMonth;

        ++monthOfYear;

        if (monthOfYear < 10)
            month = "0" + monthOfYear;
        else
            month = "" + monthOfYear;

        fullDate = "" + year + "-" + (month) + "-" + day;


        mDate.setText(fullDate);
    }

    private class AddEvent extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {
                URL url = new URL(Constants.URLs.ALUMNI_BASE + Constants.URLs.ALUMNI_EVENTS);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Log.d("POST", "DATA ready to sent");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("requestType", "put")
                        .appendQueryParameter("name", title)
                        .appendQueryParameter("type", type)
                        .appendQueryParameter("edate", date)
                        .appendQueryParameter("desc", desc);
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
                finish();
            }

        }
    }

    public void date(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                AlumniAddEventActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        Calendar minDate = Calendar.getInstance();
        minDate.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setMinDate(minDate);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

}
