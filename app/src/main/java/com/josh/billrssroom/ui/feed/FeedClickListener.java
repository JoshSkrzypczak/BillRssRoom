package com.josh.billrssroom.ui.feed;

import com.josh.billrssroom.model.BillItem;

public interface FeedClickListener {

    void onSaveClicked(BillItem billItem, int position);

    void onShareClicked(BillItem billItem);

    void onBrowserClicked(BillItem billItem);
}
