package com.samkeet.smartrevaadmin.Alumni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.samkeet.smartrevaadmin.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AlumniJobReferalManager extends AppCompatActivity {

    public TextView mName, mMobile, mComp, mType, mRole, mDesc, mEmail;
    public String name, mobile, comp, type, role, desc, email;

    public JSONObject object;
    public String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_job_referal_manager);

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
}
