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

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.ViewHolder> {

    private String[] mTitle,mDesc,mDates;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitleView,mDescView,mDateView;
        public ViewHolder(View v) {
            super(v);
            mTitleView = (TextView) v.findViewById(R.id.title);
            mDescView= (TextView) v.findViewById(R.id.desc);
            mDateView= (TextView) v.findViewById(R.id.datetext);
        }
    }


    public MyAppointmentAdapter(String[] mTitle, String[] mDesc,String[] mDates ){
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mDates=mDates;
    }

    @Override
    public MyAppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_myappointment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitleView.setText(mTitle[position]);
        holder.mDescView.setText(mDesc[position]);
        holder.mDateView.setText(mDates[position]);
    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }
}