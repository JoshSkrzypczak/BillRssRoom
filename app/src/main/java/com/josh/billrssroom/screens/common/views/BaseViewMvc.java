package com.josh.billrssroom.screens.common.views;

import android.content.Context;
import android.view.View;

import androidx.annotation.StringRes;

public abstract class BaseViewMvc implements ViewMvc {

    private View rootView;

    @Override
    public View getRootView() {
        return rootView;
    }

    protected void setRootView(View rootView) {
        this.rootView = rootView;
    }

    protected  <T extends View> T findViewById(int id) {
        return getRootView().findViewById(id);
    }

    protected Context getContext() {
        return getRootView().getContext();
    }

    protected String getString(@StringRes int id){
        return getContext().getString(id);
    }
}