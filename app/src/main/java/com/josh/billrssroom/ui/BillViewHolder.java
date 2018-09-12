package com.josh.billrssroom.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.josh.billrssroom.databinding.ItemRowRssBinding;

public class BillViewHolder extends RecyclerView.ViewHolder {

    final ItemRowRssBinding binding;

    public BillViewHolder(ItemRowRssBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
