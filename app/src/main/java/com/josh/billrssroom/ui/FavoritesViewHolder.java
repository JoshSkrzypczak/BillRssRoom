package com.josh.billrssroom.ui;

import android.support.v7.widget.RecyclerView;

import com.josh.billrssroom.databinding.ItemRowFavoritesBinding;

public class FavoritesViewHolder extends RecyclerView.ViewHolder {

    final ItemRowFavoritesBinding binding;

    public FavoritesViewHolder(ItemRowFavoritesBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
