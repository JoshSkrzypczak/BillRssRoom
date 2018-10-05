package com.josh.billrssroom.ui.feed;

import android.widget.ImageButton;

import com.josh.billrssroom.model.FeedItem;

public interface BillItemClickListener {

    void onSaveClicked(FeedItem model, int position);

    void onShareClicked(FeedItem model, int position);

    void onBrowserClicked(FeedItem model);




}
