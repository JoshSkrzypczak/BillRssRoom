package com.josh.billrssroom.common.dependencyinjection;

import android.content.Context;
import android.view.LayoutInflater;

import com.josh.billrssroom.networking.DataService;
import com.josh.billrssroom.screens.common.ScreensNavigator;
import com.josh.billrssroom.screens.common.controllers.BackPressDispatcher;
import com.josh.billrssroom.screens.common.fragmentframehelper.FragmentFrameHelper;
import com.josh.billrssroom.screens.common.fragmentframehelper.FragmentFrameWrapper;
import com.josh.billrssroom.screens.common.toastshelper.ToastsHelper;
import com.josh.billrssroom.screens.common.ViewMvcFactory;
import com.josh.billrssroom.screens.singleactivity.FeedListController;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class ControllerCompositionRoot {

    private final CompositionRoot compositionRoot;
    private final FragmentActivity activity;


    public ControllerCompositionRoot(CompositionRoot compositionRoot, FragmentActivity activity) {
        this.compositionRoot = compositionRoot;
        this.activity = activity;
    }

//    public ControllerCompositionRoot(CompositionRoot compositionRoot, Activity activity) {
//        this.compositionRoot = compositionRoot;
//        this.activity = activity;
//    }

    private FragmentActivity getFragmentActivity(){
        return activity;
    }

    private Context getContext(){
        return activity;
    }

    private FragmentManager getFragmentManager(){
       return getFragmentActivity().getSupportFragmentManager();
    }

    public DataService getRssFeedApi() {
        return compositionRoot.getRssFeedApi();
    }

    private LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(activity);
    }

    public ViewMvcFactory getViewMvcFactory(){ return new ViewMvcFactory(getLayoutInflater()); }

    public FeedListController getFeedListController(){
        return new FeedListController(
                getScreensNavigator(),
                getToastsHelper(),
                getBackPressDispatcher());
    }

    public ScreensNavigator getScreensNavigator(){
        return new ScreensNavigator(getFragmentFrameHelper(), getContext());
    }

    private FragmentFrameHelper getFragmentFrameHelper() {
        return new FragmentFrameHelper(getFragmentActivity(), getFragmentFrameWrapper(), getFragmentManager());
    }

    private FragmentFrameWrapper getFragmentFrameWrapper() {
        return (FragmentFrameWrapper)getFragmentActivity();
    }

    public ToastsHelper getToastsHelper(){
        return new ToastsHelper(getContext());
    }

    public BackPressDispatcher getBackPressDispatcher() {
        return (BackPressDispatcher)getFragmentActivity();
    }
}