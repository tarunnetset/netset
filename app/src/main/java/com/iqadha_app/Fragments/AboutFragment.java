package com.iqadha_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iqadha_app.AsynchClasses.CommonFunctions;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.FireBaseModel.AboutModel;
import com.iqadha_app.R;
import com.iqadha_app.Utils.Alerts;

/**
 * Created by netset on 9/11/16.
 */

public class AboutFragment extends Fragment {
    View rootView;
    TextView about_detail, about_title;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.about_fragment, container, false);
        about_detail = (TextView) rootView.findViewById(R.id.about_detail);
        about_title = (TextView) rootView.findViewById(R.id.about_title);


        if( CommonFunctions.getConnectivityStatus(getActivity())) {
            getAboutData(InfoFragment.language);
        }else {
            Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
        }
        return rootView;
    }





    void getAboutData(String language) {

        DatabaseReference datebase2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = datebase2.child(Globals.INFO).child(Globals.LANG).child(language).child(Globals.PAGE).child(Globals.ABOUT).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AboutModel about_us = dataSnapshot.getValue(AboutModel.class);
                about_detail.setText(about_us.Sub_Content);
                about_title.setText(about_us.Title);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
