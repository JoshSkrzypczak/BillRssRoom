package com.josh.billrssroom.ui.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesActivity extends AppCompatActivity implements FavoriteClickListener {

    private static final String TAG = FavoritesActivity.class.getSimpleName();

    private FeedViewModel feedViewModel;
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.favorites_list);
        adapter = new FavoritesAdapter(this,this);
        recyclerView.setAdapter(adapter);

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeUiFavorites(feedViewModel);

    }

    private void subscribeUiFavorites(FeedViewModel feedViewModel) {
        feedViewModel.getFavoriteFeedItems().observe(this, new Observer<List<FeedItem>>() {
            @Override
            public void onChanged(List<FeedItem> feedItems) {
                if (feedItems != null){
                    adapter.setFavoritesList(feedItems);
                }
            }
        });
    }

    @Override
    public void onBrowserBtnClick(FeedItem model, int position) {
        Log.d(TAG, "onBrowserBtnClick: " + model.getTitle());
    }

    @Override
    public void onShareBtnClick(FeedItem model, int position) {
        Log.d(TAG, "onShareBtnClick: " + model.getTitle());
    }

    @Override
    public void onTrashBtnClick(FeedItem model, int position) {
        feedViewModel.removeItemFromFavorites(model);
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
}