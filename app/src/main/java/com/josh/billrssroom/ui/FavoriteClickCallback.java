package com.josh.billrssroom.ui;

import com.josh.billrssroom.model.BillItem;

public interface FavoriteClickCallback {

    void onBrowserClick(BillItem billItem);

    void onShareClick(BillItem billItem);

    void onTrashClick(BillItem billItem);
}
