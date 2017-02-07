package com.samkeet.smartrevaadmin.Placement2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.samkeet.smartrevaadmin.R;

import java.util.ArrayList;

public class Placement2SelectBranchActivity extends AppCompatActivity {

    public ArrayList<String> data = new ArrayList<String>();
    public ArrayList<String> temp = new ArrayList<String>();
    public CheckBox branches[];
    public String selectedBranch[];
    public LinearLayout mainView;
    public Button mDone;
    public String sbranch;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_placement2_select_branch);

        mainView = (LinearLayout) findViewById(R.id.mainView);
        data = getIntent().getStringArrayListExtra("DATA");
        branches = new CheckBox[data.size()];
        mDone = (Button) findViewById(R.id.done);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < data.size(); i++) {
                    if (branches[i].isChecked()) {
                        temp.add(data.get(i));
                    }
                }

                selectedBranch = temp.toArray(new String[temp.size()]);

                if (selectedBranch.length > 0) {
                    sbranch = selectedBranch[0];
                }
                for (int i = 1; i < selectedBranch.length; i++) {
                    sbranch = sbranch.concat("|" + selectedBranch[i]);
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("DATA", sbranch);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        setView();

    }

    public void setView() {
        for (int i = 0; i < data.size(); i++) {
            branches[i] = new CheckBox(this);
            branches[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            branches[i].setText(data.get(i));
            mainView.addView(branches[i]);
        }

    }
}
