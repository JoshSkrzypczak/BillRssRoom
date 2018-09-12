package com.josh.billrssroom.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.BillItem;
import com.josh.billrssroom.viewmodel.FavoritesViewModel;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

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
        favoritesAdapter = new FavoritesAdapter(this, favoriteClickCallback);
        recyclerView.setAdapter(favoritesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        favoritesViewModel.getAllFavorites().observe(this, new Observer<List<BillItem>>() {
            @Override
            public void onChanged(@Nullable List<BillItem> billItems) {
                favoritesAdapter.setBillEntities(billItems);

                System.out.println(billItems);
            }
        });

    }
    private final FavoriteClickCallback favoriteClickCallback = new FavoriteClickCallback() {
        @Override
        public void onBrowserClick(BillItem billItem) {

        }

        @Override
        public void onShareClick(BillItem billItem) {

        }

        @Override
        public void onTrashClick(BillItem billItem) {

        }
    };
}
