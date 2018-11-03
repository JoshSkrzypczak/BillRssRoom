package com.josh.billrssroom.screens.feed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.utilities.AsyncClickTask;
import com.josh.billrssroom.utilities.AsyncResponse;
import com.josh.billrssroom.utilities.AsyncRowTask;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OtherRssAdapter extends RecyclerView.Adapter<OtherRssAdapter.OtherRssViewHolder> {

    private static final String TAG = OtherRssAdapter.class.getSimpleName();
    private static final String PAYLOAD_SAVE_BTN_CLICKED = "PAYLOAD_SAVE_BTN_CLICKED";

    private BillItemClickListener billItemClickListener;

    private final LayoutInflater inflater;
    private List<FeedItem> feedItems;
    private Context context;

    public OtherRssAdapter(Context context, BillItemClickListener billItemClickListener) {
        this.context = context;
        this.billItemClickListener = billItemClickListener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OtherRssViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_row_rss, parent, false);
        return new OtherRssViewHolder(view, billItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherRssViewHolder holder, int position) {
        FeedItem item = feedItems.get(position);


        holder.bindView(item);

        /*
         * Get the boolean value of each row and set the drawable as full or empty.
         * 0 not saved is empty. 1 is saved is full.
         */
        AsyncRowTask asyncRowTask = new AsyncRowTask(context.getApplicationContext(), new AsyncResponse() {
            @Override
            public void onPreExecute(int position) {
            }

            @Override
            public void onProgressUpdate(int value) {
            }

            @Override
            public void onPostExecute(int favoriteValueInt) {
                if (favoriteValueInt > 0) {
                    holder.btnSave.setImageResource(R.drawable.ic_favorite_full);
                } else {
                    holder.btnSave.setImageResource(R.drawable.ic_favorite_empty);
                }
            }
        });
        asyncRowTask.execute(item);


// TODO: 10/16/2018 Obviously there's a better way to do this:
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();

                AsyncClickTask.TaskParams taskParams =
                        new AsyncClickTask.TaskParams(adapterPosition, item);

                AsyncClickTask asyncTask = new AsyncClickTask(
                        context.getApplicationContext(),
                        adapterPosition,
                        new AsyncResponse() {
                            @Override
                            public void onPreExecute(int position) {
                                Log.d(TAG, "onPreExecute: position: " + position);
                            }

                            @Override
                            public void onProgressUpdate(int value) {
                            }

                            @Override
                            public void onPostExecute(int value) {
                                Log.d(TAG, "onPostExecute: value: " + value);
                            }
                        });
                asyncTask.execute(taskParams);

                notifyItemChanged(adapterPosition, PAYLOAD_SAVE_BTN_CLICKED);

            }
        });


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

        public OtherRssViewHolder(@NonNull View itemView, final BillItemClickListener listener) {
            super(itemView);
            titleView = itemView.findViewById(R.id.text_title);
            descriptionView = itemView.findViewById(R.id.text_description);
            dateView = itemView.findViewById(R.id.text_date);
            btnSave = itemView.findViewById(R.id.btn_save);
            btnShare = itemView.findViewById(R.id.btn_share);
            btnBrowser = itemView.findViewById(R.id.btn_browser);

            btnBrowser.setOnClickListener(v -> listener.onBrowserBtnClick(feedItem));
            btnShare.setOnClickListener(v -> listener.onShareBtnClick(feedItem, getAdapterPosition()));
//            btnSave.setOnClickListener(v -> { });
        }

        public void bindView(FeedItem feedItem) {
            this.feedItem = feedItem;
            titleView.setText(feedItem.getTitle());
            descriptionView.setText(feedItem.getFormattedDescription());
            dateView.setText(feedItem.getFormattedDate());
        }
    }
}