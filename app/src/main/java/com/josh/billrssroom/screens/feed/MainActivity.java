package com.josh.billrssroom.screens.feed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.networking.Resource;
import com.josh.billrssroom.screens.common.controllers.BaseActivity;
import com.josh.billrssroom.screens.common.toastshelper.ToastsHelper;
import com.josh.billrssroom.screens.favorites.FavoritesActivity;
import com.josh.billrssroom.utilities.AsyncClickTask;
import com.josh.billrssroom.utilities.AsyncResponse;
import com.josh.billrssroom.utilities.Utils;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends BaseActivity implements FeedListViewMvcImpl.Listener,
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String TAG = "MainActivity";

    private FeedListViewMvc viewMvc;

    private FeedViewModel feedViewModel;

    private ToastsHelper toastsHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        viewMvc = getCompositionRoot().getViewMvcFactory().getFeedListViewMvc(null);
        toastsHelper = getCompositionRoot().getToastsHelper();
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeFeedUi(feedViewModel);

        setContentView(viewMvc.getRootView());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart: ");
        super.onStart();
        viewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
        viewMvc.unregisterListener(this);
    }

    private void subscribeFeedUi(FeedViewModel feedViewModel) {
        Log.d(TAG, "subscribeFeedUi: ");
        feedViewModel.getLiveDataFeedItems().observe(this, listResource -> {
            if (listResource != null && listResource.data != null) {
                viewMvc.bindFeedItems(listResource.data);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setQueryHint("Search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                return true;
            // TODO: 11/29/2018 Remove later
            case R.id.action_get_count:
                int feedCount = viewMvc.getFeedCount();
                toastsHelper.showListCountToast(feedCount);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        toastsHelper.showShareButtonToast(feedItem, position);
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem, int position) {
        Utils.openCustomTab(MainActivity.this, feedItem.getLink());
    }

    @Override
    public void onSaveBtnClicked(FeedItem feedItem, int position) {
        Log.d(TAG, "onSaveBtnClicked: " + feedItem.getTitle() + " pos: " + position);
        // TODO: 11/17/2018 Animate btn onClick
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public boolean onClose() {
        return false;
    }
}