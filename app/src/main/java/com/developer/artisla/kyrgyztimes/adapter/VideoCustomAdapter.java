package com.developer.artisla.kyrgyztimes.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.developer.artisla.kyrgyztimes.R;
import com.developer.artisla.kyrgyztimes.PlayerYoutube;
import com.developer.artisla.kyrgyztimes.model.Videos;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.ProgressBar;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class VideoCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "VideoCustomAdapter";

    private List<Videos> mDataSet;


    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */

    public static class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final TextView titleTv;
        private final ImageView newsIv;



        public VideoViewHolder(View v) {
            super(v);

            titleTv = (TextView) v.findViewById(R.id.titleTv);
            newsIv = (ImageView) v.findViewById(R.id.newsIv);

            v.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {}

        public ImageView getNewsIvView() {
            return newsIv;
        }


        public TextView getTitleTvView() {
            return titleTv;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public VideoCustomAdapter(List<Videos> dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        RecyclerView.ViewHolder viewHolder;


            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.video_list, viewGroup, false);
            viewHolder = new VideoViewHolder(v);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your data set at this position and replace the contents of the view
        // with that element
            ((VideoViewHolder) viewHolder).getTitleTvView().setText(mDataSet.get(position).getTitle());
            Context context = ((VideoViewHolder) viewHolder).getNewsIvView().getContext();
            Picasso.with(context).load(Uri.parse(mDataSet.get(position).getImagePath())).into(((VideoViewHolder) viewHolder).getNewsIvView());
      //  Log.d(TAG, mDataSet.get(position).getVideoUrl() + " Clicked!!!");
/*
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d(TAG, mDataSet.get(position).getId() + " onLongClickListener()");

                ViewHolder.COMPANY = mDataSet.get(position);
                //Toast.makeText(view.getContext(), String.valueOf(mDataSet.get(position).getId()), Toast.LENGTH_SHORT).show();


                return false;
            }
        });*/

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Videos videos = mDataSet.get(position);

                Log.d(TAG,videos.getVideoUrl()+" Clicked!!!");
                String watchId="";
                String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

                Pattern compiledPattern = Pattern.compile(pattern);
                Matcher matcher = compiledPattern.matcher(videos.getVideoUrl());

                if(matcher.find()){
                    watchId = matcher.group();
                }
                Log.d("Watch id youtube: ",watchId);
                Intent intent = new Intent(view.getContext(), PlayerYoutube.class);
                intent.putExtra("Videos",mDataSet.get(position));
                view.getContext().startActivity(intent);

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}