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
import com.josh.billrssroom.ui.feed.RssAdapter;
import com.josh.billrssroom.ui.viewmodel.FavoritesViewModel;
import com.josh.billrssroom.ui.feed.BillItemClickListener;
import com.josh.billrssroom.ui.feed.BillItemAnimator;
import com.josh.billrssroom.ui.viewmodel.BillViewModel;
import com.josh.billrssroom.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BillItemClickListener {

    private static final String ACTION_SAVE_BTN_CLICKED = "action_save_btn";
    private ActivityMainBinding binding;
    private FavoritesViewModel favoritesViewModel;
    private BillViewModel viewModel;
    private RssAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new RssAdapter(this);
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setItemAnimator(new BillItemAnimator());

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        viewModel = ViewModelProviders.of(this).get(BillViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(BillViewModel viewModel) {
        viewModel.getAllBills().observe(this, items -> {
            if (items != null){
                binding.setIsLoading(false);
                adapter.setRssList(items);
            } else {
                binding.setIsLoading(true);
            }
        });
    }

    @Override
    public void onSaveClicked(BillModel billModel, int position) {
        Toast.makeText(MainActivity.this, "Saved: " + billModel.getTitle(),
                Toast.LENGTH_SHORT).show();

        // TODO: 9/18/2018 Use better insertion strategy
        favoritesViewModel.insertSingleRecord(billModel);

        adapter.notifyItemChanged(position, ACTION_SAVE_BTN_CLICKED);
    }

    @Override
    public void onShareClicked(BillModel billModel) {
        Toast.makeText(this, "TODO: Shared!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBrowserClicked(BillModel billModel) {
        Utils.openCustomTab(this, billModel.getLink());
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
}