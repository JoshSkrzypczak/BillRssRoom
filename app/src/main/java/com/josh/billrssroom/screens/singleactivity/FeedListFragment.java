package com.josh.billrssroom.screens.singleactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.ScreensNavigator;
import com.josh.billrssroom.screens.common.controllers.BaseFragment;
import com.josh.billrssroom.screens.feed.FeedListViewMvc;
import com.josh.billrssroom.utilities.AsyncClickTask;
import com.josh.billrssroom.utilities.AsyncResponse;
import com.josh.billrssroom.viewmodel.FeedViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class FeedListFragment extends BaseFragment implements FeedListViewMvc.Listener {

    public static Fragment newInstance() {
        return new FeedListFragment();
    }

    private FeedListViewMvc viewMvc;

    private FeedViewModel feedViewModel;

    private FeedListController feedListController;

    private ScreensNavigator screensNavigator;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewMvc = getCompositionRoot().getViewMvcFactory().getFeedListViewMvc(container);
        screensNavigator = getCompositionRoot().getScreensNavigator();
        feedListController = getCompositionRoot().getFeedListController();
        feedListController.bindView(viewMvc);

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        subscribeFeedUi(feedViewModel);

        return viewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        feedListController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        feedListController.onStop();
    }

    private void subscribeFeedUi(FeedViewModel feedViewModel) {
        feedViewModel.getLiveDataFeedItems().observe(this, listResource -> {
            if (listResource != null && listResource.data != null) {
                viewMvc.bindFeedItems(listResource.data);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                screensNavigator.toFavorites();
                return true;
            case R.id.action_get_count:
                int feedCount = viewMvc.getFeedCount();
                feedListController.onCountMenuBtnClicked(feedCount);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        feedListController.onShareBtnClicked(feedItem, position);
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem, int position) {
        feedListController.onBrowserBtnClicked(feedItem, position);
    }

    @Override
    public void onSaveBtnClicked(FeedItem feedItem, int position) {
        feedListController.onSaveBtnClicked(feedItem, position);

        // TODO: 11/17/2018 Animate btn onClick
        // TODO: 11/17/2018 No ripple effect onClick
        AsyncClickTask.TaskParams taskParams =
                new AsyncClickTask.TaskParams(position, feedItem);

        AsyncClickTask asyncTask = new AsyncClickTask(
                getActivity(),
                position,
                new AsyncResponse() {
                    @Override
                    public void onPreExecute(int position1) {
                    }

                    @Override
                    public void onProgressUpdate(int value) {
                    }

                    @Override
                    public void onPostExecute(int value) {
                    }
                });
        asyncTask.execute(taskParams);
    }
}
