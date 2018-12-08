package com.josh.billrssroom.screens.common;


import android.content.Context;

import com.josh.billrssroom.screens.common.fragmentframehelper.FragmentFrameHelper;
import com.josh.billrssroom.screens.singleactivity.FavoritesFragment;
import com.josh.billrssroom.screens.singleactivity.FeedListFragment;
import com.josh.billrssroom.utilities.Utils;

public class ScreensNavigator {

    private FragmentFrameHelper fragmentFrameHelper;

    private final Context context;

    public ScreensNavigator(FragmentFrameHelper fragmentFrameHelper,
                            Context context) {
        this.context = context;
        this.fragmentFrameHelper = fragmentFrameHelper;

    }

    public void toOpenChromeBrowser(String url){
        Utils.openCustomTab(context, url);
    }

    public void toFavorites(){
        fragmentFrameHelper.replaceFragment(FavoritesFragment.newInstance());
    }

    public void toFeedList(){
        fragmentFrameHelper.replaceFragmentAndClearBackstack(FeedListFragment.newInstance());
    }


    public void navigateUp(){
        fragmentFrameHelper.navigateUp();
    }
}

