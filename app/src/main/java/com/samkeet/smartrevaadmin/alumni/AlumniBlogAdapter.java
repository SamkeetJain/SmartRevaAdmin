package com.samkeet.smartrevaadmin.Alumni;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samkeet.smartrevaadmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import dmax.dialog.SpotsDialog;

/**
 * Created by Frost on 1/11/2017.
 */

public class AlumniBlogAdapter extends RecyclerView.Adapter<AlumniBlogAdapter.ViewHolder> {

    public boolean authenticationError = true;
    public String errorMessage = "Data courpted";
    public SpotsDialog pd;
    private JSONObject[] disscussionObjects;
    private String[] name, loc, title, desc, time, stars, replies, id, starStatus;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mStarClicker, mReplyClicker;
        public TextView mName, mloc, mTitle, mDesc, mTime, mStars, mReplies;

        public ViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.name);
            mloc = (TextView) v.findViewById(R.id.loc);
            mTitle = (TextView) v.findViewById(R.id.title);
            mDesc = (TextView) v.findViewById(R.id.desc);
            mTime = (TextView) v.findViewById(R.id.time);
            mStars = (TextView) v.findViewById(R.id.starscount);
            mReplies = (TextView) v.findViewById(R.id.repiescount);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlumniBlogAdapter(JSONObject[] disscussionObjects, Context context) {
        this.disscussionObjects = disscussionObjects;
        this.context = context;

        name = new String[disscussionObjects.length];
        loc = new String[disscussionObjects.length];
        title = new String[disscussionObjects.length];
        desc = new String[disscussionObjects.length];
        time = new String[disscussionObjects.length];
        stars = new String[disscussionObjects.length];
        replies = new String[disscussionObjects.length];
        id = new String[disscussionObjects.length];
        starStatus = new String[disscussionObjects.length];


        for (int i = 0; i < disscussionObjects.length; i++) {
            try {
                name[i] = disscussionObjects[i].getString("name");
                loc[i] = disscussionObjects[i].getString("loc");
                title[i] = disscussionObjects[i].getString("title");
                desc[i] = disscussionObjects[i].getString("message");
                time[i] = disscussionObjects[i].getString("ddate");
                stars[i] = disscussionObjects[i].getString("starscount");
                replies[i] = disscussionObjects[i].getString("repliescount");
                id[i] = disscussionObjects[i].getString("Id");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlumniBlogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_alumni_disscussion, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ViewHolder h = holder;
        holder.mName.setText(name[position]);
        holder.mloc.setText(loc[position]);
        holder.mTitle.setText(title[position]);
        holder.mDesc.setText(desc[position]);
        holder.mTime.setText(time[position]);
        String starscount = stars[position] + " Stars";
        holder.mStars.setText(starscount);
        String repliescount = replies[position] + " Replies";
        holder.mReplies.setText(repliescount);

        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlumniBlogManager.class);
                intent.putExtra("DATA", disscussionObjects[position].toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.mDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlumniBlogManager.class);
                intent.putExtra("DATA", disscussionObjects[position].toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return name.length;
    }


}
