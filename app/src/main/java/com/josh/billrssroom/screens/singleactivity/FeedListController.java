package com.josh.billrssroom.screens.singleactivity;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.ScreensNavigator;
import com.josh.billrssroom.screens.common.controllers.BackPressDispatcher;
import com.josh.billrssroom.screens.common.controllers.BackPressedListener;
import com.josh.billrssroom.screens.common.toastshelper.ToastsHelper;
import com.josh.billrssroom.screens.feed.FeedListViewMvc;

public class FeedListController implements FeedListViewMvc.Listener, BackPressedListener {

    private final ScreensNavigator screensNavigator;
    private final ToastsHelper toastsHelper;
    private final BackPressDispatcher backPressDispatcher;

    private FeedListViewMvc viewMvc;

    public FeedListController(ScreensNavigator screensNavigator,
                              ToastsHelper toastsHelper,
                              BackPressDispatcher backPressDispatcher) {
        this.screensNavigator = screensNavigator;
        this.toastsHelper = toastsHelper;
        this.backPressDispatcher = backPressDispatcher;
    }

    public void bindView(FeedListViewMvc viewMvc) {
        this.viewMvc = viewMvc;
    }

    public void onStart() {
        viewMvc.registerListener(this);
        backPressDispatcher.registerListener(this);
    }

    public void onStop() {
        viewMvc.unregisterListener(this);
        backPressDispatcher.unregisterListener(this);
    }


    public void onCountMenuBtnClicked(int feedCount) {
        toastsHelper.showListCountToast(feedCount);
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        toastsHelper.showShareButtonToast(feedItem, position);
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem, int position) {
        screensNavigator.toOpenChromeBrowser(feedItem.getLink());
    }

    @Override
    public void onSaveBtnClicked(FeedItem feedItem, int position) {

    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}