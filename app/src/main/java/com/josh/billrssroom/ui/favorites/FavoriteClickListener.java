package com.josh.billrssroom.ui.favorites;

import com.josh.billrssroom.model.BillModel;

public interface FavoriteClickListener {

    void onBrowserClick(BillModel billModel, int position);

    void onShareClick(BillModel billModel, int position);

    void onTrashClick(BillModel billModel, int position);
}
