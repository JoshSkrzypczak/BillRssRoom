package com.josh.billrssroom.ui.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.josh.billrssroom.R;
import com.josh.billrssroom.databinding.ItemRowRssBinding;
import com.josh.billrssroom.model.BillModel;

import java.lang.ref.WeakReference;

public class BillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private WeakReference<BillItemClickListener> feedClickRef;
    final ItemRowRssBinding binding;
    public BillModel billModel;


    public BillViewHolder(ItemRowRssBinding binding, BillItemClickListener billItemClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        feedClickRef = new WeakReference<>(billItemClickListener);
        binding.btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                feedClickRef.get().onSaveClicked(billModel, getAdapterPosition());
                break;
        }
    }
}