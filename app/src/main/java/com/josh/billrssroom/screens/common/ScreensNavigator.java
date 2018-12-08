package com.josh.billrssroom.screens.common;


import android.content.Context;

import com.josh.billrssroom.utilities.Utils;

public class ScreensNavigator {

    private final Context context;

    public ScreensNavigator(Context context) {
        this.context = context;
    }

    public void toOpenChromeBrowser(String url){
        Utils.openCustomTab(context, url);
    }
}

