package com.josh.billrssroom.screens.favorites;

import com.josh.billrssroom.model.FeedItem;

public interface FavoriteClickListener {

    void onBrowserBtnClick(FeedItem model, int position);

    void onShareBtnClick(FeedItem model, int position);

    void onTrashBtnClick(FeedItem model, int position);
}
