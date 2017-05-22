package com.iqadha_app.Adapters;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iqadha_app.R;
import com.iqadha_app.Splash;
import com.natasa.progressviews.CircleSegmentBar;
import com.natasa.progressviews.utils.ProgressStartPoint;

import java.util.ArrayList;

;


public class ViewPagerAdapterPager extends PagerAdapter {

    Context context;
    ArrayList<Integer> images;
    String typ;
    private Runnable mTimer;
    protected int progress;
    private Handler mHandler;


    public ViewPagerAdapterPager(Context context, ArrayList<Integer> images, String typ) {
        this.context = context;
        this.images = images;
        this.typ = typ;

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((View) object);
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int resId = 0;
        resId = R.layout.image_ll;
        View view = inflater.inflate(resId, null);
        ImageView back_minus = (ImageView) view.findViewById(R.id.image_container);
        RelativeLayout nxt = (RelativeLayout) view.findViewById(R.id.nxt);

        CircleSegmentBar segmentBar = (CircleSegmentBar) view.findViewById(R.id.segment_bar);
        CircleSegmentBar segment_bar2 = (CircleSegmentBar) view.findViewById(R.id.segment_bar2);
        segmentBar.setCircleViewPadding(2);
        //  segmentBar.setWidth(250);
        //   segmentBar.setWidthProgressBackground(25);
        // segmentBar.setWidthProgressBarLine(25);
        segmentBar.setStartPositionInDegrees(360);
        segmentBar.setLinearGradientProgress(false);
        //you can set start position for progress
        segmentBar.setStartPositionInDegrees(ProgressStartPoint.BOTTOM);

        //you can set linear gradient with default colors or to set yours colors, or sweep gradient
        segmentBar.setProgressColor(context.getResources().getColor(R.color.yellow));
        segment_bar2.setCircleViewPadding(2);
        //  segment_bar2.setWidthProgressBackground(25);
        segment_bar2.setWidthProgressBarLine(1);
        segment_bar2.setStartPositionInDegrees(360);
        segment_bar2.setLinearGradientProgress(false);
        segment_bar2.setStartPositionInDegrees(ProgressStartPoint.BOTTOM);

        if ((Splash.currentmin >= Splash.Nightmin) || (Splash.currentmin <= Splash.Daymin)) {
            back_minus.setBackgroundResource(R.mipmap.moon_up);
            segmentBar.setProgressColor(context.getResources().getColor(R.color.purple));
            segment_bar2.setProgressColor(context.getResources().getColor(R.color.purple));

        } else {
            back_minus.setBackgroundResource(R.mipmap.day_image);
            segmentBar.setProgressColor(context.getResources().getColor(R.color.yellow));
            segment_bar2.setProgressColor(context.getResources().getColor(R.color.yellow));

        }
        if (images.get(position) == 1) {
            nxt.setVisibility(View.INVISIBLE);

        } else {
            nxt.setVisibility(View.VISIBLE);

        }

        Log.e("inview adapter", "inview adapter");
        mHandler = new Handler();
        setTimer(segmentBar, segment_bar2);

        ((ViewPager) container).addView(view, position);

        return view;

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }


    @Override
    public Parcelable saveState() {
        return null;
    }


    private void setTimer(final CircleSegmentBar segmentBar, final CircleSegmentBar segment_bar2) {
        mTimer = new Runnable() {
            @Override
            public void run() {
                progress += 1;
                if (progress <= 60)
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            segmentBar.setProgress((float) progress);
                            segment_bar2.setProgress((float) progress);

                            if ((Splash.currentmin >= Splash.Nightmin) || (Splash.currentmin <= Splash.Daymin)) {
                                segmentBar.setText("" + progress + "%", 40, context.getResources().getColor(R.color.purple));
                            } else {
                                segmentBar.setText("" + progress + "%", 40, context.getResources().getColor(R.color.yellow));
                            }


                        }
                    });

                mHandler.postDelayed(this, 100);
            }
        };

        mHandler.postDelayed(mTimer, 1000);

    }


}
