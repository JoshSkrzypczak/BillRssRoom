package com.josh.billrssroom.screens.feed;

import android.content.Context;
import android.view.ViewGroup;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.ViewMvcFactory;
import com.josh.billrssroom.screens.feed.feedlistitems.FeedItemViewMvc;
import com.josh.billrssroom.utilities.AsyncResponse;
import com.josh.billrssroom.utilities.AsyncRowTask;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapterMvc extends RecyclerView.Adapter<FeedAdapterMvc.FeedViewHolderMvc>
        implements FeedItemViewMvc.Listener {


    public interface Listener {
        void onShareBtnClicked(FeedItem feedItem, int position);
        void onBrowserBtnClicked(FeedItem feedItem, int position);
    }

    static class FeedViewHolderMvc extends RecyclerView.ViewHolder {
        private final FeedItemViewMvc feedItemViewMvc;

        public FeedViewHolderMvc(FeedItemViewMvc feedItemViewMvc) {
            super(feedItemViewMvc.getRootView());
            this.feedItemViewMvc = feedItemViewMvc;
        }
    }

    private final Listener listener;
    private Context context;
    private ViewMvcFactory viewMvcFactory;
    private List<FeedItem> feedItems;

    public FeedAdapterMvc(Context context, Listener listener, ViewMvcFactory viewMvcFactory) {
        this.listener = listener;
        this.context = context;
        this.viewMvcFactory = viewMvcFactory;
    }

    public void setFeedItems(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedViewHolderMvc onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FeedItemViewMvc feedItemViewMvc = viewMvcFactory.getFeedItemViewMvc(parent);
        feedItemViewMvc.registerListener(this);
        return new FeedViewHolderMvc(feedItemViewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolderMvc holder, int position) {
        holder.feedItemViewMvc.bindItem(feedItems.get(position), holder.getAdapterPosition());

        /*
         * Get the boolean value of each row and set the drawable as full or empty.
         * 0 not saved is empty. 1 is saved is full.
         */
        AsyncRowTask asyncRowTask = new AsyncRowTask(context.getApplicationContext(), new AsyncResponse() {
            @Override
            public void onPreExecute(int position) {
            }

            @Override
            public void onProgressUpdate(int value) {
            }

            @Override
            public void onPostExecute(int favoriteValueInt) {
                if (favoriteValueInt > 0) {
                    holder.feedItemViewMvc.setImageResource(R.drawable.ic_favorite_full);

                } else {
                    holder.feedItemViewMvc.setImageResource(R.drawable.ic_favorite_empty);
                }
            }
        });
        asyncRowTask.execute(feedItems.get(position));

        // TODO: 10/30/2018 Implement onClick to save
    }

    @Override
    public int getItemCount() {
        return feedItems == null ? 0 : feedItems.size();
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        listener.onShareBtnClicked(feedItem, position);
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem, int position) {
        listener.onBrowserBtnClicked(feedItem, position);
    }
}