package com.josh.billrssroom.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.BillItem;
import com.josh.billrssroom.utilities.Utils;
import com.josh.billrssroom.viewmodel.BillViewModel;
import com.josh.billrssroom.databinding.ActivityMainBinding;
import com.josh.billrssroom.viewmodel.FavoritesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BillAdapter billAdapter;
    private BillViewModel viewModel;
    private FavoritesViewModel favoritesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        billAdapter = new BillAdapter(this, billClickCallback);
        binding.billsList.setAdapter(billAdapter);
        binding.billsList.setItemAnimator(new FeedItemAnimator());

        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        viewModel = ViewModelProviders.of(this).get(BillViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(BillViewModel viewModel) {

        viewModel.getAllBills().observe(this, new Observer<List<BillItem>>() {
            @Override
            public void onChanged(@Nullable List<BillItem> billItems) {
                if (billItems != null) {
                    binding.setIsLoading(false);
                    billAdapter.setBillItemList(billItems);
                } else {
                    binding.setIsLoading(true);
                }
            }
        });
    }

    private final BillClickCallback billClickCallback = new BillClickCallback() {
        @Override
        public void onBrowserClick(BillItem billItem, int position) {
            Utils.openCustomTab(MainActivity.this, billItem.getLink());
        }

        @Override
        public void onSaveClick(BillItem billEntity, int position) {
            Toast.makeText(MainActivity.this, "Saved: " + billEntity.getTitle(),
                    Toast.LENGTH_SHORT).show();
            favoritesViewModel.insert(billEntity);

        }

        @Override
        public void onShareClick(BillItem billItem, int position) {
            Toast.makeText(MainActivity.this, "Shared: " + billItem.getTitle(),
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.favorites:
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
