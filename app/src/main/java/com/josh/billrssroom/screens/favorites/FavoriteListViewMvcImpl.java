package com.josh.billrssroom.screens.favorites;

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


/**
 * Implemented in {@link FavoritesActivity}
 */
public class FavoriteListViewMvcImpl extends BaseObservableViewMvc<FavoriteListViewMvc.Listener>
        implements FavoriteListViewMvc, FavoriteAdapterMvc.Listener {

    private RecyclerView recyclerView;
    private FavoriteAdapterMvc favoriteAdapterMvc;

    public FavoriteListViewMvcImpl(LayoutInflater inflater,
                                   @Nullable ViewGroup parent,
                                   ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.activity_favorites, parent, false));

        recyclerView = findViewById(R.id.favorites_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteAdapterMvc = new FavoriteAdapterMvc(getContext(), this, viewMvcFactory);
        recyclerView.setAdapter(favoriteAdapterMvc);
    }

    @Override
    public int getFavoriteCount() {
        return favoriteAdapterMvc.getItemCount();
    }

    @Override
    public void bindFavoriteItems(List<FeedItem> data) {
        favoriteAdapterMvc.setFavoriteItemList(data);
    }

    @Override
    public void onDeleteBtnClicked(FeedItem feedItem, int position) {
        for (Listener listener : getListeners()) {
            listener.onDeleteBtnClicked(feedItem, position);
        }
        favoriteAdapterMvc.notifyItemRemoved(position);
    }

    @Override
    public void onShareBtnClicked(FeedItem feedItem, int position) {
        for (Listener listener : getListeners()) {
            listener.onShareBtnClicked(feedItem, position);
        }
    }

    @Override
    public void onBrowserBtnClicked(FeedItem feedItem) {
        for (Listener listener : getListeners()) {
            listener.onBrowserBtnClicked(feedItem);
        }
    }
}