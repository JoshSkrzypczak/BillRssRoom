package com.josh.billrssroom.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.josh.billrssroom.R;
import com.josh.billrssroom.databinding.ItemRowRssBinding;
import com.josh.billrssroom.model.BillItem;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    List<BillItem> mBillItemList;
    LayoutInflater inflater;

    @Nullable
    private final BillClickCallback billClickCallback;


    public BillAdapter(Context context, @Nullable BillClickCallback billClickCallback) {
        inflater = LayoutInflater.from(context);
        this.billClickCallback = billClickCallback;
    }

    public void setBillItemList(List<BillItem> billItemList) {
        if (mBillItemList == null) {
            mBillItemList = billItemList;
            notifyItemRangeInserted(0, billItemList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mBillItemList.size();
                }

                @Override
                public int getNewListSize() {
                    return mBillItemList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mBillItemList.get(oldItemPosition).getTitle() ==
                            billItemList.get(newItemPosition).getTitle();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    BillItem newBillItem = billItemList.get(newItemPosition);
                    BillItem oldBillItem = mBillItemList.get(oldItemPosition);
                    return newBillItem.getTitle() == oldBillItem.getTitle();
                }
            });
            mBillItemList = billItemList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ItemRowRssBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_row_rss,
                        parent, false);
        binding.setCallback(billClickCallback);
        return new BillViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        final BillItem item = mBillItemList.get(position);
        holder.binding.setBill(mBillItemList.get(position));
        holder.binding.executePendingBindings();

        holder.binding.billTitle.setText(item.getTitle());
        holder.binding.billPubDate.setText(item.getFormattedDate());
        holder.binding.billDescription.setText(item.getFormattedDescription());

//        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z", Locale.US);
//        format.setTimeZone(TimeZone.getTimeZone("EST"));
//        try {
//            Date parsedDated = format.parse(item.getPubDate());
//            TimeZone tz = TimeZone.getTimeZone("America/Detroit");
//            SimpleDateFormat destinationFormat =
//                    new SimpleDateFormat("EEE, MMM dd, yyyy hh:mm a", Locale.US);
//            destinationFormat.setTimeZone(tz);
//            String result = destinationFormat.format(parsedDated);
//            holder.binding.billPubDate.setText(result);
//        } catch (ParseException e){
//            e.printStackTrace();
//        }

//        String date = item.getPubDate();
//        DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy hh:mm:ss z", Locale.US);
//        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy hh:mm a", Locale.US);
//        date = LocalDateTime.parse(date, parseFormatter).format(newFormatter);

    }

//    private String stripHtml(String billDescription) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return Html.fromHtml(billDescription, Html.FROM_HTML_MODE_LEGACY).toString();
//        } else {
//            return Html.fromHtml(billDescription).toString();
//        }
//    }

    @Override
    public int getItemCount() {
        return mBillItemList == null ? 0 : mBillItemList.size();
    }

    static class BillViewHolder extends RecyclerView.ViewHolder {

        final ItemRowRssBinding binding;

        public BillViewHolder(ItemRowRssBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
