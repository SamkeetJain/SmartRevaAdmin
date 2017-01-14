package com.samkeet.smartrevaadmin.alumni;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.smartrevaadmin.R;

/**
 * Created by Sam on 17-Dec-16.
 */

public class AlumniEventListAdapter extends RecyclerView.Adapter<AlumniEventListAdapter.ViewHolder> {

    private String[] mName,mDate,mType,mDesc;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameView,mDateView,mTypeView,mDescView;

        public ViewHolder(View v) {
            super(v);
            mNameView = (TextView) v.findViewById(R.id.name);
            mDateView = (TextView) v.findViewById(R.id.date);
            mTypeView = (TextView) v.findViewById(R.id.type);
            mDescView = (TextView) v.findViewById(R.id.desc);

        }
    }
    public AlumniEventListAdapter(String[] mName, String[] mType, String[] mDate, String[] mDesc) {
        this.mName = mName;
        this.mDate = mDate;
        this.mDesc = mDesc;
        this.mType = mType;

    }

    @Override
    public AlumniEventListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_event_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.mNameView.setText(mName[position]);
        holder.mTypeView.setText(mType[position]);
        holder.mDateView.setText(mDate[position]);
        holder.mDescView.setText(mDesc[position]);

    }

    @Override
    public int getItemCount() {
        return mName.length;
    }
}
