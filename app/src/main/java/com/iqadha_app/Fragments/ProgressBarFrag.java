package com.iqadha_app.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.R;
import com.iqadha_app.Splash;
import com.natasa.progressviews.CircleSegmentBar;
import com.natasa.progressviews.utils.ProgressStartPoint;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by netset on 22/11/16.
 */
public class ProgressBarFrag extends Fragment {
    View view;
    RelativeLayout nxt;
    CircleSegmentBar segmentBar;
    CircleSegmentBar segment_bar2;
    ImageView back_minus;
    public static Runnable mTimer;
    TextView text_per;
    private Handler mHandler;
    TextView compted_data;
    public static Boolean start = false;
    Boolean runin = false;
    CountDownTimer cTimer = null;
    Timer T;
    int count = 0;


    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.image_ll, container, false);
        nxt = (RelativeLayout) view.findViewById(R.id.nxt);
        nxt.setVisibility(View.VISIBLE);
        text_per = (TextView) view.findViewById(R.id.text_per);
        back_minus = (ImageView) view.findViewById(R.id.image_container);
        segmentBar = (CircleSegmentBar) view.findViewById(R.id.segment_bar);
        segment_bar2 = (CircleSegmentBar) view.findViewById(R.id.segment_bar2);
        compted_data = (TextView) view.findViewById(R.id.compted_data);

        compted_data.setText(Globals.Totaldone_prayers + " of " + Globals.total_p + " Completed");
        segmentBar.setCircleViewPadding(2);
        Log.e("avgpercentis", Globals.averagesume + "");


        segmentBar.setStartPositionInDegrees(360);
        segmentBar.setLinearGradientProgress(false);
        //you can set start position for progress
        segmentBar.setStartPositionInDegrees(ProgressStartPoint.BOTTOM);
        //you can set linear gradient with default colors or to set yours colors, or sweep gradient
        segmentBar.setProgressColor(getActivity().getResources().getColor(R.color.yellow));
        segment_bar2.setCircleViewPadding(2);
        //  segment_bar2.setWidthProgressBackground(25);
        segment_bar2.setWidthProgressBarLine(1);
        segment_bar2.setStartPositionInDegrees(360);
        segment_bar2.setLinearGradientProgress(false);
        segment_bar2.setStartPositionInDegrees(ProgressStartPoint.BOTTOM);


        if ((Splash.currentmin >= Splash.Nightmin) || (Splash.currentmin <= Splash.Daymin)) {
            back_minus.setBackgroundResource(R.mipmap.moon_up);
            segmentBar.setProgressColor(getActivity().getResources().getColor(R.color.purple));
            segment_bar2.setProgressColor(getActivity().getResources().getColor(R.color.purple));

        } else {
            back_minus.setBackgroundResource(R.mipmap.day_image);
            segmentBar.setProgressColor(getActivity().getResources().getColor(R.color.yellow));
            segment_bar2.setProgressColor(getActivity().getResources().getColor(R.color.yellow));

        }

        //   Log.e("inview adapter", "inview adapter");
        mHandler = new Handler();
        mHandler.sendEmptyMessage(0);//Do this when you add the call back.
        if (mHandler.hasMessages(0)) {
            Log.e("inside handler", "inside Handler");
            mHandler.removeCallbacks(mTimer);
            // setTimer(segmentBar, segment_bar2);
            //mHandler.removeMessages(some_integer);
        }

        if (runin) {
            cancetT();
        }
        T = new Timer();
        segmentBar.setProgress(Globals.averagesume);
        //   setTimer(segmentBar, segment_bar2);
        Log.e("on create", "oncreate Progress bAR");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        segmentBar.setProgress(Globals.averagesume);
        Log.e("on resume is called", "on resume");
        count = 0;
        try {
            NewstartTimer(segmentBar, segment_bar2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    void NewstartTimer(final CircleSegmentBar segmentBar, final CircleSegmentBar segment_bar2) {
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (count <= Globals.averagesume) {
                    runin = true;
                    //  Log.e("count", count + "");
                    final int finalProgress = count;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Log.e("inside timer", "Timerr");
                            segmentBar.setProgress((float) finalProgress);
                            segment_bar2.setProgress((float) finalProgress);
                            //Log.e("inside", "FORRRRR " + finalProgress);
                            if ((Splash.currentmin >= Splash.Nightmin) || (Splash.currentmin <= Splash.Daymin)) {
                                segmentBar.setText("" + finalProgress + "%", 40, getResources().getColor(R.color.transparent));
                                text_per.setText(" " + finalProgress + "%");
                                text_per.setTextColor(getResources().getColor(R.color.purple));


                            } else {
                                segmentBar.setText(finalProgress + "%", 40, getResources().getColor(R.color.transparent));
                                text_per.setText(" " + finalProgress + "%");
                                text_per.setTextColor(getResources().getColor(R.color.yellow));
                            }

                            count++;
                        }
                    });

                } else {
                    Log.e("elsee count", count + "");
                    cancetT();
                }
            }
        }, 25, 25);

    }

    void cancetT() {
        if (T != null)
            T.cancel();
        runin = false;

    }

}
