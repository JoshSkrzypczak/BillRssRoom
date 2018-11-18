package com.josh.billrssroom.screens.feed.feedlistitems;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.views.BaseObservableViewMvc;

public class FeedItemViewMvcImpl extends BaseObservableViewMvc<FeedItemViewMvc.Listener>
        implements FeedItemViewMvc {

    private final TextView titleView;
    private final TextView descriptionView;
    private final TextView dateView;
    public final ImageButton btnSave;
    private final ImageButton btnShare;
    private final ImageButton btnBrowser;

    private FeedItem feedItem;

    private int position;


    public FeedItemViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
      setRootView(inflater.inflate(R.layout.item_row_rss, parent, false));
        titleView = findViewById(R.id.text_title);
        descriptionView = findViewById(R.id.text_description);
        dateView = findViewById(R.id.text_date);
        btnSave = findViewById(R.id.btn_save);
        btnShare = findViewById(R.id.btn_share);
        btnBrowser = findViewById(R.id.btn_browser);

        btnBrowser.setOnClickListener(v -> {
            for (Listener listener : getListeners()){
                listener.onBrowserBtnClicked(feedItem, position);
            }
        });

        btnShare.setOnClickListener(v -> {
            for (Listener listener : getListeners()){
                listener.onShareBtnClicked(feedItem, position);
            }
        });

        btnSave.setOnClickListener(v -> {
            for (Listener listener : getListeners()){
                listener.onSaveBtnClicked(feedItem, position);
            }
        });
    }

    @Override
    public void bindItem(FeedItem feedItem, int position) {
        this.feedItem = feedItem;
        this.position = position;
        titleView.setText(feedItem.getTitle());
        descriptionView.setText(feedItem.getFormattedDescription());
        dateView.setText(feedItem.getFormattedDate());
    }

    @Override
    public void setImageResource(int drawable) {
        btnSave.setImageResource(drawable);
    }
}
