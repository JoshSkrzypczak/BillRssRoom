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
        Log.i(TAG, "onPreExecute: position: " + position);
        super.onPreExecute();
        delegate.onPreExecute(position);
    }

    @Override
    protected Integer doInBackground(MyTaskParams... myTaskParams) {
        Log.i(TAG, "doInBackground: doInBackground");
        int myIntParam = myTaskParams[0].intParam;
        FeedItem feedItem = myTaskParams[0].feedItem;

        int favoriteValueInt = asyncDao.getIntBoolean(feedItem.getTitle());

        Log.d(TAG, "doInBackground: favoriteIntValueBeforeClick: " + favoriteValueInt);

        if (favoriteValueInt == 1) {
            asyncDao.updateAndSetItemToFalse(feedItem.getTitle());
        } else {
            asyncDao.updateAndSetItemToTrue(feedItem.getTitle());
        }

        return myIntParam;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.i(TAG, "onProgressUpdate: value: " + values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        Log.i(TAG, "onPostExecute: value: " + integer);
        super.onPostExecute(integer);
        delegate.onPostExecute(integer);
    }
}