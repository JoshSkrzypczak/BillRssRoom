package com.josh.billrssroom.network;

import com.josh.billrssroom.model.BillItem;
import com.josh.billrssroom.model.Channel;
import com.josh.billrssroom.model.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    String BASE_URL = "http://www.legislature.mi.gov/";

    @GET("documents/publications/RssFeeds/billupdate.xml")
    Call<Rss> getBillItems();
}
