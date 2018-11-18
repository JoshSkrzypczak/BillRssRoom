package com.josh.billrssroom.screens.favorites;

import com.josh.billrssroom.screens.common.views.ObservableViewMvc;
import com.josh.billrssroom.model.FeedItem;

import java.util.List;

/**
 * Override methods called in the Favorites Activity
 */
public interface FavoriteListViewMvc extends ObservableViewMvc<FavoriteListViewMvc.Listener> {

    public interface Listener {
        void onDeleteBtnClicked(FeedItem feedItem, int position);
        void onShareBtnClicked(FeedItem feedItem, int position);
        void onBrowserBtnClicked(FeedItem feedItem);
    }

    void bindFavoriteItems(List<FeedItem> data);
}
