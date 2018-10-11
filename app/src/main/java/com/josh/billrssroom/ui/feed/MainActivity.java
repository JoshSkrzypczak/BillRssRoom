package com.josh.billrssroom.ui.feed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.josh.billrssroom.R;
import com.josh.billrssroom.api.Resource;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.ui.favorites.FavoritesActivity;
import com.josh.billrssroom.utilities.AsyncResponse;
import com.josh.billrssroom.utilities.BottomNavDrawerFragment;
import com.josh.billrssroom.utilities.MyAsyncTask;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements BillItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String PAYLOAD_SAVE_BTN_CLICKED = "PAYLOAD_SAVE_BTN_CLICKED";

    private FeedViewModel feedViewModel;
    private RecyclerView recyclerView;
    private OtherRssAdapter otherRssAdapter;
    private BottomAppBar bottomAppBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bar);
        recyclerView = findViewById(R.id.recyclerview);
        otherRssAdapter = new OtherRssAdapter(this, this);
        recyclerView.setAdapter(otherRssAdapter);
        recyclerView.setItemAnimator(new BillItemAnimator());

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeFeedUi(feedViewModel);


        setSupportActionBar(bottomAppBar);
    }


    private void subscribeFeedUi(FeedViewModel feedViewModel) {
        feedViewModel.getObservableFeedItems().observe(this, (Resource<List<FeedItem>> listResource) -> {
            if (listResource != null && listResource.data != null) {
                otherRssAdapter.setRssList(listResource.data);
            }
        });
    }

    @Override
    public void onSaveBtnClick(View view, FeedItem model, int position) {
        Toast.makeText(this, "onSaveClick: " + model.getTitle(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onSaveClick: Favorite button pressed " + model.getTitle());

        MyAsyncTask.MyTaskParams params = new MyAsyncTask.MyTaskParams(position, model);

        MyAsyncTask myAsyncTask = new MyAsyncTask(this, position, new AsyncResponse() {
            @Override
            public void processTaskSetup(int position) {
                otherRssAdapter.notifyItemChanged(position, PAYLOAD_SAVE_BTN_CLICKED);
            }

            @Override
            public void processFinish(int position) {

            }
        });
        myAsyncTask.execute(params);
    }

    @Override
    public void onShareBtnClick(FeedItem model, int position) {
        Log.d(TAG, "onShareBtnClick: " + model.getTitle());
    }

    @Override
    public void onBrowserBtnClick(FeedItem model) {
        Log.d(TAG, "onBrowserBtnClick: " + model.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BottomNavDrawerFragment bottomNavDrawerFragment = new BottomNavDrawerFragment();
                bottomNavDrawerFragment.show(getSupportFragmentManager(), bottomNavDrawerFragment.getTag());
                return true;
            case R.id.favorite:
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}