package com.samkeet.smartrevaadmin.Placement2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.smartrevaadmin.R;

/**
 * Created by Frost on 12-02-2017.
 */

public class Placement2ManageDriveAdapter extends RecyclerView.Adapter<Placement2ManageDriveAdapter.ViewHolder> {

    private String[] nName;
    private String[] nDate;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nNameView, nDateView;

        public ViewHolder(View v) {
            super(v);
            nNameView = (TextView) v.findViewById(R.id.camp_name);
            nDateView = (TextView) v.findViewById(R.id.ddate);

        }
    }

    public Placement2ManageDriveAdapter(String[] nName, String[] nDate) {
        this.nName = nName;
        this.nDate = nDate;

    }

    @Override
    public Placement2ManageDriveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_manage_drive, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.nNameView.setText(nName[position]);
        holder.nDateView.setText(nDate[position]);

    }

    @Override
    public int getItemCount() {
        return nName.length;
    }
}


