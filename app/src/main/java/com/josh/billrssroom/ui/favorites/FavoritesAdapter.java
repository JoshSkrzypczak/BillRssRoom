package com.josh.billrssroom.ui.favorites;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.josh.billrssroom.R;
import com.josh.billrssroom.databinding.ItemRowFavoritesBinding;
import com.josh.billrssroom.model.BillItem;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

    private final FavoriteClickListener favoriteClickListener;
    private List<BillItem> billItems;


    public FavoritesAdapter(Context context, FavoriteClickListener favoriteClickListener) {
        this.favoriteClickListener = favoriteClickListener;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ItemRowFavoritesBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_row_favorites,
                        parent, false);
        return new FavoritesViewHolder(binding, favoriteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        BillItem item = billItems.get(position);
        holder.binding.setBill(billItems.get(position));
        holder.binding.executePendingBindings();

        holder.billItem = item;
    }

    public void setFavoriteItems(List<BillItem> billEntities) {
        this.billItems = billEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return billItems == null ? 0 : billItems.size();
    }
}