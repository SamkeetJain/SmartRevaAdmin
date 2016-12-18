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

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private String[] name,date,type,desc;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,date,type,desc;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            date = (TextView) v.findViewById(R.id.date);
            type = (TextView) v.findViewById(R.id.type);
            desc = (TextView) v.findViewById(R.id.desc);

        }
    }
    public EventListAdapter(String[] name, String[] date, String[] type, String[] desc) {
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.type = type;

    }

    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_event_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.name.setText(name[position]);
        holder.type.setText(type[position]);
        holder.date.setText(date[position]);
        holder.desc.setText(desc[position]);

    }

    @Override
    public int getItemCount() {
        return name.length;
    }
}
