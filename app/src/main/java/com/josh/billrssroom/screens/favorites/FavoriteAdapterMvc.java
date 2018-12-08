package com.josh.billrssroom.screens.favorites;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.ViewMvcFactory;
import com.josh.billrssroom.screens.favorites.favoritelistitems.FavoriteItemViewMvc;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapterMvc extends RecyclerView.Adapter<FavoriteAdapterMvc.FavoriteViewHolderMvc>
        implements FavoriteItemViewMvc.Listener {

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
    private Context context;
    private ViewMvcFactory viewMvcFactory;
    private List<FeedItem> favoriteItems;

    public FavoriteAdapterMvc(Context context, Listener listener, ViewMvcFactory viewMvcFactory) {
        this.viewMvcFactory = viewMvcFactory;
        this.listener = listener;
        this.context = context;
    }

    public void setFavoriteItems(List<FeedItem> favoriteItems) {
        this.favoriteItems = favoriteItems;
        notifyDataSetChanged();
    }

    public void setFavoriteItemList(final List<FeedItem> itemList) {
        if (favoriteItems == null) {
            favoriteItems = itemList;
            notifyItemRangeInserted(0, itemList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return favoriteItems.size();
                }

                @Override
                public int getNewListSize() {
                    return itemList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return favoriteItems.get(oldItemPosition).getTitle()
                            .equals(itemList.get(newItemPosition).getTitle());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    FeedItem newProduct = itemList.get(newItemPosition);
                    FeedItem oldProduct = favoriteItems.get(oldItemPosition);
                    return newProduct.getTitle().equals(oldProduct.getTitle());
                }
            });
            favoriteItems = itemList;
            result.dispatchUpdatesTo(this);
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
        return favoriteItems == null ? 0 : favoriteItems.size();
    }


    @Override
    public void onDeleteBtnClicked(FeedItem feedItem, int position) {
        listener.onDeleteBtnClicked(feedItem, position);
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        listener.onShareBtnClicked(feedItem, position);
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem) {
        listener.onBrowserBtnClicked(feedItem);
    }
}