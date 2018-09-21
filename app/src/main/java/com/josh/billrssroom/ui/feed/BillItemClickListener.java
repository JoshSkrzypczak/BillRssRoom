package com.josh.billrssroom.ui.feed;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.model.RssResult;

public interface BillItemClickListener {

    void onSaveClicked(FeedItem model, int position);

    void onShareClicked(FeedItem model);

    void onBrowserClicked(FeedItem model);
}
