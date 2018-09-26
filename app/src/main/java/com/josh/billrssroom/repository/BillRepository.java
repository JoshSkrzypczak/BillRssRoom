package com.josh.billrssroom.repository;

import android.util.Log;

import com.josh.billrssroom.AppExecutors;
import com.josh.billrssroom.api.ApiResponse;
import com.josh.billrssroom.api.DataService;
import com.josh.billrssroom.api.NetworkBoundResource;
import com.josh.billrssroom.api.Resource;
import com.josh.billrssroom.api.RetrofitClient;
import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.model.RssResult;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillRepository {
    private static BillRepository INSTANCE;
    private final DataService apiService;
    private final BillDatabase billDatabase;
    private MediatorLiveData<List<FeedItem>> observableBills;
    private final AppExecutors appExecutors;


    public BillRepository(final BillDatabase billDatabase, AppExecutors appExecutors) {
        this.billDatabase = billDatabase;
        this.appExecutors = appExecutors;
        observableBills = new MediatorLiveData<>();

        apiService = RetrofitClient.getInstance().getRestApi();
    }

    public LiveData<Resource<List<FeedItem>>> loadBillItems() {
        return new NetworkBoundResource<List<FeedItem>, RssResult>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull RssResult item) {
                billDatabase.billDao().insertData(item.getChannel().getItems());
            }

            @Override
            protected boolean shouldFetch(@androidx.annotation.Nullable List<FeedItem> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<FeedItem>> loadFromDb() {
                return billDatabase.billDao().loadItems();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RssResult>> createCall() {
                return apiService.getMyService();
            }
        }.asLiveData();
    }


    public static BillRepository getInstance(final BillDatabase billDatabase, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (BillRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BillRepository(billDatabase, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    //    public LiveData<List<FeedItem>> getFeedItems() {
//        final MutableLiveData<List<FeedItem>> data = new MutableLiveData<>();
//        apiService.getRssFeed().enqueue(new Callback<RssResult>() {
//            @Override
//            public void onResponse(@NonNull Call<RssResult> call, @NonNull Response<RssResult> response) {
//                if (response.isSuccessful()) {
//                    data.setValue(response.body().getChannel().getItems());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<RssResult> call, @NonNull Throwable t) {
//                t.printStackTrace();
//                Log.e("Error:: ", t.getMessage());
//            }
//        });
//        return data;
//    }
}