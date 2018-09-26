package com.josh.billrssroom.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.api.Resource;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.ui.favorites.FavoritesActivity;
import com.josh.billrssroom.ui.feed.BillItemAnimator;
import com.josh.billrssroom.ui.feed.BillItemClickListener;
import com.josh.billrssroom.ui.feed.RssAdapter;
import com.josh.billrssroom.ui.viewmodel.BillViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BillItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String ACTION_SAVE_BTN_CLICKED = "action_save_btn";
    private BillViewModel viewModel;
    private RssAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new RssAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new BillItemAnimator());

        viewModel = ViewModelProviders.of(this).get(BillViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(BillViewModel viewModel) {

        viewModel.getSecondAttemptObservableBills().observe(this, listResource -> {

            if (listResource != null && listResource.data != null){
                adapter.setRssList(listResource.data);
            }

        });

//        viewModel.getAllBills().observe(this, new Observer<List<FeedItem>>() {
//            @Override
//            public void onChanged(@Nullable List<FeedItem> feedItems) {
//                if (feedItems != null){
//                    adapter.setRssList(feedItems);
//                }
//            }
//        });
    }

    @Override
    public void onSaveClicked(FeedItem item, int position) {
        Toast.makeText(MainActivity.this, "Saved: " + item.getTitle(),
                Toast.LENGTH_SHORT).show();

        viewModel.insertItemToFavorites(item);

        adapter.notifyItemChanged(position, ACTION_SAVE_BTN_CLICKED);
    }

    @Override
    public void onShareClicked(FeedItem item) {
        Toast.makeText(this, "TODO! Shared: " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBrowserClicked(FeedItem item) {
        Toast.makeText(this, "TODO! Browser: " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}