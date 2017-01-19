package com.samkeet.smartrevaadmin.Placements;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.smartrevaadmin.R;

/**
 * Created by Frost on 1/10/2017.
 */

public class PlacementDriveAdapter extends RecyclerView.Adapter<PlacementDriveAdapter.ViewHolder> {

    private String[] mName,mDate,mDepartment,mJobrole;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameView,mDateView,mDepartmentView,mJobroleView;

        public ViewHolder(View v) {
            super(v);
            mNameView = (TextView) v.findViewById(R.id.name);
            mDateView = (TextView) v.findViewById(R.id.date);
            mDepartmentView = (TextView) v.findViewById(R.id.department);
            mJobroleView = (TextView) v.findViewById(R.id.jobrole);

        }
    }
    public PlacementDriveAdapter(String[] mName, String[] mDate, String[] mDepartment,String[] mJobrole) {
        this.mName = mName;
        this.mDate = mDate;
        this.mDepartment=mDepartment;
        this.mJobrole = mJobrole;

    }

    @Override
    public PlacementDriveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_placement_drive, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.mNameView.setText(mName[position]);
        holder.mDateView.setText(mDate[position]);
        holder.mDepartmentView.setText(mDepartment[position]);
        holder.mJobroleView.setText(mJobrole[position]);

    }

    @Override
    public int getItemCount() {
        return mName.length;
    }
}
