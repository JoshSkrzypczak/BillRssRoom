package com.josh.billrssroom.ui.feed;

import android.view.View;
import android.widget.ImageButton;

import com.josh.billrssroom.model.FeedItem;

public interface BillItemClickListener {

    void onSaveClicked(View view, FeedItem model, int position);

    void onShareClicked(FeedItem model, int position);

    void onBrowserClicked(FeedItem model);
}
