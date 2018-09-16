package com.josh.billrssroom.ui.favorites;

import com.josh.billrssroom.model.BillItem;

public interface FavoriteClickListener {

    void onBrowserClick(BillItem billItem, int position);

    void onShareClick(BillItem billItem, int position);

    void onTrashClick(BillItem billItem, int position);
}
