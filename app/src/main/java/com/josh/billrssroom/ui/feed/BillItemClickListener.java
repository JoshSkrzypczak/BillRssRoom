package com.josh.billrssroom.ui.feed;

import android.view.View;
import android.widget.ImageButton;

import com.josh.billrssroom.model.FeedItem;

public interface BillItemClickListener {

    void onSaveBtnClick(FeedItem model, int position);

    void onShareBtnClick(FeedItem model, int position);

    void onBrowserBtnClick(FeedItem model);
}
