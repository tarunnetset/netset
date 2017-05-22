package com.iqadha_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iqadha_app.Adapters.ExpandableListAdapter;
import com.iqadha_app.AsynchClasses.CommonFunctions;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.FireBaseModel.FaqModel;
import com.iqadha_app.R;
import com.iqadha_app.Utils.Alerts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by netset on 9/11/16.
 */

public class FAQFragments extends Fragment {
    ExpandableListView questions;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    View rootView;
    TextView faq_title;
    int child_count,count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.faq_fragments, container, false);
        questions = (ExpandableListView) rootView.findViewById(R.id.questions);
        faq_title=(TextView)rootView.findViewById(R.id.faq_title);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();



        if( CommonFunctions.getConnectivityStatus(getActivity())) {
            getFaqData(InfoFragment.language);
            gettitle(InfoFragment.language);
        }else {
            Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
        }



        return rootView;
    }



    void gettitle(String language)
    {
        DatabaseReference datebase2 = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = datebase2.child(Globals.INFO).child(Globals.LANG).child(language).child(Globals.PAGE).child(Globals.FAQ).child(Globals.TITLE).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("dataSnapshot",dataSnapshot.getValue()+"");
                faq_title.setText(dataSnapshot.getValue()+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    void getFaqData(String language) {

        DatabaseReference datebase2 = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = datebase2.child(Globals.INFO).child(Globals.LANG).child(language).child(Globals.PAGE).child(Globals.FAQ).child(Globals.QUESTIONARIES).getRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("counttt",dataSnapshot.getChildrenCount()+"");
                child_count=(int) dataSnapshot.getChildrenCount();
                ref.orderByKey().addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        count++;
                        FaqModel faq = dataSnapshot.getValue(FaqModel.class);
                        listDataHeader.add(faq.Question);
                        List<String> answer = new ArrayList<String>();
                        answer.add(faq.Answer);
                        listDataChild.put(listDataHeader.get(count-1), answer);
                        if(child_count == count){
                            //stop progress bar here
                            Log.e("Complete===",""+"**************");
                            //language_LV.setAdapter(new LanguageAdapter(getActivity(),languages,selected_language));
                            ExpandableListAdapter listAdapter1 = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
                            questions.setAdapter(listAdapter1);

                        }


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("error===",databaseError.toString());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
