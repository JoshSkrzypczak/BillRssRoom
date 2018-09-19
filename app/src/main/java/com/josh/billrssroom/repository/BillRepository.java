package com.josh.billrssroom.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.josh.billrssroom.api.DataService;
import com.josh.billrssroom.api.RetrofitClient;
import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.model.BillModel;
import com.josh.billrssroom.model.Rss;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository modules handle data operations.
 * They provide a clean API so that the rest of the app can retrieve this data easily.
 * They know where to get the data from and what API calls to make when data is updated.
 * You can consider repositories to be mediators between different data sources, such as persistent
 * models, web services, and caches.
 */
public class BillRepository {

    private final DataService apiService;

    private static BillRepository sInstance;
    private final BillDatabase billDatabase;
    private MediatorLiveData<List<BillModel>> observableBills;


    public BillRepository(final BillDatabase billDatabase) {
        this.billDatabase = billDatabase;
        observableBills = new MediatorLiveData<>();

        observableBills.addSource(this.billDatabase.billDao().loadBillItems(), billItems -> {
            observableBills.postValue(billItems);
        });

        apiService = RetrofitClient.getInstance().getRestApi();
    }

    public LiveData<List<BillModel>> getFeedItems() {
        final MutableLiveData<List<BillModel>> data = new MutableLiveData<>();
        apiService.getFeedItems().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(@NonNull Call<Rss> call, @NonNull Response<Rss> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body().getChannel().getBillModels());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Rss> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("Error:: ", t.getMessage());
            }
        });
        return data;
    }

    public static BillRepository getInstance(final BillDatabase billDatabase){
        if (sInstance == null){
            synchronized (BillRepository.class){
                if (sInstance == null){
                    sInstance = new BillRepository(billDatabase);
                }
            }
        }
        return sInstance;
    }
}