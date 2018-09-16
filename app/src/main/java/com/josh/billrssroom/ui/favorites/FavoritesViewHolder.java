package com.josh.billrssroom.ui.favorites;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.josh.billrssroom.R;
import com.josh.billrssroom.databinding.ItemRowFavoritesBinding;
import com.josh.billrssroom.model.BillItem;

import java.lang.ref.WeakReference;

public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private WeakReference<FavoriteClickListener> favClickRef;
    public BillItem billItem;
    final ItemRowFavoritesBinding binding;

    public FavoritesViewHolder(ItemRowFavoritesBinding binding, FavoriteClickListener myFavClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        favClickRef = new WeakReference<>(myFavClickListener);
        itemView.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share:
                favClickRef.get().onShareClick(billItem, getAdapterPosition());
                break;
            case R.id.btn_browser:
                favClickRef.get().onBrowserClick(billItem, getAdapterPosition());
                break;
            case R.id.btn_trash:
                favClickRef.get().onTrashClick(billItem, getAdapterPosition());
                break;
        }

        favClickRef.get().onTrashClick(billItem, getAdapterPosition());

    }
}