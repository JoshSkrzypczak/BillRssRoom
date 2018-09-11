package com.josh.billrssroom.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.josh.billrssroom.FeedRepository;
import com.josh.billrssroom.model.BillItem;

import java.util.List;

public class BillViewModel extends ViewModel {

    public FeedRepository repository;

    public LiveData<List<BillItem>> allBills;


    public BillViewModel () {
        super();
        repository = new FeedRepository();
        allBills = repository.getBills();
    }

    public LiveData<List<BillItem>> getAllBills() {
        return allBills;
    }
}
