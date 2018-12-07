package com.josh.billrssroom.screens.favorites;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.controllers.BaseActivity;
import com.josh.billrssroom.utilities.Utils;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class FavoritesActivity extends BaseActivity implements FavoriteListViewMvcImpl.Listener {

    private FavoriteListViewMvc favoriteViewMvc;

    private FeedViewModel feedViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteViewMvc = getCompositionRoot().getViewMvcFactory().getFavoriteListViewMvc(null);

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeUiFavorites(feedViewModel);

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

    private void subscribeUiFavorites(FeedViewModel feedViewModel) {
        feedViewModel.getFavorites().observe(this, feedItems -> {
            if (feedItems != null){
                favoriteViewMvc.bindFavoriteItems(feedItems);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.clear_data:
                Toast.makeText(this, "Clearing favorites...", Toast.LENGTH_SHORT).show();
                feedViewModel.deleteAllFavorites();
                return true;
            case R.id.action_get_count:
                int favoriteCount = favoriteViewMvc.getFavoriteCount();
                Toast.makeText(this, "Count: " + favoriteCount, Toast.LENGTH_SHORT).show();
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
        // TODO: 11/17/2018 Implement Share Btn Clicked
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem) {
        Utils.openCustomTab(FavoritesActivity.this, feedItem.getLink());
    }
}