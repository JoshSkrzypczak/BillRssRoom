package com.josh.billrssroom.screens.singleactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.ScreensNavigator;
import com.josh.billrssroom.screens.common.controllers.BackPressDispatcher;
import com.josh.billrssroom.screens.common.controllers.BackPressedListener;
import com.josh.billrssroom.screens.common.controllers.BaseFragment;
import com.josh.billrssroom.screens.common.toastshelper.ToastsHelper;
import com.josh.billrssroom.screens.favorites.FavoriteListViewMvc;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public class FavoritesFragment extends BaseFragment implements
        FavoriteListViewMvc.Listener, BackPressedListener,
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    private FavoriteListViewMvc favoriteViewMvc;

    private FeedViewModel feedViewModel;

    private ToastsHelper toastsHelper;
    private ScreensNavigator screensNavigator;
    private BackPressDispatcher backPressDispatcher;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        favoriteViewMvc = getCompositionRoot().getViewMvcFactory().getFavoriteListViewMvc(container);
        screensNavigator = getCompositionRoot().getScreensNavigator();
        toastsHelper = getCompositionRoot().getToastsHelper();
        backPressDispatcher = getCompositionRoot().getBackPressDispatcher();

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeUiFavorites(feedViewModel.getFavorites());

        return favoriteViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        favoriteViewMvc.registerListener(this);
        backPressDispatcher.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        favoriteViewMvc.unregisterListener(this);
        backPressDispatcher.unregisterListener(this);
    }

    private void subscribeUiFavorites(LiveData<List<FeedItem>> liveData) {
        liveData.observe(this, feedItems -> {
            if (feedItems != null) {
                favoriteViewMvc.bindFavoriteItems(feedItems);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorites, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setQueryHint("Search");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                screensNavigator.toFeedList();
                return true;
            case R.id.clear_data:
                toastsHelper.showClearingFavoritesToast();
                feedViewModel.deleteAllFavorites();
                return true;
            case R.id.action_get_count:
                int favoriteCount = favoriteViewMvc.getFavoriteCount();
                toastsHelper.showListCountToast(favoriteCount);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDeleteBtnClicked(FeedItem feedItem, int position) {
        feedViewModel.removeItemFromFavorites(feedItem);
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        toastsHelper.showShareButtonToast(feedItem, position);
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem) {
        screensNavigator.toOpenChromeBrowser(feedItem.getLink());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query == null || query.isEmpty()) {
            subscribeUiFavorites(feedViewModel.getFavorites());
        } else {
            subscribeUiFavorites(feedViewModel.searchFavorites("*" + query + "*"));
        }
        return false;
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
