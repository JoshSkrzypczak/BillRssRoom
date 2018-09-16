package com.josh.billrssroom.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.databinding.ActivityMainBinding;
import com.josh.billrssroom.model.BillItem;
import com.josh.billrssroom.ui.favorites.FavoritesActivity;
import com.josh.billrssroom.ui.favorites.FavoritesViewModel;
import com.josh.billrssroom.ui.feed.FeedAdapter;
import com.josh.billrssroom.ui.feed.FeedClickListener;
import com.josh.billrssroom.ui.feed.FeedItemAnimator;
import com.josh.billrssroom.ui.feed.FeedViewModel;

public class MainActivity extends AppCompatActivity implements FeedClickListener {

    private static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";

    private ActivityMainBinding binding;

    private FeedAdapter adapter;

    private FeedViewModel feedViewModel;
    private FavoritesViewModel favoritesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new FeedAdapter(this);
        binding.billsList.setAdapter(adapter);
        binding.billsList.setItemAnimator(new FeedItemAnimator());

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeUi(feedViewModel);

    }

    private void subscribeUi(FeedViewModel viewModel) {
        viewModel.getAllBills().observe(this, billItems -> {
            if (billItems != null) {
                binding.setIsLoading(false);
                adapter.setBillItemList(billItems);
            } else {
                binding.setIsLoading(true);
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
            case R.id.favorites:
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveClicked(BillItem billItem, int position) {
        Toast.makeText(MainActivity.this, "Saved: " + billItem.getTitle(),
                Toast.LENGTH_SHORT).show();

        favoritesViewModel.insertSingleRecord(billItem);

        adapter.notifyItemChanged(position, ACTION_LIKE_BUTTON_CLICKED);
    }

    @Override
    public void onShareClicked(BillItem billItem) {
    }

    @Override
    public void onBrowserClicked(BillItem billItem) {
    }
}