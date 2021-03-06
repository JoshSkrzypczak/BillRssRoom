package com.josh.billrssroom.screens.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.josh.billrssroom.screens.common.toolbar.ToolbarViewMvc;
import com.josh.billrssroom.screens.favorites.favoritelistitems.FavoriteItemViewMvc;
import com.josh.billrssroom.screens.favorites.favoritelistitems.FavoriteItemViewMvcImpl;
import com.josh.billrssroom.screens.favorites.FavoriteListViewMvc;
import com.josh.billrssroom.screens.favorites.FavoriteListViewMvcImpl;
import com.josh.billrssroom.screens.feed.feedlistitems.FeedItemViewMvc;
import com.josh.billrssroom.screens.feed.feedlistitems.FeedItemViewMvcImpl;
import com.josh.billrssroom.screens.feed.FeedListViewMvc;
import com.josh.billrssroom.screens.feed.FeedListViewMvcImpl;

import androidx.annotation.Nullable;

public class ViewMvcFactory {

    private final LayoutInflater layoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public FeedListViewMvc getFeedListViewMvc(@Nullable ViewGroup parent){
        return new FeedListViewMvcImpl(layoutInflater, parent, this);
    }

    public FeedItemViewMvc getFeedItemViewMvc(@Nullable ViewGroup parent){
        return new FeedItemViewMvcImpl(layoutInflater, parent);
    }

    public FavoriteListViewMvc getFavoriteListViewMvc(@Nullable ViewGroup parent){
        return new FavoriteListViewMvcImpl(layoutInflater, parent, this);
    }

    public FavoriteItemViewMvc getFavoriteItemViewMvc(@Nullable ViewGroup parent){
        return new FavoriteItemViewMvcImpl(layoutInflater, parent);
    }

//    public ToolbarViewMvc getToolbarViewMvc(@Nullable ViewGroup parent) {
//        return new ToolbarViewMvc(layoutInflater, parent);
//    }

}
