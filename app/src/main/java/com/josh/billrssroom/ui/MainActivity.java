package com.josh.billrssroom.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.BillItem;
import com.josh.billrssroom.utilities.Utils;
import com.josh.billrssroom.viewmodel.BillViewModel;
import com.josh.billrssroom.databinding.ActivityMainBinding;

import java.util.List;

import okhttp3.internal.Util;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BillAdapter billAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        recyclerView = findViewById(R.id.bills_list);
//        layoutManager = new LinearLayoutManager(this);
//        billAdapter = new BillAdapter(this);
//        recyclerView.setLayoutManager(layoutManager);

        billAdapter = new BillAdapter(this, billClickCallback);
        binding.billsList.setAdapter(billAdapter);

        final BillViewModel viewModel = ViewModelProviders.of(this).get(BillViewModel.class);

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
        public void onBrowserClick(BillItem billItem) {
            Utils.openCustomTab(MainActivity.this, billItem.getLink());
        }

        @Override
        public void onSaveClick(BillItem billItem) {
            Toast.makeText(MainActivity.this, "Saved: " + billItem.getTitle(),
                    Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onShareClick(BillItem billItem) {
            Toast.makeText(MainActivity.this, "Shared: " + billItem.getTitle(),
                    Toast.LENGTH_SHORT).show();
        }
    };
}
