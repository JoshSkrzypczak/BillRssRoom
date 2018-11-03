package com.josh.billrssroom.screens.feed;

import com.josh.billrssroom.model.FeedItem;

public interface BillItemClickListener {

    void onSaveBtnClick(FeedItem model, int position);

    void onShareBtnClick(FeedItem model, int position);

    void onBrowserBtnClick(FeedItem model);
}
