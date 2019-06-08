package com.josh.billrssroom.screens.common.toastshelper;

import android.content.Context;
import android.widget.Toast;

import com.josh.billrssroom.model.FeedItem;

import androidx.lifecycle.LiveData;

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

    public void showFavoriteListCount(Integer feedCount){
        Toast.makeText(context, "Favorite Db Count: " + feedCount, Toast.LENGTH_SHORT).show();
    }

    public void showFeedListCount(Integer feedCount){
        Toast.makeText(context, "Feed Db Count: " + feedCount, Toast.LENGTH_SHORT).show();
    }
}