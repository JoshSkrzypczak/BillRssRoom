package com.josh.billrssroom.ui.favorites;

import com.josh.billrssroom.model.FeedItem;

public interface FavoriteClickListener {

    void onBrowserClick(FeedItem model, int position);

    void onShareClick(FeedItem model, int position);

    void onTrashClick(FeedItem model, int position);
}
