package com.josh.billrssroom.screens.favorites;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.ViewMvcFactory;
import com.josh.billrssroom.screens.favorites.favoritelistitems.FavoriteItemViewMvc;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapterMvc extends RecyclerView.Adapter<FavoriteAdapterMvc.FavoriteViewHolderMvc>
        implements FavoriteItemViewMvc.Listener {

    private static final String TAG = "FavoriteAdapterMvc";

    public interface Listener {
        void onDeleteBtnClicked(FeedItem feedItem, int position);
        void onShareBtnClicked(FeedItem feedItem, int position);
        void onBrowserBtnClicked(FeedItem feedItem);
    }

    static class FavoriteViewHolderMvc extends RecyclerView.ViewHolder {
        private final FavoriteItemViewMvc favoriteItemViewMvc;

        public FavoriteViewHolderMvc(FavoriteItemViewMvc favoriteItemViewMvc) {
            super(favoriteItemViewMvc.getRootView());
            this.favoriteItemViewMvc = favoriteItemViewMvc;
        }
    }

    private final Listener listener;
    private ViewMvcFactory viewMvcFactory;
    private List<FeedItem> favoriteItems;

    public FavoriteAdapterMvc(Listener listener, ViewMvcFactory viewMvcFactory) {
        this.viewMvcFactory = viewMvcFactory;
        this.listener = listener;
    }

    public void setFavoriteItemList(final List<FeedItem> itemList) {
//        notifyDataSetChanged(); // calling this here blocks animation
        if (favoriteItems == null){
            favoriteItems = itemList;
            notifyDataSetChanged();
        } else {
            FeedDiffCallback feedDiffCallback = new FeedDiffCallback(favoriteItems, itemList);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(feedDiffCallback);

            /* Calling this causes the list to clear entirely after closing the SearchView */
//            this.favoriteItems.clear();
//            this.favoriteItems.addAll(itemList);

            favoriteItems = itemList;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public FavoriteViewHolderMvc onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavoriteItemViewMvc favoriteItemViewMvc = viewMvcFactory.getFavoriteItemViewMvc(parent);
        favoriteItemViewMvc.registerListener(this);
        return new FavoriteViewHolderMvc(favoriteItemViewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolderMvc holder, int position) {
        holder.favoriteItemViewMvc.bindItem(favoriteItems.get(position), holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        if (favoriteItems != null)
            return favoriteItems.size();
        else return 0;
    }

    @Override
    public void onDeleteBtnClicked(FeedItem feedItem, int position) {
        Log.d(TAG, "onDeleteBtnClicked: " + position);
        listener.onDeleteBtnClicked(feedItem, position);
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        Log.d(TAG, "onShareBtnClicked: " + position);
        listener.onShareBtnClicked(feedItem, position);
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem) {
        listener.onBrowserBtnClicked(feedItem);
    }

    class FeedDiffCallback extends DiffUtil.Callback {

        private final List<FeedItem> oldItems, newItems;

        public FeedDiffCallback(List<FeedItem> oldItems, List<FeedItem> newItems) {
            this.oldItems = oldItems;
            this.newItems = newItems;
        }

        @Override
        public int getOldListSize() {
            return oldItems.size();
        }

        @Override
        public int getNewListSize() {
            return newItems.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldItems.get(oldItemPosition).getTitle()
                    .equals(newItems.get(newItemPosition).getTitle());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            FeedItem newFeedItem = newItems.get(newItemPosition);
            FeedItem oldFeedItem = oldItems.get(oldItemPosition);
            return newFeedItem.getTitle().equals(oldFeedItem.getTitle());
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}