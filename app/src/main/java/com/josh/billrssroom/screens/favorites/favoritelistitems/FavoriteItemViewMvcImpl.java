package com.josh.billrssroom.screens.favorites.favoritelistitems;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.views.BaseObservableViewMvc;

public class FavoriteItemViewMvcImpl extends BaseObservableViewMvc<FavoriteItemViewMvc.Listener>
        implements FavoriteItemViewMvc {

    private final TextView titleView;
    private final TextView descriptionView;
    private final TextView dateView;
    private final ImageButton btnDelete;
    private final ImageButton btnShare;
    private final ImageButton btnBrowser;

    private FeedItem feedItem;
    private int position;


    public FavoriteItemViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.item_row_favorites, parent, false));
        titleView = findViewById(R.id.text_title);
        descriptionView = findViewById(R.id.text_description);
        dateView = findViewById(R.id.text_date);
        btnDelete = findViewById(R.id.btn_trash);
        btnShare = findViewById(R.id.btn_share);
        btnBrowser = findViewById(R.id.btn_browser);

        btnBrowser.setOnClickListener(v -> {
            for (Listener listener : getListeners()){
                listener.onBrowserBtnClicked(feedItem);
            }
        });

        btnShare.setOnClickListener(v -> {
            for (Listener listener : getListeners()){
                listener.onShareBtnClicked(feedItem, position);
            }
        });

        btnDelete.setOnClickListener(v -> {
            for (Listener listener : getListeners()){
                listener.onDeleteBtnClicked(feedItem, position);
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
}