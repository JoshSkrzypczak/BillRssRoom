package com.josh.billrssroom.ui.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.singlesourceattempt.FeedViewModel;
import com.josh.billrssroom.ui.favorites.FavoritesActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements OtherRssAdapter.OnFeedItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String PAYLOAD_SAVE_BTN_CLICKED = "PAYLOAD_SAVE_BTN_CLICKED";

    private FeedViewModel feedViewModel;
    private RssAdapter adapter;
    private RecyclerView recyclerView;
    private OtherRssAdapter otherRssAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        recyclerView = findViewById(R.id.recyclerview);
//        adapter = new RssAdapter(this, this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setItemAnimator(new BillItemAnimator());


        recyclerView = findViewById(R.id.recyclerview);
        otherRssAdapter = new OtherRssAdapter(this);
        otherRssAdapter.setOnFeedItemClickListener(this);
        recyclerView.setAdapter(otherRssAdapter);
        recyclerView.setItemAnimator(new BillItemAnimator());


        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);


        subscribeFeedUi(feedViewModel);
    }

    private void subscribeFeedUi(FeedViewModel feedViewModel) {
        feedViewModel.getObservableFeedItems().observe(this, listResource -> {
            if (listResource != null && listResource.data != null) {
                otherRssAdapter.setRssList(listResource.data);
                recyclerView.invalidate();
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

        feedViewModel.updateFeedItemAsFavorite(item);

    }
}