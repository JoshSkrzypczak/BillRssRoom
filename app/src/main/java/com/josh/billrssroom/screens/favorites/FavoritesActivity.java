package com.josh.billrssroom.screens.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.controllers.BaseActivity;
import com.josh.billrssroom.screens.common.toastshelper.ToastsHelper;
import com.josh.billrssroom.utilities.Utils;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public class FavoritesActivity extends BaseActivity implements FavoriteListViewMvcImpl.Listener,
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String TAG = "FavoritesActivity";

    private FavoriteListViewMvc favoriteViewMvc;

    private FeedViewModel feedViewModel;

    private ToastsHelper toastsHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteViewMvc = getCompositionRoot().getViewMvcFactory().getFavoriteListViewMvc(null);
        toastsHelper = getCompositionRoot().getToastsHelper();

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeUiFavorites(feedViewModel.getFavorites());

        setContentView(favoriteViewMvc.getRootView());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        favoriteViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        favoriteViewMvc.unregisterListener(this);
    }

    private void subscribeUiFavorites(LiveData<List<FeedItem>> liveData) {
        Log.i(TAG, "subscribeUiFavorites: ");
        liveData.observe(this, feedItems -> {
            if (feedItems != null){
                favoriteViewMvc.bindFavoriteItems(feedItems);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setQueryHint("Search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
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
        Log.d(TAG, "onDeleteBtnClicked: " + feedItem.getTitle() + " pos: " + position);
        feedViewModel.removeItemFromFavorites(feedItem);
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        toastsHelper.showShareButtonToast(feedItem, position);
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem) {
        Utils.openCustomTab(FavoritesActivity.this, feedItem.getLink());
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query == null || query.isEmpty()){
            Log.d(TAG, "query == null || query is empty");
            subscribeUiFavorites(feedViewModel.getFavorites());
        } else {
            subscribeUiFavorites(feedViewModel.searchFavorites("*" + query + "*"));
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    private void subscribeSearchFavorites(LiveData<List<FeedItem>> searchFavorites) {
        Log.i(TAG, "subscribeSearchFavorites: ");
        searchFavorites.observe(this, feedItems -> {
            if (feedItems != null){
                favoriteViewMvc.bindSearchFavorites(feedItems);
            }
        });
    }
}