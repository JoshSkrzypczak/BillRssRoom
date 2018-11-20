package com.josh.billrssroom.screens.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.networking.Resource;
import com.josh.billrssroom.screens.common.controllers.BaseActivity;
import com.josh.billrssroom.screens.favorites.FavoritesActivity;
import com.josh.billrssroom.utilities.AsyncClickTask;
import com.josh.billrssroom.utilities.AsyncResponse;
import com.josh.billrssroom.utilities.Utils;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends BaseActivity implements FeedListViewMvcImpl.Listener {

    private FeedListViewMvc viewMvc;

    private FeedViewModel feedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMvc = getCompositionRoot().getViewMvcFactory().getFeedListViewMvc(null);

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeFeedUi(feedViewModel);

        setContentView(viewMvc.getRootView());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewMvc.unregisterListener(this);
    }

    private void subscribeFeedUi(FeedViewModel feedViewModel) {
        feedViewModel.getObservableFeedItems().observe(this, (Resource<List<FeedItem>> listResource) -> {
            if (listResource != null && listResource.data != null) {
                viewMvc.bindFeedItems(listResource.data);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        Toast.makeText(this, "TODO: Implement Share: " + feedItem.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem, int position) {
        Utils.openCustomTab(MainActivity.this, feedItem.getLink());
    }

    @Override
    public void onSaveBtnClicked(FeedItem feedItem, int position) {

        // TODO: 11/17/2018 Animate btn onClick
        // TODO: 11/17/2018 No ripple effect onClick

        AsyncClickTask.TaskParams taskParams =
                new AsyncClickTask.TaskParams(position, feedItem);

        AsyncClickTask asyncTask = new AsyncClickTask(
                this,
                position,
                new AsyncResponse() {
                    @Override
                    public void onPreExecute(int position1) {
                    }

                    @Override
                    public void onProgressUpdate(int value) {
                    }

                    @Override
                    public void onPostExecute(int value) {
                    }
                });
        asyncTask.execute(taskParams);
    }
}