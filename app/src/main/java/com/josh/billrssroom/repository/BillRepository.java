package com.josh.billrssroom.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.model.BillModel;
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

    public LiveData<List<BillModel>> getFeedItems() {
        final MutableLiveData<List<BillModel>> data = new MutableLiveData<>();
        apiService.getBillItemsNormally().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(@NonNull Call<Rss> call, @NonNull Response<Rss> response) {
                data.setValue(response.body().getChannel().getBillModels());


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