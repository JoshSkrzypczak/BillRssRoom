package com.josh.billrssroom.utilities;

public interface AsyncResponse {

    void onPreExecute(int position);

    void onProgressUpdate(int value);

    void onPostExecute(int favoriteValueInt);
}