package com.iqadha_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iqadha_app.R;

/**
 * Created by netset on 9/11/16.
 */

public class ContactFragments extends Fragment {
    View rootView;
    TextView info_tx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.contact, container, false);
        info_tx = (TextView) rootView.findViewById(R.id.info_tx);

        return rootView;
    }


}
