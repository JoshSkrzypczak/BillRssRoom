package com.josh.billrssroom.screens.feed;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.screens.common.ViewMvcFactory;
import com.josh.billrssroom.screens.common.views.BaseObservableViewMvc;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FeedListViewMvcImpl extends BaseObservableViewMvc<FeedListViewMvc.Listener>
        implements FeedListViewMvc, FeedAdapterMvc.Listener {

    private final RecyclerView recyclerView;
    private final FeedAdapterMvc feedAdapterMvc;

    public FeedListViewMvcImpl(LayoutInflater inflater,
                               @Nullable ViewGroup parent,
                               ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.activity_main, parent, false));

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        feedAdapterMvc = new FeedAdapterMvc(getContext(), this, viewMvcFactory);
        recyclerView.setItemAnimator(new BillItemAnimator());
        recyclerView.setAdapter(feedAdapterMvc);
    }

    @Override
    public void bindFeedItems(List<FeedItem> data) {
        feedAdapterMvc.setFeedItems(data);
    }

    @Override
    public int getFeedCount() {
        return feedAdapterMvc.getItemCount();
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

    @Override
    public void onSaveBtnClicked(FeedItem feedItem, int position) {
        for (Listener listener : getListeners()){
            listener.onSaveBtnClicked(feedItem, position);
            System.out.println(":::onSaveBtnClicked::: " + feedItem.getTitle() + " pos: " + position);
        }
    }
}