package com.josh.billrssroom.ui.favorites;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.singlesourceattempt.FeedViewModel;

import java.util.List;

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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.favorites_list);

        adapter = new FavoritesAdapter(this, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeFavUi(feedViewModel);

    }

    private void subscribeFavUi(FeedViewModel feedViewModel) {

    }

    @Override
    public void onBrowserClick(FeedItem item, int position) {
        Toast.makeText(this, "TODO! Browser: " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareClick(FeedItem item, int position) {
        Toast.makeText(this, "TODO! Share: " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrashClick(FeedItem item, int position) {


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}