package com.samkeet.smartrevaadmin.Alumni;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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

public class AlumniBlogManager extends AppCompatActivity {

    public JSONObject jsonObject;
    public String data;
    public TextView mName, mloc, mTitle, mDesc, mTime, mStars, mReplies;
    public String name, loc, title, desc, time, stars, replies, starStatus, ID;

    public SpotsDialog pd;
    public Context progressDialogContext;

    public boolean authenticationError = true;
    public String errorMessage = "Data Corupted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_blog_manager);
        progressDialogContext = this;

        String temp = getIntent().getStringExtra("DATA");
        try {
            jsonObject = new JSONObject(temp);
            name = jsonObject.getString("name");
            loc = jsonObject.getString("loc");
            title = jsonObject.getString("title");
            desc = jsonObject.getString("message");
            time = jsonObject.getString("ddate");
            stars = jsonObject.getString("starscount");
            replies = jsonObject.getString("repliescount");
            starStatus = jsonObject.getString("starStatus");
            ID = jsonObject.getString("Id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mName = (TextView) findViewById(R.id.name);
        mloc = (TextView) findViewById(R.id.loc);
        mTitle = (TextView) findViewById(R.id.title);
        mDesc = (TextView) findViewById(R.id.desc);
        mTime = (TextView) findViewById(R.id.time);
        mStars = (TextView) findViewById(R.id.starscount);
        mReplies = (TextView) findViewById(R.id.repiescount);

        mName.setText(name);
        mloc.setText(loc);
        mTitle.setText(title);
        mDesc.setText(desc);
        mTime.setText(time);
        temp = stars + " Stars";
        mStars.setText(temp);
        temp = replies + " Replies";
        mReplies.setText(temp);
    }

    public void BackButton(View v) {
        finish();
    }

    public void Delete(View v) {
        if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {

            DeletePost deletePost = new DeletePost();
            deletePost.execute();
        }

    }
    public class DeletePost extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute() {
            pd = new SpotsDialog(progressDialogContext, R.style.CustomPD);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        protected Integer doInBackground(Void... params) {
            try {

                URL url = new URL(Constants.URLs.ALUMNI_BASE + Constants.URLs.ALUMNI_DISSCUSSION);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN())
                        .appendQueryParameter("requestType", "delete")
                        .appendQueryParameter("id", ID);
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
                finish();
            }
        }

    }

}
