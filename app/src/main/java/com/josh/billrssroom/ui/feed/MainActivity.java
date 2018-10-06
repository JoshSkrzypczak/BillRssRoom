package com.josh.billrssroom.ui.feed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.josh.billrssroom.R;
import com.josh.billrssroom.api.Resource;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.singlesourceattempt.FeedViewModel;
import com.josh.billrssroom.ui.favorites.FavoritesActivity;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements OtherRssAdapter.OnFeedItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FeedViewModel feedViewModel;
    private RecyclerView recyclerView;
    private OtherRssAdapter otherRssAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview);
        otherRssAdapter = new OtherRssAdapter(this);
        otherRssAdapter.setOnFeedItemClickListener(this);
        recyclerView.setAdapter(otherRssAdapter);
        recyclerView.setItemAnimator(new BillItemAnimator());

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeFeedUi(feedViewModel);
    }

    private void subscribeFeedUi(FeedViewModel feedViewModel) {
        feedViewModel.getObservableFeedItems().observe(this, (Resource<List<FeedItem>> listResource) -> {
            if (listResource != null && listResource.data != null) {
                Log.d(TAG, "subscribeFeedUi: message: " + listResource.message + " status:"  + listResource.status);
                otherRssAdapter.setRssList(listResource.data);
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
    public void onSaveClick(View v, int position, FeedItem item) {
        // TODO: 10/5/2018 Save item to favorites
    }
}