package com.josh.billrssroom.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.db.dao.ItemDao;
import com.josh.billrssroom.model.FeedItem;

public class AsyncClickTask extends AsyncTask<AsyncClickTask.TaskParams, Integer, Integer> {

    public static class TaskParams {
        FeedItem feedItem;
        int intParam;


        public TaskParams(int intParam, FeedItem feedItem){
            this.intParam = intParam;
            this.feedItem = feedItem;
        }
    }

    public static final String TAG = AsyncClickTask.class.getSimpleName();


    public ItemDao asyncDao;public AsyncResponse delegate;
    int position;

    public AsyncClickTask(Context context, int position, AsyncResponse asyncResponse){
        asyncDao = ((BasicApp) context.getApplicationContext()).getFeedDatabase().feedDao();
        this.position = position;
        delegate = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        delegate.delegatePreExecute(position);
    }

    @Override
    protected Integer doInBackground(TaskParams... taskParams) {
        int myIntParam = taskParams[0].intParam;
        FeedItem feedItem = taskParams[0].feedItem;

        int favoriteValueInt = asyncDao.getIntBoolean(feedItem.getTitle());
        Log.d(TAG, "doInBackground: favoriteIntValue: " + favoriteValueInt);


        if (favoriteValueInt == 1) {
            asyncDao.updateAndSetItemToFalse(feedItem.getTitle());
        } else {
            asyncDao.updateAndSetItemToTrue(feedItem.getTitle());
        }

        publishProgress(favoriteValueInt);

        return myIntParam;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        delegate.delegateProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        delegate.delegatePostExecute(integer);
    }
}
