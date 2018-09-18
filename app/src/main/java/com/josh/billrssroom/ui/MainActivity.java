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
import com.josh.billrssroom.model.BillModel;
import com.josh.billrssroom.ui.favorites.FavoritesActivity;
import com.josh.billrssroom.ui.viewmodel.FavoritesViewModel;
import com.josh.billrssroom.ui.feed.BillAdapter;
import com.josh.billrssroom.ui.feed.BillItemClickListener;
import com.josh.billrssroom.ui.feed.BillItemAnimator;
import com.josh.billrssroom.ui.viewmodel.BillViewModel;

public class MainActivity extends AppCompatActivity implements BillItemClickListener {

    private static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";

    private ActivityMainBinding binding;

    private BillAdapter adapter;

    private BillViewModel viewModel;
    private FavoritesViewModel favoritesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new BillAdapter(this);
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setItemAnimator(new BillItemAnimator());

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        viewModel = ViewModelProviders.of(this).get(BillViewModel.class);

        subscribeUi(viewModel);

    }

    private void subscribeUi(BillViewModel viewModel) {

        viewModel.getAllBills().observe(this, billItems -> {
            if (billItems != null) {
                binding.setIsLoading(false);
                adapter.setBillsList(billItems);
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
    public void onSaveClicked(BillModel billModel, int position) {
        Toast.makeText(MainActivity.this, "Saved: " + billModel.getTitle(),
                Toast.LENGTH_SHORT).show();

        favoritesViewModel.insertSingleRecord(billModel);

        adapter.notifyItemChanged(position, ACTION_LIKE_BUTTON_CLICKED);
    }

    @Override
    public void onShareClicked(BillModel billModel) {
    }

    @Override
    public void onBrowserClicked(BillModel billModel) {
    }
}