package com.samkeet.smartrevaadmin.Placements;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.samkeet.smartrevaadmin.Constants;
import com.samkeet.smartrevaadmin.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dmax.dialog.SpotsDialog;

public class PlacementDriveActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public SpotsDialog pd;
    public Context progressDialogContext;

    public SwipeRefreshLayout swipeRefreshLayout;
    public String[] mTitle, mDate, mRole, mDept;
    public JSONObject[] driveObjects;

    public boolean authenticationError = true;
    public String errorMessage = "Data Corrupted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_manage_event);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        progressDialogContext = this;
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {
                    GetDrives getEvents = new GetDrives();
                    getEvents.execute();
                }
            }
        });

        final GestureDetector mGestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent motionEvent) {

                View child = mRecyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int temp = mRecyclerView.getChildPosition(child);
                    Intent intent = new Intent(getApplicationContext(), PlacementDriveManager.class);
                    intent.putExtra("DATA", driveObjects[temp].toString());
                    startActivity(intent);

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        GetDrives getDrives = new GetDrives();
        getDrives.execute();
    }

    public void BackButton (View v){finish();}

    public class GetDrives extends AsyncTask<Void, Void, Integer> {


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

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("token", Constants.SharedPreferenceData.getTOKEN()).appendQueryParameter("type", "get");
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
                    JSONArray jsonArray = new JSONArray(jsonResults.toString());
                    driveObjects=new JSONObject[jsonArray.length()];
                    mTitle = new String[jsonArray.length()];
                    mDate = new String[jsonArray.length()];
                    mDept = new String[jsonArray.length()];
                    mRole = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        driveObjects[i]=jsonObject;
                        mTitle[i] = jsonObject.getString("comp_name");
                        mDept[i] = jsonObject.getString("dept");
                        mDate[i] = jsonObject.getString("ddate");
                        mRole[i] = jsonObject.getString("role");
                    }
                    authenticationError=false;
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
            swipeRefreshLayout.setRefreshing(false);
            if (authenticationError) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                mAdapter = new PlacementDriveAdapter(mTitle, mDate, mDept, mRole);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

}
