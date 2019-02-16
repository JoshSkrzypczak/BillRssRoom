package com.josh.billrssroom.viewmodel;

import android.app.Application;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.FeedRepository;
import com.josh.billrssroom.utilities.Objects;

import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class FavoritesViewModel extends AndroidViewModel {

    private FeedRepository feedRepository;

    private final MediatorLiveData<List<FeedItem>> observableFavorites;

    private LiveData<List<FeedItem>> allFavorites;
    private LiveData<List<FeedItem>> results;
    private MutableLiveData<String> query = new MutableLiveData<>();


    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        feedRepository = ((BasicApp) application).getFeedRepository();

        allFavorites = feedRepository.getFavorites();
        results = Transformations.switchMap(query,
                input -> feedRepository.searchFavorites(input));



        observableFavorites = new MediatorLiveData<>();
        observableFavorites.setValue(null);
        LiveData<List<FeedItem>> favorites = ((BasicApp) application).getFeedRepository().getFavorites();
        observableFavorites.addSource(favorites, observableFavorites::setValue);
    }


    public LiveData<List<FeedItem>> getResults(){
        return results;
    }

//    public void setQuery(String filter){
//        query.postValue(filter);
//    }

    public void setQuery(@Nonnull String originalInput){
        String input = originalInput.toLowerCase(Locale.getDefault()).trim();
        if (Objects.equals(input, query.getValue())){
            return;
        }
        query.setValue(input);
    }

    public LiveData<List<FeedItem>> getAllFavorites(){
        return allFavorites;
    }


    public LiveData<List<FeedItem>> getFavorites() {
        return observableFavorites;
    }

    public void deleteAllFavorites() {
        feedRepository.deleteAllFavorites();
    }

    public void removeItemFromFavorites(FeedItem feedItem) {
        feedRepository.removeItemFromFavorites(feedItem);
    }

    public void updateRemoveItemFromFavorites(FeedItem feedItem){
        feedRepository.updateFeedItemAsFavorite(feedItem);
    }

    public LiveData<List<FeedItem>> searchFavorites(String query){
        return feedRepository.searchFavorites(query);
    }

    public int getNumFavCount(){
        return feedRepository.getNumFavoriteItems();
    }
}
