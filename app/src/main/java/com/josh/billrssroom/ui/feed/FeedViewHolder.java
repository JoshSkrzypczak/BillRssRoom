package com.josh.billrssroom.ui.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.josh.billrssroom.R;
import com.josh.billrssroom.databinding.ItemRowRssBinding;
import com.josh.billrssroom.model.BillItem;

import java.lang.ref.WeakReference;

public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private WeakReference<FeedClickListener> feedClickRef;
    final ItemRowRssBinding binding;
    public BillItem billItem;


    public FeedViewHolder(ItemRowRssBinding binding, FeedClickListener feedClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        feedClickRef = new WeakReference<>(feedClickListener);
        binding.btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                feedClickRef.get().onSaveClicked(billItem, getAdapterPosition());
                break;
        }
    }
}