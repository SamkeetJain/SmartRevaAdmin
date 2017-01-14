package com.samkeet.smartrevaadmin.councling;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.smartrevaadmin.R;

/**
 * Created by FROST on 12/18/2016.
 */

public class CounclingMyAppointmentAdapter extends RecyclerView.Adapter<CounclingMyAppointmentAdapter.ViewHolder> {

    private String[] mTitle,mDesc,mDates,mName,mSrn,mTime;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitleView,mDescView,mDateView,mNameView,mSrnView,mTimeView;
        public ViewHolder(View v) {
            super(v);
            mTitleView = (TextView) v.findViewById(R.id.title);
            mDescView= (TextView) v.findViewById(R.id.desc);
            mDateView= (TextView) v.findViewById(R.id.datetext);
            mNameView= (TextView) v.findViewById(R.id.name);
            mSrnView= (TextView) v.findViewById(R.id.srn);
            mTimeView= (TextView) v.findViewById(R.id.time);


        }
    }


    public CounclingMyAppointmentAdapter(String[] mTitle, String[] mDesc, String[] mDates, String[] mName, String[] mSrn, String[] mTime ){
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mDates=mDates;
        this.mName=mName;
        this.mSrn=mSrn;
        this.mTime=mTime;

    }

    @Override
    public CounclingMyAppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_myappointment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitleView.setText(mTitle[position]);
        holder.mDescView.setText(mDesc[position]);
        holder.mDateView.setText(mDates[position]);
        holder.mNameView.setText(mName[position]);
        holder.mSrnView.setText(mSrn[position]);
        holder.mTimeView.setText(mTime[position]);

    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }
}