package com.josh.billrssroom.common.dependencyinjection;

import com.josh.billrssroom.api.DataService;
import com.josh.billrssroom.common.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class CompositionRoot {

    private Retrofit retrofit;

    private Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit =  new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public DataService getRssFeedApi() {
        return getRetrofit().create(DataService.class);
    }
}