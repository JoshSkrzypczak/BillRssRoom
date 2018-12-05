package com.josh.billrssroom.screens.feed;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.ViewMvcFactory;
import com.josh.billrssroom.screens.feed.feedlistitems.FeedItemViewMvc;
import com.josh.billrssroom.utilities.AsyncResponse;
import com.josh.billrssroom.utilities.AsyncRowTask;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import java.io.FilterReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapterMvc extends RecyclerView.Adapter<FeedAdapterMvc.FeedViewHolderMvc>
        implements FeedItemViewMvc.Listener {

    public interface Listener {
        void onShareBtnClicked(FeedItem feedItem, int position);
        void onBrowserBtnClicked(FeedItem feedItem, int position);
        void onSaveBtnClicked(FeedItem feedItem, int position);
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


    public void setFeedItemList(final List<FeedItem> feedItemList){
        if (this.feedItems == null){
            this.feedItems = feedItemList;
            notifyItemRangeInserted(0, feedItemList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return feedItems.size();
                }

                @Override
                public int getNewListSize() {
                    return feedItemList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return feedItems.get(oldItemPosition).getDescription()
                            .equals(feedItemList.get(newItemPosition).getDescription());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    FeedItem newItem = feedItemList.get(newItemPosition);
                    FeedItem oldItem = feedItems.get(oldItemPosition);
                    return newItem.getDescription().equals(oldItem.getDescription());
                }
            });
            feedItems = feedItemList;
            result.dispatchUpdatesTo(this);
        }
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
         * Get the boolean value of each row and setImageResource of the save button:
         * 0 =  Item is Not saved. Use empty heart drawable
         * 1  = Item is saved. Use full hear drawable.
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

        // TODO: 10/30/2018 Implement item saving via onClick here or in activity?
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

    @Override
    public void onSaveBtnClicked(FeedItem feedItem, int position) {
        listener.onSaveBtnClicked(feedItem, position);
    }
}