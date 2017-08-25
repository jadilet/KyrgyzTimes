package com.developer.artisla.kyrgyztimes.adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.developer.artisla.kyrgyztimes.NewsShow;
import com.developer.artisla.kyrgyztimes.R;
import com.developer.artisla.kyrgyztimes.model.News;

import java.util.List;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */

public class NewsCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "NewsCustomAdapter";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<News> mDataSet;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView headerTv;

        public HeaderViewHolder(View v) {
            super(v);
            headerTv = (TextView) v.findViewById(R.id.headerTv);
        }

        public TextView getHeaderTv(){
            return this.headerTv;
        }
    }

    public static class NewsVideoHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private final TextView titleTv;
        private final TextView commentTv;

        public NewsVideoHolder(View v) {
            super(v);

            titleTv = (TextView) v.findViewById(R.id.titleTv);
            commentTv = (TextView) v.findViewById(R.id.commentTv);

            v.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {}

        public TextView getCommentTv() {
            return this.commentTv;
        }

        public TextView getTitleTv() {
            return this.titleTv;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public NewsCustomAdapter(List<News> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public int getItemViewType(int position) {

        return (mDataSet.get(position).getHeader().isEmpty())?TYPE_ITEM:TYPE_HEADER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v;

        switch (viewType){
            case TYPE_ITEM:
                 v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.news_list, viewGroup, false);
                return new NewsVideoHolder(v);
            case TYPE_HEADER:
                 v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_item, viewGroup, false);
                return new HeaderViewHolder(v);
        }

        return null;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your data set at this position and replace the contents of the view
        // with that element
        switch (viewHolder.getItemViewType()){
            case TYPE_HEADER:
                ((HeaderViewHolder) viewHolder).getHeaderTv().setText(mDataSet.get(position).getHeader());
                break;
            case TYPE_ITEM:
                ((NewsVideoHolder) viewHolder).getTitleTv().setText(mDataSet.get(position).getTitle());
                ((NewsVideoHolder) viewHolder).getCommentTv().setText(String.valueOf(mDataSet.get(position).getComment()));
                break;

        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG,mDataSet.get(position).getTitle()+" Clicked!!! "+position);

                News news = mDataSet.get(position);
                if (mDataSet.get(position).getHeader().isEmpty()) {

                 //   Log.d("News title ",mDataSet.get(position).getTitle());

                    Intent intent = new Intent(view.getContext(), NewsShow.class);
                    intent.putExtra("News", news);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}