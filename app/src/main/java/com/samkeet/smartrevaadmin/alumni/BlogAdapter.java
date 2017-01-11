package com.samkeet.smartrevaadmin.alumni;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samkeet.smartrevaadmin.R;

/**
 * Created by Frost on 1/11/2017.
 */

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {

    private String[] mName,mTitle,mDesc,mTime,mReplies;




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameView,mTitleView,mDescView,mTimeView,mRepliesView;

        public ViewHolder(View v) {
            super(v);
            mNameView = (TextView) v.findViewById(R.id.name);
            mDescView =(TextView) v.findViewById(R.id.desc);
            mTitleView =(TextView) v.findViewById(R.id.title);
            mTimeView =(TextView) v.findViewById(R.id.time);
            mRepliesView =(TextView) v.findViewById(R.id.replies);

        }
    }
    public BlogAdapter(String[] mName, String[] mTitle,String[] mDesc,String[] mTime,String[] mReplies) {
        this.mName = mName;
        this.mDesc =mDesc;
        this.mTitle=mTitle;
        this.mTime=mTime;
        this.mReplies=mReplies;


    }

    @Override
    public BlogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_blog_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.mNameView.setText(mName[position]);
        holder.mTitleView.setText(mTime[position]);
        holder.mDescView.setText(mDesc[position]);
        holder.mTimeView.setText(mTime[position]);
        holder.mRepliesView.setText(mReplies[position]);

    }

    @Override
    public int getItemCount() {
        return mName.length;
    }
}
