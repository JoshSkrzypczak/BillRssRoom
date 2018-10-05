package com.josh.billrssroom.ui.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OtherRssAdapter extends RecyclerView.Adapter<OtherRssAdapter.OtherRssViewHolder> {
    public static final String PAYLOAD_SAVE_BTN_CLICKED = "PAYLOAD_SAVE_BTN_CLICKED";

    private final LayoutInflater inflater;
    private List<FeedItem> feedItems;
    private Context context;
    private OnFeedItemClickListener onFeedItemClickListener;

    public OtherRssAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OtherRssViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_row_rss, parent, false);
        return new OtherRssViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherRssViewHolder holder, int position) {
        FeedItem item = feedItems.get(position);

        int adapterPosition = holder.getAdapterPosition();

        holder.bindView(item);

        holder.btnSave.setOnClickListener(v -> {
            onFeedItemClickListener.onSaveClick(v, holder.getAdapterPosition(), item);
            Toast.makeText(context, "Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            notifyItemChanged(adapterPosition, PAYLOAD_SAVE_BTN_CLICKED);
        });
    }

    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    public void setRssList(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return feedItems == null ? 0 : feedItems.size();
    }

    public static class OtherRssViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView descriptionView;
        TextView dateView;
        ImageButton btnSave;
        ImageButton btnShare;
        ImageButton btnBrowser;
        FeedItem feedItem;

        public OtherRssViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.text_title);
            descriptionView = itemView.findViewById(R.id.text_description);
            dateView = itemView.findViewById(R.id.text_date);
            btnSave = itemView.findViewById(R.id.btn_save);
            btnShare = itemView.findViewById(R.id.btn_share);
            btnBrowser = itemView.findViewById(R.id.btn_browser);
        }

        public void bindView(FeedItem feedItem) {
            this.feedItem = feedItem;
            int adapterPosition = getAdapterPosition();
            titleView.setText(feedItem.getTitle());
            descriptionView.setText(feedItem.getFormattedDescription());
            dateView.setText(feedItem.getFormattedDate());
        }
    }

    public interface OnFeedItemClickListener {
        void onSaveClick(View v, int position, FeedItem feedItem);
    }
}