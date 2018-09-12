package com.josh.billrssroom.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.josh.billrssroom.R;
import com.josh.billrssroom.databinding.ItemRowFavoritesBinding;
import com.josh.billrssroom.model.BillItem;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

    private List<BillItem> billEntities;

    @Nullable
    private final FavoriteClickCallback favoriteClickCallback;

    public FavoritesAdapter(Context context, @Nullable FavoriteClickCallback favoriteClickCallback) {
        this.favoriteClickCallback = favoriteClickCallback;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ItemRowFavoritesBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_row_favorites,
                        parent, false);
        binding.setCallback(favoriteClickCallback);
        return new FavoritesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        BillItem item = billEntities.get(position);
        holder.binding.setBill(billEntities.get(position));
        holder.binding.executePendingBindings();
    }

    public void setBillEntities(List<BillItem> billEntities) {
        this.billEntities = billEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (billEntities != null)
            return billEntities.size();
        else return 0;
    }
}
