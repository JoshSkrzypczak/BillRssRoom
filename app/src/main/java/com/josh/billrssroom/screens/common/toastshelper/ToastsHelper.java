package com.josh.billrssroom.screens.common.toastshelper;

import android.content.Context;
import android.widget.Toast;

import com.josh.billrssroom.model.FeedItem;

public class ToastsHelper {

    private final Context context;

    public ToastsHelper(Context context) {
        this.context = context;
    }

    public void showShareButtonToast(FeedItem feedItem, int position) {
        Toast.makeText(context, "TODO: Implement Share: "
                + feedItem.getTitle()
                + " position: "
                + position, Toast.LENGTH_SHORT).show();
    }

    public void showListCountToast(int feedCount){
        Toast.makeText(context, "Count: " + feedCount, Toast.LENGTH_SHORT).show();
    }

    public void showClearingFavoritesToast() {
        Toast.makeText(context, "Clearing favorites...", Toast.LENGTH_SHORT).show();
    }
}