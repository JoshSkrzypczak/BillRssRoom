package com.josh.billrssroom.utilities;

public interface AsyncResponse {

    void delegatePreExecute(int position);

    void delegateProgressUpdate(int value);

    void delegatePostExecute(int favoriteValueInt);
}
