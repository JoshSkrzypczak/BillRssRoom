package com.josh.billrssroom.ui.feed;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.josh.billrssroom.R;
import com.josh.billrssroom.databinding.ItemRowRssBinding;
import com.josh.billrssroom.model.BillModel;

import java.util.Collections;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillViewHolder> {

    private final BillItemClickListener billItemClickListener;
    private List<BillModel> billModels = Collections.emptyList();


    public BillAdapter(BillItemClickListener billItemClickListener) {
        this.billItemClickListener = billItemClickListener;
    }

    public void setBillsList(List<BillModel> billItemsList) {
        billModels = billItemsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ItemRowRssBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_row_rss, parent, false);

        return new BillViewHolder(binding, billItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        final BillModel item = billModels.get(position);
        holder.binding.setBill(billModels.get(position));
        holder.binding.executePendingBindings();

        holder.billModel = item;
    }

    @Override
    public int getItemCount() {
        return billModels.size();
    }
}