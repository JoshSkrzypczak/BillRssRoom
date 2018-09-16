package com.josh.billrssroom.ui.feed;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.josh.billrssroom.R;
import com.josh.billrssroom.databinding.ItemRowRssBinding;
import com.josh.billrssroom.model.BillItem;

import java.util.Collections;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private final FeedClickListener feedClickListener;
    private List<BillItem> billItems = Collections.emptyList();

    public FeedAdapter(FeedClickListener feedClickListener) {
        this.feedClickListener = feedClickListener;
    }

    public void setBillItemList(List<BillItem> billItemsList) {
        billItems = billItemsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ItemRowRssBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_row_rss, parent, false);

        return new FeedViewHolder(binding, feedClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        final BillItem item = billItems.get(position);
        holder.binding.setBill(billItems.get(position));
        holder.binding.executePendingBindings();

        holder.billItem = item;
    }

    @Override
    public int getItemCount() {
        return billItems.size();
    }

    public interface ClickCallback {
        void onClick(BillItem billItem, int position);
    }
}