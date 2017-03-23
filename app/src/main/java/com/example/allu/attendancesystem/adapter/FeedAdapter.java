package com.example.allu.attendancesystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.allu.attendancesystem.pojo.Feed;
import com.example.allu.attendancesystem.R;
import com.example.allu.attendancesystem.ui.ViewFeed_Activity;
import com.example.allu.attendancesystem.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by allu on 2/15/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedView> {
    Context mContext;
    ArrayList<Feed> feedArrayList;
    SimpleDateFormat dateFormat;
    Utils utils;

    public FeedAdapter(Context mContext, ArrayList<Feed> feedArrayList) {
        this.mContext = mContext;
        this.feedArrayList = feedArrayList;
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        utils = new Utils(mContext);
    }

    @Override
    public FeedView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_feed,parent,false);
        return new FeedView(v);
    }

    @Override
    public void onBindViewHolder(FeedView holder, int position) {
        final Feed feed = feedArrayList.get(position);
        holder.Heading.setText(feed.getHeading());
        holder.Description.setText(feed.getDescription());

        holder.Created.setText(feed.getCreated());

        holder.Single_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,ViewFeed_Activity.class);
                i.putExtra("feed",feed);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedArrayList.size();
    }

    public void setFeed(Feed feed){
        feedArrayList.add(feed);
        this.notifyDataSetChanged();
    }
}

class FeedView extends RecyclerView.ViewHolder{
    TextView Heading,Description,Created;
    CardView Single_feed;
    public FeedView(View itemView) {
        super(itemView);
        Heading = (TextView)itemView.findViewById(R.id.txt_head);
        Description = (TextView)itemView.findViewById(R.id.txt_desc);
        Created = (TextView)itemView.findViewById(R.id.txt_created);
        Single_feed = (CardView)itemView.findViewById(R.id.card_single_feed);
    }
}
