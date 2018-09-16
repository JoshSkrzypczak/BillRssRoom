package com.josh.billrssroom.ui.favorites;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.BillItem;

public class FavoritesActivity extends AppCompatActivity implements FavoriteClickListener {

    private static final String TAG = FavoritesActivity.class.getSimpleName();
    private FavoritesViewModel favoritesViewModel;
    private RecyclerView recyclerView;
    private FavoritesAdapter favoritesAdapter;

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
        favoritesAdapter = new FavoritesAdapter(this, this);
        recyclerView.setAdapter(favoritesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        favoritesViewModel.getAllFavorites().observe(this, billItems -> {
            favoritesAdapter.setFavoriteItems(billItems);
        });
    }

    @Override
    public void onBrowserClick(BillItem billItem, int position) {
    }

    @Override
    public void onShareClick(BillItem billItem, int position) {
    }

    @Override
    public void onTrashClick(BillItem billItem, int position) {
        favoritesViewModel.deleteSingleRecord(billItem);
        favoritesAdapter.notifyItemRemoved(position);
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