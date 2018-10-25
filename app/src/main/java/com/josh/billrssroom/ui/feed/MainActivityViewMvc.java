package com.josh.billrssroom.ui.feed;

import android.view.View;

import com.josh.billrssroom.model.FeedItem;

import java.util.List;

interface MainActivityViewMvc {

    public interface Listener {
        void onSaveBtnClicked(FeedItem model, int position);
        void onShareBtnClicked(FeedItem feedItem, int position);
        void onBrowserBtnClicked(FeedItem feedItem);
    }

    void registerListener(Listener listener);

    void unregisterListener(Listener listener);

    View getRootView();

    void bindFeedItems(List<FeedItem> data);
}
