package com.iqadha_app.AsynchClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.iqadha_app.Utils.Alerts;

import org.apache.http.NameValuePair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by netset on 29/8/16.
 */
public class Globals {
    public static List<NameValuePair> nameValuePairs;
    public static String URL = "http://wexplor.com/webservices/web_service/";

    public static MultipartEntity reqEntity;
    public static List<ParamsGetter> getterList;
    public static int Tab_pos, frag_ID;
    public static Fragment FRAGMENT;
    public static FragmentTransaction fragmentTransaction;
    public static String Totalprayers;
    public static String email, pass, gender = "NO", Dob, Balig_date;
    public static float day;
    public static String FirebaseUserid;
    public static String todaydate;
    public static int averagesume = 0, Totaldone_prayers = 0, total_p = 0;


    //database_name
    public static String USER="users";

    //table name
    public static String FASTS = "fasts";
    public static String PRAYERS = "prayers";
    public static String DATA = "data";


    //PRAYERS
    public static String FAJR = "Fajr";
    public static String ZUHR = "Zuhr";
    public static String ASR = "Asr";
    public static String MAGRIB = "Maghrib";
    public static String ISHA = "Isha";
    public static String WITR = "Witr";

    //FASTS
    public static String FASTS_QADHA = "Fasts Qadha";



    //INFORMATION

    //About Us Content
    public static String PAGE="Page";
    public static String ABOUT="About";

    //Faq
    public static String FAQ="Faq";
    public static String QUESTIONARIES="Questionnaire";
    public static String TITLE="Title";

    //lANGUAGE
    public static String INFO="Info";
    public static String LANG="lang";

    static SharedPreferences  pref;
    static SharedPreferences.Editor editor;





    public static  void setUserTotalDayTillBaligh(Context c,String totalday)
    {
        pref = c.getSharedPreferences("com.iqadha", c.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("TotalBalighDay", totalday).commit();
    }
    public static String getUserTotalDayTillBaligh (Context c) {
        pref = c.getSharedPreferences("com.iqadha", c.MODE_PRIVATE);
        String day = pref.getString("TotalBalighDay","");
        return day;
    }


    public static Long getttime(Context c) {
        Alerts.showCommonDialog(c, "");
        Long time = null;
        RequestQueue queue = Volley.newRequestQueue(c);
        String url = "http://www.timeapi.org/utc/now";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Alerts.cancelDialog();
                        Log.e("time is", response);
                        try {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                            Date date = simpleDateFormat.parse(response);
                            TimeZone tz = TimeZone.getDefault();
                            //  SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat destFormat = new SimpleDateFormat("d MMM, yyyy");
                            destFormat.setTimeZone(tz);
                            todaydate = destFormat.format(date);
                            Log.e("resulttt", "onResponse: " + todaydate.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errror", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(stringRequest);
        return time;

    }


    public static String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
