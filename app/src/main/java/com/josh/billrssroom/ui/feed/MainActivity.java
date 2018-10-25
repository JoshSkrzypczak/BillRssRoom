package com.josh.billrssroom.ui.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.josh.billrssroom.R;
import com.josh.billrssroom.api.Resource;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.ui.favorites.FavoritesActivity;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity implements MainActivityViewMvcImpl.Listener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String PAYLOAD_SAVE_BTN_CLICKED = "PAYLOAD_SAVE_BTN_CLICKED";

    private MainActivityViewMvc viewMvc;

    private FeedViewModel feedViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewMvc = new MainActivityViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.registerListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeFeedUi(feedViewModel);

        setContentView(viewMvc.getRootView());
    }


    private void subscribeFeedUi(FeedViewModel feedViewModel) {
        feedViewModel.getObservableFeedItems().observe(this, (Resource<List<FeedItem>> listResource) -> {
            if (listResource != null && listResource.data != null) {

                viewMvc.bindFeedItems(listResource.data);


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
            case R.id.favorite:
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveBtnClicked(FeedItem model, int position) {
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        Toast.makeText(this, "Shared: " + feedItem.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem) {
        Toast.makeText(this, "Browser: " + feedItem.getTitle(), Toast.LENGTH_SHORT).show();
    }
}