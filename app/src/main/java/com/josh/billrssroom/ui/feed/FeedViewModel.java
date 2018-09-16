package com.josh.billrssroom.ui.feed;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.repository.FeedRepository;
import com.josh.billrssroom.model.BillItem;

import java.util.List;

public class FeedViewModel extends AndroidViewModel {


    private final MediatorLiveData<List<BillItem>> observableBills;


    public FeedViewModel(@NonNull Application application) {
        super(application);

        observableBills = new MediatorLiveData<>();

        observableBills.setValue(null);

        LiveData<List<BillItem>> bills = ((BasicApp) application).getRepository().getFeedItems();

        observableBills.addSource(bills, observableBills::setValue);
    }

    public LiveData<List<BillItem>> getAllBills() {
        return observableBills;
    }
}