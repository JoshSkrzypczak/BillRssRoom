package com.josh.billrssroom.screens.feed.feedlistitems;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.views.ObservableViewMvc;

/**
 * Represents a single list item
 */
public interface FeedItemViewMvc extends ObservableViewMvc<FeedItemViewMvc.Listener> {

    public interface Listener {
        void onShareBtnClicked(FeedItem feedItem, int position);
        void onBrowserBtnClicked(FeedItem feedItem, int position);
        void onSaveBtnClicked(FeedItem feedItem, int position);
    }

    void bindItem(FeedItem feedItem, int position);

    void setImageResource(int drawable);
}
