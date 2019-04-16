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



    private MutableLiveData<String> filterText = new MutableLiveData<>();




    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        feedRepository = ((BasicApp) application).getFeedRepository();

        allFavorites = feedRepository.getFavorites();

        // Trying to mirror this SO question:
        // https://stackoverflow.com/questions/49192540/paging-library-filter-search
        results = Transformations.switchMap(
                filterText, input -> {
                    if (input == null || input.equals("")){
                        return feedRepository.getFavorites();
                    } else {
                        return feedRepository.getFilteredData(input);
                    }
                });



        observableFavorites = new MediatorLiveData<>();
        observableFavorites.setValue(null);
        LiveData<List<FeedItem>> favorites = ((BasicApp) application).getFeedRepository().getFavorites();
        observableFavorites.addSource(favorites, observableFavorites::setValue);
    }


    public LiveData<List<FeedItem>> getResults(){
        return results;
    }

    public void setFilterText(@Nonnull String originalInput){
        String input = originalInput.toLowerCase(Locale.getDefault()).trim();
        if (Objects.equals(input, filterText.getValue())){
            return;
        }
        filterText.postValue(input);
    }

    public LiveData<List<FeedItem>> getAllFavorites(){
        return allFavorites;
    }

    public void deleteAllFavorites() {
        feedRepository.deleteAllFavorites();
    }

    public void updateRemoveItemFromFavorites(FeedItem feedItem){
        feedRepository.updateFeedItemAsFavorite(feedItem);
    }

    public int getFavoriteCount(){
        return feedRepository.getFavoritesCount();
    }
}