package com.samkeet.smartrevaadmin.Alumni;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.smartrevaadmin.R;

/**
 * Created by Sam on 17-Dec-16.
 */


public class AlumniMemberRequestAdapter extends RecyclerView.Adapter<AlumniMemberRequestAdapter.ViewHolder> {

    private String[] mName;
    private String[] mMobileno;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameView,mMobilenoView;

        public ViewHolder(View v) {
            super(v);
            mNameView = (TextView) v.findViewById(R.id.name);
            mMobilenoView = (TextView) v.findViewById(R.id.mobileno);

        }
    }
    public AlumniMemberRequestAdapter(String[] mName, String[] mMobileno) {
        this.mName = mName;
        this.mMobileno= mMobileno;

    }

    @Override
    public AlumniMemberRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_member_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.mNameView.setText(mName[position]);
        holder.mMobilenoView.setText(mMobileno[position]);

    }

    @Override
    public int getItemCount() {
        return mName.length;
    }
}

