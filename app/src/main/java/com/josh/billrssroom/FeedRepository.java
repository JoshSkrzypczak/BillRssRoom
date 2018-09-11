package com.josh.billrssroom;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.josh.billrssroom.db.AppDatabase;
import com.josh.billrssroom.model.BillItem;
import com.josh.billrssroom.model.Rss;
import com.josh.billrssroom.network.DataService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FeedRepository {
    public static final String TAG = FeedRepository.class.getSimpleName();

    private DataService apiService;

    private static class SingletonHelper {
        private static final FeedRepository INSTANCE = new FeedRepository();
    }

    public static FeedRepository getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public FeedRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DataService.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        apiService = retrofit.create(DataService.class);
    }

    public LiveData<List<BillItem>> getBills() {
        final MutableLiveData<List<BillItem>> data = new MutableLiveData<>();

        apiService.getBillItems().enqueue(new Callback<Rss>() {
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
}
