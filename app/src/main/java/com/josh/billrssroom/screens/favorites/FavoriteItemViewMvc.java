package com.josh.billrssroom.screens.favorites;

import com.josh.billrssroom.screens.common.ObservableViewMvc;
import com.josh.billrssroom.model.FeedItem;

public interface FavoriteItemViewMvc extends ObservableViewMvc<FavoriteItemViewMvc.Listener> {

    public interface Listener {
        void onDeleteBtnClicked(FeedItem feedItem, int position);
        void onShareBtnClicked(FeedItem feedItem, int position);
        void onBrowserBtnClicked(FeedItem feedItem);
    }

    void bindItem(FeedItem feedItem, int position);

}