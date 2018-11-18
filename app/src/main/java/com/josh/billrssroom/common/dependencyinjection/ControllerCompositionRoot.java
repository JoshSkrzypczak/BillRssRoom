package com.josh.billrssroom.common.dependencyinjection;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import com.josh.billrssroom.networking.DataService;
import com.josh.billrssroom.screens.common.ViewMvcFactory;

public class ControllerCompositionRoot {

    private final CompositionRoot compositionRoot;
    private Activity activity;


    public ControllerCompositionRoot(CompositionRoot compositionRoot, Activity activity) {
        this.compositionRoot = compositionRoot;
        this.activity = activity;
    }

    private Context getContext(){
        return activity;
    }

    public DataService getRssFeedApi() {
        return compositionRoot.getRssFeedApi();
    }

    private LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(activity);
    }

    public ViewMvcFactory getViewMvcFactory(){ return new ViewMvcFactory(getLayoutInflater()); }
}
