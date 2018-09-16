package com.josh.billrssroom.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.josh.billrssroom.db.FeedDatabase;
import com.josh.billrssroom.model.BillItem;
import com.josh.billrssroom.model.Rss;
import com.josh.billrssroom.api.DataService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class FeedRepository {

    private final DataService apiService;

    private static FeedRepository sInstance;
    private final FeedDatabase feedDatabase;
    private MediatorLiveData<List<BillItem>> observableBills;


    public FeedRepository(final FeedDatabase feedDatabase) {
        this.feedDatabase = feedDatabase;
        observableBills = new MediatorLiveData<>();

        observableBills.addSource(this.feedDatabase.billDao().loadBillItems(), billItems -> {
            observableBills.postValue(billItems);
        });

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DataService.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(httpClient.build())
                .build();

        apiService = retrofit.create(DataService.class);
    }

    public LiveData<List<BillItem>> getFeedItems() {
        final MutableLiveData<List<BillItem>> data = new MutableLiveData<>();
        apiService.getBillItemsNormally().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(@NonNull Call<Rss> call, @NonNull Response<Rss> response) {
                data.setValue(response.body().getChannel().getBillItems());


            }

            @Override
            public void onFailure(@NonNull Call<Rss> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("Error:: ", t.getMessage());
            }
        });
        return data;
    }

    public static FeedRepository getInstance(final FeedDatabase feedDatabase){
        if (sInstance == null){
            synchronized (FeedRepository.class){
                if (sInstance == null){
                    sInstance = new FeedRepository(feedDatabase);
                }
            }
        }
        return sInstance;
    }
}