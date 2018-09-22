package com.josh.billrssroom.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.josh.billrssroom.api.DataService;
import com.josh.billrssroom.api.RetrofitClient;
import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.model.RssResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillRepository {
    private static BillRepository INSTANCE;
    private final DataService apiService;
    private final BillDatabase billDatabase;
    private MediatorLiveData<List<FeedItem>> observableBills;


    public BillRepository(final BillDatabase billDatabase) {
        this.billDatabase = billDatabase;
        observableBills = new MediatorLiveData<>();

//        observableBills.addSource(this.billDatabase.billDao().loadFeedItems(), items -> {
//            observableBills.postValue(items);
//        });


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