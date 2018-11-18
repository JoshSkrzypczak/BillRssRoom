package com.josh.billrssroom.screens.feed;

import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.toolbar.ToolbarViewMvc;
import com.josh.billrssroom.screens.common.views.BaseObservableViewMvc;
import com.josh.billrssroom.screens.common.ViewMvcFactory;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

public class FeedListViewMvcImpl extends BaseObservableViewMvc<FeedListViewMvc.Listener> implements
        FeedListViewMvc,
        FeedAdapterMvc.Listener {

//    private final ToolbarViewMvc toolbarViewMvc;
//    private final Toolbar toolbar;

    private final RecyclerView recyclerView;
    private final FeedAdapterMvc feedAdapterMvc;

    public FeedListViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.activity_main, parent, false));

        recyclerView = findViewById(R.id.recyclerview);
        feedAdapterMvc = new FeedAdapterMvc(getContext(), this, viewMvcFactory);
        recyclerView.setAdapter(feedAdapterMvc);

//        toolbar = findViewById(R.id.toolbar);
//        toolbarViewMvc = viewMvcFactory.getToolbarViewMvc(toolbar);
//        toolbar.addView(toolbarViewMvc.getRootView());
//        toolbarViewMvc.setTitle(getString(R.string.app_name));

    }

    @Override
    public void bindFeedItems(List<FeedItem> data) {
        feedAdapterMvc.setFeedItems(data);
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        for (Listener listener : getListeners()) {
            listener.onShareBtnClicked(feedItem, position);
        }
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem, int position) {
        for (Listener listener : getListeners()) {
            listener.onBrowserBtnClicked(feedItem, position);
        }
    }
}