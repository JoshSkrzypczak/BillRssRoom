package com.josh.billrssroom.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.model.FeedItem;

import java.util.List;


/**
 * The class that prepares the data for viewing in the MainActivity and reacts to user interactions
 */
public class BillViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<FeedItem>> observableBills;

    public BillViewModel(@NonNull Application application) {
        super(application);

        observableBills = new MediatorLiveData<>();

        observableBills.setValue(null);

        LiveData<List<FeedItem>> items = ((BasicApp) application).getRepository().getFeedItems();

        observableBills.addSource(items, observableBills::setValue);
    }

    public LiveData<List<FeedItem>> getAllBills() {
        return observableBills;
    }
}