package com.josh.billrssroom.utilities;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.db.dao.ItemDao;
import com.josh.billrssroom.model.FeedItem;

public class MyAsyncTask extends AsyncTask<MyAsyncTask.MyTaskParams, Integer, Integer> {

    public static class MyTaskParams {
        FeedItem feedItem;
        int intParam;

        public MyTaskParams(int intParam, FeedItem feedItem) {
            this.intParam = intParam;
            this.feedItem = feedItem;
        }
    }

    public static final String TAG = MyAsyncTask.class.getSimpleName();

    public AsyncResponse delegate;
    public ItemDao asyncDao;
    int position;

    public MyAsyncTask(Activity activity, int position, AsyncResponse asyncResponse) {
        asyncDao = ((BasicApp) activity.getApplication()).getFeedDatabase().feedDao();
        this.position = position;
        delegate = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        delegate.processTaskSetup(position);
    }

    @Override
    protected Integer doInBackground(MyTaskParams... myTaskParams) {
        int myIntParam = myTaskParams[0].intParam;
        FeedItem feedItem = myTaskParams[0].feedItem;

//        boolean favoriteValue = asyncDao.getItemBoolean(feedItem.getTitle());

        int favoriteValueInt = asyncDao.getIntBoolean(feedItem.getTitle());

        Log.i(TAG, "::int value:: " + String.valueOf(favoriteValueInt));

        if (favoriteValueInt == 1) {
            asyncDao.updateAndSetItemToFalse(feedItem.getTitle());
        } else {
            asyncDao.updateAndSetItemToTrue(feedItem.getTitle());
        }

        return myIntParam;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        delegate.processFinish(integer);
    }
}