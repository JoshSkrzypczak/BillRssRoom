package com.josh.billrssroom.utilities;

import android.content.Context;
import android.os.AsyncTask;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.db.dao.ItemDao;
import com.josh.billrssroom.model.FeedItem;

public class AsyncRowTask extends AsyncTask<FeedItem, Integer, Integer> {

    public ItemDao asyncDao;
    public AsyncResponse delegate;

    public AsyncRowTask(Context context, AsyncResponse delegate){
        asyncDao = ((BasicApp)context.getApplicationContext()).getFeedDatabase().feedDao();
        this.delegate = delegate;
    }

    @Override
    protected Integer doInBackground(FeedItem... items) {

        String billTitle = items[0].getTitle();


        return asyncDao.getIntBoolean(items[0].getTitle()); // passed to onPostExecute
    }

    @Override
    protected void onPostExecute(Integer favoriteValueInt) {
        delegate.delegatePostExecute(favoriteValueInt);
    }
}