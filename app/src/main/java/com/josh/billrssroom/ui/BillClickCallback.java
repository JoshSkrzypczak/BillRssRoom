package com.josh.billrssroom.ui;

import com.josh.billrssroom.model.BillItem;

public interface BillClickCallback {

    void onBrowserClick(BillItem billItem);

    void onSaveClick(BillItem billItem);

    void onShareClick(BillItem billItem);
}
