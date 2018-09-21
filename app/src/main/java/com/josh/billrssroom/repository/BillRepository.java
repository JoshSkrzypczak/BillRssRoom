package com.josh.billrssroom.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.api.DataService;
import com.josh.billrssroom.api.RetrofitClient;
import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.model.RssResult;

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
    private static BillRepository INSTANCE;
    private final DataService apiService;
    private final BillDatabase billDatabase;
    private MediatorLiveData<List<FeedItem>> observableBills;


    public BillRepository(final BillDatabase billDatabase) {
        this.billDatabase = billDatabase;
        observableBills = new MediatorLiveData<>();

        observableBills.addSource(this.billDatabase.billDao().loadItems(), items -> {
            observableBills.postValue(items);
        });

        apiService = RetrofitClient.getInstance().getRestApi();
    }

    public LiveData<List<FeedItem>> getFeedItems() {
        final MutableLiveData<List<FeedItem>> data = new MutableLiveData<>();
        apiService.getRssFeed().enqueue(new Callback<RssResult>() {
            @Override
            public void onResponse(@NonNull Call<RssResult> call, @NonNull Response<RssResult> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body().getChannel().getItems());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RssResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("Error:: ", t.getMessage());
            }
        });
        return data;
    }

    public static BillRepository getInstance(final BillDatabase billDatabase){
        if (INSTANCE == null){
            synchronized (BillRepository.class){
                if (INSTANCE == null){
                    INSTANCE = new BillRepository(billDatabase);
                }
            }
        }
        return INSTANCE;
    }
}