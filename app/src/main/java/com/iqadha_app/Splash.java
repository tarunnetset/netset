package com.iqadha_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;

import com.iqadha_app.AsynchClasses.CommonFunctions;
import com.iqadha_app.Utils.Alerts;
import com.iqadha_app.Utils.Font;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Splash extends Activity {
    RelativeLayout activity_splash;
    private static int SPLASH_TIME_OUT = 3000;
    public static float Nightmin = 17.30f;
    public static float Daymin = 4.30f;
    public static Float currentmin;
    String date;

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        //  Font.setDefaultFont(Splash.this,"MONOSPACE", "fonts/ProximaNova-Regular.otf");
        activity_splash = (RelativeLayout) findViewById(R.id.activity_splash);

        pref = getSharedPreferences("com.iqadha", MODE_PRIVATE);
        editor = pref.edit();
        DateFormat df = new SimpleDateFormat("HH.mm");
        date = df.format(Calendar.getInstance().getTime());
        Log.e("dateis", date + "");
        currentmin = Float.parseFloat(date);
        Log.e("dateis", currentmin + "");
        if ((currentmin >= Nightmin) || (currentmin <= Daymin)) {
            activity_splash.setBackgroundResource(R.mipmap.nightsplash);
        } else {
            activity_splash.setBackgroundResource(R.mipmap.daysplash);
        }
        if (CommonFunctions.getConnectivityStatus(Splash.this)) {
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    String userid = pref.getString("FirebaseuserId", "");
                    if (userid.length() > 0) {
                        Intent i = new Intent(Splash.this, HomeActivity.class);
                        startActivity(i);
                    }else {
                        Intent i = new Intent(Splash.this, SignIn.class);
                        startActivity(i);
                    }
                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            Alerts.Alertnet(Splash.this, "Please Connect to Internet", "No Internet Access");
        }


    }

    Runnable settext = new Runnable() {
        @Override
        public void run() {
            Font.setDefaultFont(Splash.this, "MONOSPACE", "fonts/ProximaNova.otf");
            Log.e("thread", "thread");

        }
    };

}
