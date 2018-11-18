package com.josh.billrssroom.screens.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.controllers.BaseActivity;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class FavoritesActivity extends BaseActivity implements FavoriteListViewMvcImpl.Listener {

    private static final String TAG = FavoritesActivity.class.getSimpleName();

    private FavoriteListViewMvc favoriteViewMvc;

    private FeedViewModel feedViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favoriteViewMvc = getCompositionRoot().getViewMvcFactory().getFavoriteListViewMvc(null);
        favoriteViewMvc.registerListener(this);

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeUiFavorites(feedViewModel);

        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void subscribeUiFavorites(FeedViewModel feedViewModel) {
        feedViewModel.getFavoriteFeedItems().observe(this, feedItems -> {
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
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDeleteBtnClicked(FeedItem feedItem, int position) {
        feedViewModel.removeItemFromFavorites(feedItem);
//        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        Log.d(TAG, "onShareBtnClick: " + feedItem.getTitle() + " " + position);
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem) {
        Log.d(TAG, "onBrowserBtnClick: " + feedItem.getTitle());
    }
}