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

public class CounclingViewWallPostsAdapter extends RecyclerView.Adapter<CounclingViewWallPostsAdapter.ViewHolder> {

    private String[] mTitle,mDesc;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle,mDesc;
        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.title);
            mDesc= (TextView) v.findViewById(R.id.desc);


        }
    }


    public CounclingViewWallPostsAdapter(String[] mDesc, String[] mTitle){
        this.mDesc = mDesc;
        this.mTitle = mTitle;

    }

    @Override
    public CounclingViewWallPostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_view_wallposts, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mDesc.setText(mDesc[position]);
        holder.mTitle.setText(mTitle[position]);

    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }
}