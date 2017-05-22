package com.iqadha_app.AsynchClasses;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

public class MainAsyncTask extends AsyncTask<String, Void, String> {

    MainAsynListener<String> listener;
    int receivedId;
    public static ProgressDialog mDialog;

    String url, tag, dialog_text;
    List<ParamsGetter> getterList;
    boolean isDialogDisplay, isSuccess = false;
    Context context;
    InputStream mInputStreamis = null;
    public CommonFunctions sSetconnection;


    /**
     * @param context
     * @param url
     * @param receivedId
     * @param listener
     */
    public MainAsyncTask(Context context, String url, int receivedId,
                         MainAsynListener<String> listener, String tag, List<ParamsGetter> getterList) {

        this.context = context;
        this.url = url;
        this.receivedId = receivedId;
        this.listener = listener;
        this.tag = tag;
        this.getterList = getterList;

    }

    public MainAsyncTask(Context context, String url, int receivedId,
                         MainAsynListener<String> listener, String tag, List<ParamsGetter> getterList, boolean load) {

        this.context = context;
        this.url = url;
        this.receivedId = receivedId;
        this.listener = listener;
        this.tag = tag;
        this.getterList = getterList;
        this.isDialogDisplay = load;
    }


    @Override
    protected void onPreExecute() {
        sSetconnection = new CommonFunctions();
        if (isDialogDisplay) {
            if (mDialog != null) {
                if (mDialog.isShowing()) {
                    cancelDialog();
                }
            }
            showCommonDialog(context);
        }

    }

    @Override
    protected String doInBackground(String... arg0) {
        String mResult = null;

//		JSONObject json = letterbox JSONObject();
        try {
            if (CommonFunctions.getConnectivityStatus(context)) {
                mResult = CommonFunctions.getOkHttpClient(context, url, receivedId, tag, getterList);
            } else {
                showCommonDialog(context);
            }

            if (mResult != null) {
                isSuccess = true;
            } else {
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {

                    @Override
                    public void run() {
//				        	CommonUtilities.showMessage("Please Try Again..", context,Toast.LENGTH_SHORT);
                        Toast.makeText(context, "Please Try Again..", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mResult;
    }

    /**
     * activateDriverCheckResponse 1 = flag(Email does not Exist) 2 = Error with
     * HTTP connection 3 = Error while convert into string 4 = Failure 5 = Email
     * Already Exist
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onPostExecute(String _result) {
        try {

            if (isSuccess) {
                if (_result == null) {
                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "Please Try Again..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Log.e("result><><><>", "" + _result);
                listener.onPostSuccess(_result, receivedId, isSuccess);
                try {
                    cancelDialog();
                } catch (Exception e) {
                    Log.e("exception><><><>", "" + "loader");
                }

            } else {
                listener.onPostError(receivedId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub

        super.onCancelled();

    }

    public void showCommonDialog(Context mContext) {
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage(dialog_text);
        mDialog.setIndeterminate(false);
        mDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        mDialog.setCancelable(false);
        mDialog.show();

    }

    // cancel progress dialog
    public void cancelDialog() {
        try {
            if (mDialog != null) {
                if (mDialog.isShowing()) {
                    mDialog.cancel();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

