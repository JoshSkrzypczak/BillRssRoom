package com.josh.billrssroom.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.model.BillModel;

import java.util.List;



public class BillViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<BillModel>> observableBills;

    public BillViewModel(@NonNull Application application) {
        super(application);

        observableBills = new MediatorLiveData<>();

        observableBills.setValue(null);

        LiveData<List<BillModel>> bills = ((BasicApp) application).getRepository().getFeedItems();

        observableBills.addSource(bills, observableBills::setValue);

    }

    public LiveData<List<BillModel>> getAllBills() {
        return observableBills;
    }
}