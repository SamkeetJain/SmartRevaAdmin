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

public class ViewWallPostsAdapter extends RecyclerView.Adapter<ViewWallPostsAdapter.ViewHolder> {

    private String[] mSrn,mName,mDate,mTime;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mSrn,mName,mDate,mTime;
        public ViewHolder(View v) {
            super(v);
            mSrn = (TextView) v.findViewById(R.id.srn);
            mName= (TextView) v.findViewById(R.id.name);

        }
    }


    public ViewWallPostsAdapter(String[] mSrn, String[] mName,String[] mDate,String[] mTime){
        this.mSrn = mSrn;
        this.mName = mName;

    }

    @Override
    public ViewWallPostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_view_reservations, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSrn.setText(mSrn[position]);
        holder.mName.setText(mName[position]);

    }

    @Override
    public int getItemCount() {
        return mSrn.length;
    }
}