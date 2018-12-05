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

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends BaseActivity implements FeedListViewMvcImpl.Listener,
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private FeedListViewMvc viewMvc;

    private FeedViewModel feedViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMvc = getCompositionRoot().getViewMvcFactory().getFeedListViewMvc(null);

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeFeedUi(feedViewModel.getLiveDataFeedItems());

        setContentView(viewMvc.getRootView());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    private void subscribeFeedUi(LiveData<Resource<List<FeedItem>>> liveData) {
        liveData.observe(this, new Observer<Resource<List<FeedItem>>>() {
            @Override
            public void onChanged(Resource<List<FeedItem>> listResource) {
                if (listResource.data != null) {
                    viewMvc.setFeedItemList(listResource.data);
                }
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
            // TODO: 11/29/2018 Remove counting menu item in future. Using only for DB troubleshooting
            case R.id.action_get_count:
                int feedCount = viewMvc.getFeedCount();
                Toast.makeText(this, "Count: " + feedCount, Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        Toast.makeText(this, "TODO: Implement Share: " +
                feedItem.getTitle() +
                " position: " +
                position, Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query == null || query.isEmpty()){
            subscribeFeedUi(feedViewModel.getLiveDataFeedItems());
        } else {
            subscribeSearchFeedUi(feedViewModel.searchFeedItems("*" + query + "*"));
        }
        return false;
    }

    @Override
    public boolean onClose() {
        return false;
    }

    private void subscribeSearchFeedUi(LiveData<List<FeedItem>> liveData) {
        liveData.observe(this, new Observer<List<FeedItem>>() {
            @Override
            public void onChanged(List<FeedItem> feedItems) {
                if (feedItems != null){
                    viewMvc.setFeedItemList(feedItems);
                }
            }
        });
    }
}