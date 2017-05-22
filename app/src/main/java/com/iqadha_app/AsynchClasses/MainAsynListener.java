package com.iqadha_app.AsynchClasses;

import org.json.JSONException;

public interface MainAsynListener<T> {

    void onPostSuccess(T result, int flag, boolean isSucess) throws JSONException;

    void onPostError(int flag);

}
