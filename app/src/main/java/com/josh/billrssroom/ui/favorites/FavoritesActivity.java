package com.josh.billrssroom.ui.favorites;

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
import com.josh.billrssroom.ui.viewmodel.FavoritesViewModel;

public class FavoritesActivity extends AppCompatActivity implements FavoriteClickListener {

    private static final String TAG = FavoritesActivity.class.getSimpleName();

    private FavoritesViewModel favoritesViewModel;
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

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        favoritesViewModel.getAllFavorites().observe(this, feedItems -> {
            if (feedItems != null){
                adapter.setBillItemList(feedItems);
            }
        });
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
        Toast.makeText(this, "TODO! Delete: " + item.getTitle(), Toast.LENGTH_SHORT).show();

        favoritesViewModel.delete(item);
        adapter.notifyDataSetChanged();

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