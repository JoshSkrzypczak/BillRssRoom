package com.josh.billrssroom.ui.feed;

import com.josh.billrssroom.model.BillModel;

public interface BillItemClickListener {

    void onSaveClicked(BillModel billModel, int position);

    void onShareClicked(BillModel billModel);

    void onBrowserClicked(BillModel billModel);
}
