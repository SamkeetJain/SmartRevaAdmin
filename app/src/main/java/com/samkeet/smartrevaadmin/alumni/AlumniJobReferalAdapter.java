package com.samkeet.smartrevaadmin.Alumni;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.smartrevaadmin.R;

/**
 * Created by Blazzing Frost on 1/11/2017.
 */

public class AlumniJobReferalAdapter extends RecyclerView.Adapter<AlumniJobReferalAdapter.ViewHolder> {

    private String[] mName,mCompname,mJobtype,mJobrole;




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameView,mCompnameView,mJobtypeView,mJobroleView;

        public ViewHolder(View v) {
            super(v);
            mNameView = (TextView) v.findViewById(R.id.name);
            mCompnameView =(TextView) v.findViewById(R.id.compname);
            mJobroleView =(TextView) v.findViewById(R.id.jobrole);
            mJobtypeView =(TextView) v.findViewById(R.id.jobtype);

        }
    }
    public AlumniJobReferalAdapter(String[] mName, String[] mCompname, String[] mJobtype, String[] mJobrole) {
        this.mName = mName;
        this.mCompname=mCompname;
        this.mJobtype=mJobtype;
        this.mJobrole=mJobrole;
    }

    @Override
    public AlumniJobReferalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_job_referals, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.mNameView.setText(mName[position]);
        holder.mCompnameView.setText(mCompname[position]);
        holder.mJobtypeView.setText(mJobtype[position]);
        holder.mJobroleView.setText(mJobrole[position]);

    }

    @Override
    public int getItemCount() {
        return mName.length;
    }
}

