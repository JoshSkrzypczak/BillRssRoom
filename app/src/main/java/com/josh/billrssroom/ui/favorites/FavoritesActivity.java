package com.josh.billrssroom.ui.favorites;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.BillModel;
import com.josh.billrssroom.ui.viewmodel.FavoritesViewModel;

public class FavoritesActivity extends AppCompatActivity implements FavoriteClickListener {

    private static final String TAG = FavoritesActivity.class.getSimpleName();
    private FavoritesViewModel favoritesViewModel;
    private RecyclerView recyclerView;
    private MyFavoritesAdapter adapter;

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

        adapter = new MyFavoritesAdapter(this, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        favoritesViewModel.getAllFavorites().observe(this, billItems -> {
            adapter.setBillItemList(billItems);

        });

    }

    @Override
    public void onBrowserClick(BillModel billModel, int position) {
        Toast.makeText(this, "Browser Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareClick(BillModel billModel, int position) {
        Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrashClick(BillModel billModel, int position) {
        Toast.makeText(this, "Trash Clicked", Toast.LENGTH_SHORT).show();

        favoritesViewModel.deleteSingleRecord(billModel);
        adapter.notifyDataSetChanged();
        adapter.notifyItemRemoved(position);

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