package com.josh.billrssroom.screens.feed;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.views.ObservableViewMvc;

import java.util.List;

public interface FeedListViewMvc extends ObservableViewMvc<FeedListViewMvc.Listener> {


    void setFeedItemList(List<FeedItem> feedItems);

    public interface Listener {
        void onShareBtnClicked(FeedItem feedItem, int position);

        void onBrowserBtnClicked(FeedItem feedItem, int position);

        void onSaveBtnClicked(FeedItem feedItem, int position);
    }

    void bindFeedItems(List<FeedItem> data);

    int getFeedCount();
}
