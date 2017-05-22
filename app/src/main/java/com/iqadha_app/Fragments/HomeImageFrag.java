package com.iqadha_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iqadha_app.R;
import com.iqadha_app.Splash;

/**
 * Created by netset on 22/11/16.
 */
public class HomeImageFrag extends Fragment {
    View rootView;
    RelativeLayout nxt;
    ImageView back_minus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.image_ll, container, false);
        nxt = (RelativeLayout) rootView.findViewById(R.id.nxt);
        nxt.setVisibility(View.INVISIBLE);
        back_minus = (ImageView) rootView.findViewById(R.id.image_container);
        if ((Splash.currentmin >= Splash.Nightmin) || (Splash.currentmin <= Splash.Daymin)) {
            back_minus.setBackgroundResource(R.mipmap.moon_up);
        } else {
            back_minus.setBackgroundResource(R.mipmap.day_image);


        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        nxt = (RelativeLayout) rootView.findViewById(R.id.nxt);
        nxt.setVisibility(View.INVISIBLE);
        if ((Splash.currentmin >= Splash.Nightmin) || (Splash.currentmin <= Splash.Daymin)) {
            back_minus.setBackgroundResource(R.mipmap.moon_up);
        } else {
            back_minus.setBackgroundResource(R.mipmap.day_image);


        }
    }


}
