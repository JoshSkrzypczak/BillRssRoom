package com.josh.billrssroom.ui;

import com.josh.billrssroom.db.entity.BillEntity;
import com.josh.billrssroom.model.BillItem;

public interface BillClickCallback {

    void onBrowserClick(BillItem billItem, int position);

    void onSaveClick(BillItem billEntity, int position);

    void onShareClick(BillItem billItem, int position);
}
