package com.iqadha_app.AsynchClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;


@SuppressLint("SimpleDateFormat")
public class CommonFunctions {
    private static String baseUrl = Globals.URL;
    static MainAsynListener<String> listener;
    public static SimpleDateFormat sdf;
    public static String currenttime = "";

    public CommonFunctions() {


    }

    public static String getOkHttpClient(Context c, String Url, int flag, String tag, List<ParamsGetter> getters) {

        String strResponse = "";
        Log.e("onclick", " " + baseUrl + Url);
        try {
            OkHttpClient okk = new OkHttpClient();
            Request request = null;

            if (tag.equals("")) {
                request = new Request.Builder()
                        .url(baseUrl + Url)
                        .build();
            }

            if (tag.equalsIgnoreCase("GET")) {


                Request.Builder requestt = new Request.Builder();
                requestt.url(baseUrl + Url);
                if (getters != null) {
                    for (int i = 0; i < getters.size(); i++) {
                        requestt.addHeader(getters.get(i).getKey(), getters.get(i).getValues());
                    }
                }
                requestt.addHeader("Content-Type", "application/json; charset=utf-8");


                request = requestt.build();
            }

            if (tag.equalsIgnoreCase("Form")) {
                FormEncodingBuilder formbody = new FormEncodingBuilder();
                for (int i = 0; i < getters.size(); i++) {
                    Log.e("KEY VLAUES", ">>>>    " + getters.get(i).getKey() + "            " + getters.get(i).getValues());
                    formbody.add(getters.get(i).getKey(), getters.get(i).getValues());


                }
                RequestBody req = formbody.build();
                request = new Request.Builder()
                        .url(baseUrl + Url)
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .post(req)
                        .build();

            }

            if (tag.equalsIgnoreCase("Multipart")) {
                final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                final MediaType MEDIA_TYPE_Video = MediaType.parse("video/*");
                MultipartBuilder builder = new MultipartBuilder();

                for (int i = 0; i < getters.size(); i++) {

                    if (getters.get(i).getFile() != null) {
                        if (getters.get(i).getFile().getAbsolutePath().endsWith(".png") || getters.get(i).getFile().getAbsolutePath().endsWith(".jpg") || getters.get(i).getFile().getAbsolutePath().endsWith(".jpeg")) {
                            Log.e("KEY VLAUES FILE", ">>>>    " + getters.get(i).getKey() + "            " + getters.get(i).getFile());
                            builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getFile().getName(), RequestBody.create(MEDIA_TYPE_PNG, getters.get(i).getFile()));
                        }
                        if (getters.get(i).getFile().getAbsolutePath().endsWith(".mp4") || getters.get(i).getFile().getAbsolutePath().endsWith(".mpeg") || getters.get(i).getFile().getAbsolutePath().endsWith(".3gp") || getters.get(i).getFile().getAbsolutePath().endsWith(".avi")) {
                            builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getFile().getName(), RequestBody.create(MEDIA_TYPE_Video, getters.get(i).getFile()));
                        }
                    } else {
                        Log.e("KEY VLAUES", ">>>>    " + getters.get(i).getKey() + "            " + getters.get(i).getValues());
                        builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getValues());
                    }
                }
                RequestBody req = builder.build();
                request = new Request.Builder()
                        .url(baseUrl + Url)

                        .post(req)
                        .build();

            }


            Response response = okk.newCall(request).execute();
            strResponse = response.body().string();
            Log.e("String Response", ">>>>   " + strResponse);
        } catch (IOException e) {
            e.printStackTrace();
            listener.onPostError(flag);
        }


        return strResponse;
    }

    /*
        public static String getRetrofitClient(String Url){

            Retrofit retrofit = letterbox Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            Globals.Webservice.RetrofitService service = retrofit.create(Globals.Webservice.RetrofitService.class);


            return "";
        }
    */
    static Boolean isConnected;

    public static boolean getConnectivityStatus(Context context) {

        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = conManager.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)

                isConnected = true;
            // return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                // return TYPE_MOBILE;
                isConnected = true;
        } else {
            isConnected = false;
        }
// return TYPE_NOT_CONNECTED;
        return isConnected;


    }


}
