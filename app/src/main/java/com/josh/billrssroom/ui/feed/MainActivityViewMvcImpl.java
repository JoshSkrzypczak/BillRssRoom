package com.josh.billrssroom.ui.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.josh.billrssroom.R;
import com.josh.billrssroom.model.FeedItem;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MainActivityViewMvcImpl implements BillItemClickListener, MainActivityViewMvc {

    private RecyclerView recyclerView;
    private OtherRssAdapter otherRssAdapter;

    private final View rootView;
    private final List<Listener> listeners = new ArrayList<>(3);

    public MainActivityViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        rootView = inflater.inflate(R.layout.activity_main, parent, false);

        recyclerView = findViewById(R.id.recyclerview);
        otherRssAdapter = new OtherRssAdapter(getContext(), this);
        recyclerView.setAdapter(otherRssAdapter);
    }

    @Override
    public void registerListener(Listener listener){
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(Listener listener){
        listeners.remove(listener);
    }

    private Context getContext() {
        return getRootView().getContext();
    }

    private <T extends View> T findViewById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void onSaveBtnClick(FeedItem feedItem, int position) {
    }

    @Override
    public void onShareBtnClick(FeedItem feedItem, int position) {
        for (Listener listener : listeners){
            listener.onShareBtnClicked(feedItem, position);
        }
    }

    @Override
    public void onBrowserBtnClick(FeedItem feedItem) {
        for (Listener listener : listeners){
            listener.onBrowserBtnClicked(feedItem);
        }
    }

    @Override
    public void bindFeedItems(List<FeedItem> data) {
        otherRssAdapter.setRssList(data);
    }
}
