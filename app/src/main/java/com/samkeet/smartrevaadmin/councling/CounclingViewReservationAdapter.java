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

public class CounclingViewReservationAdapter extends RecyclerView.Adapter<CounclingViewReservationAdapter.ViewHolder> {

    private String[] mSrn,mName,mDate,mTime;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mSrnview,mNameview,mDateview,mTimeview;
        public ViewHolder(View v) {
            super(v);
            mSrnview = (TextView) v.findViewById(R.id.srn);
            mNameview= (TextView) v.findViewById(R.id.name);
            mDateview= (TextView) v.findViewById(R.id.date);
            mTimeview= (TextView) v.findViewById(R.id.time);


        }
    }


    public CounclingViewReservationAdapter(String[] mSrn, String[] mName, String[] mDate, String[] mTime){
        this.mSrn = mSrn;
        this.mName = mName;
        this.mDate = mDate;
        this.mTime= mTime;

    }

    @Override
    public CounclingViewReservationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_view_reservations, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSrnview.setText(mSrn[position]);
        holder.mNameview.setText(mName[position]);
        holder.mTimeview.setText(mTime[position]);
        holder.mDateview.setText(mDate[position]);

    }

    @Override
    public int getItemCount() {
        return mSrn.length;
    }
}