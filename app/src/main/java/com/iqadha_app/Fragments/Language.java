package com.iqadha_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iqadha_app.Adapters.LanguageAdapter;
import com.iqadha_app.AsynchClasses.CommonFunctions;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.HomeActivity;
import com.iqadha_app.R;
import com.iqadha_app.Utils.Alerts;

import java.util.ArrayList;

/**
 * Created by netset on 16/5/17.
 */

public class Language  extends Fragment {
    View rootView;
    private int count,child_count;
    ArrayList<String> languages;
    ListView language_LV;
    String selected_language;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.select_language, container, false);
        HomeActivity.action.ActionBackgroundColor("#5cb28f");
        HomeActivity.action.setMenuButtonVisibility(View.VISIBLE);
        HomeActivity.action.setMenuButtonDrawable(R.mipmap.back_icn);
        HomeActivity.action.setMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        HomeActivity.action.setTitle("LANGUAGE");
        Bundle b=getArguments();
        selected_language=b.getString("language");
        language_LV=(ListView)rootView.findViewById(R.id.language);


        if( CommonFunctions.getConnectivityStatus(getActivity())) {
            getAppLanguages();
        }else {
            Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
        }
        return rootView;
    }




    void getAppLanguages() {

        DatabaseReference datebase2 = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = datebase2.child(Globals.INFO).child(Globals.LANG).getRef();
        count = 0;
        languages=new ArrayList<String>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("counttt",dataSnapshot.getChildrenCount()+"");
                child_count=(int) dataSnapshot.getChildrenCount();
                ref.orderByKey().addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        count++;
                        Log.e("datasnap===",dataSnapshot.getKey()+"  count "+ count);
                        languages.add(dataSnapshot.getKey());
                        if(child_count == count){
                            //stop progress bar here
                            Log.e("Complete===",""+languages);
                            language_LV.setAdapter(new LanguageAdapter(getActivity(),languages,selected_language));

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

   //      Alerts.updateProfile(Globals.getUid(),"Language",language);

}
