package com.josh.billrssroom.api;

import androidx.lifecycle.LiveData;

import com.josh.billrssroom.model.RssResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("documents/publications/RssFeeds/billupdate.xml")
    Call<RssResult> getRssFeed();

    @GET("documents/publications/RssFeeds/billupdate.xml")
    LiveData<ApiResponse<RssResult>> getMyService();
}
