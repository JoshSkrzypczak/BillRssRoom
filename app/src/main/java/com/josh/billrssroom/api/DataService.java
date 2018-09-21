package com.josh.billrssroom.api;

import android.arch.lifecycle.LiveData;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.model.RssResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("documents/publications/RssFeeds/billupdate.xml")
    Call<RssResult> getRssFeed();
}
