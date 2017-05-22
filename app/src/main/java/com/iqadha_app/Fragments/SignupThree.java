package com.iqadha_app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iqadha_app.Adapters.GridAdapter;
import com.iqadha_app.AsynchClasses.CommonFunctions;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.FireBaseModel.PrayListData;
import com.iqadha_app.HomeActivity;
import com.iqadha_app.R;
import com.iqadha_app.StepSignup;
import com.iqadha_app.Utils.Alerts;

import java.util.ArrayList;
import java.util.List;

import static com.iqadha_app.Utils.Alerts.cancelDialog;
import static com.iqadha_app.Utils.Alerts.showCommonDialog;

/**
 * Created by netset on 8/11/16.
 */
public class SignupThree extends Fragment implements View.OnClickListener {
    GridView cat;
    View rootView;
    ArrayList<String> prayers, countprayers;
    ArrayList<Integer> pray_image;
    LinearLayout apply_btn, apply_ll;
    Button signup_done;
    public static int pos = 90;
    GridAdapter adap;
    private DatabaseReference datebase2;
    DatabaseReference ref;
    EditText edit_count_edt;
    List<PrayListData> list = new ArrayList<PrayListData>();
    ChildEventListener listener;
    private DatabaseReference mDatabase;
    boolean isListenerAdded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signup_3, container, false);
        cat = (GridView) rootView.findViewById(R.id.cat);
        signup_done = (Button) rootView.findViewById(R.id.signup_done);
        edit_count_edt = (EditText) rootView.findViewById(R.id.edit_count_edt);
        apply_btn = (LinearLayout) rootView.findViewById(R.id.apply_btn);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        StepSignup.action.setVisibility(View.VISIBLE);
        StepSignup.action.setTitle("STEP 2");
        datebase2 = FirebaseDatabase.getInstance().getReference();
        prayers = new ArrayList<String>();
        countprayers = new ArrayList<String>();
        pray_image = new ArrayList<Integer>();
        pray_image.add(R.mipmap.fajr);
        pray_image.add(R.mipmap.zuhr);
        pray_image.add(R.mipmap.asr);
        pray_image.add(R.mipmap.magrib);
        pray_image.add(R.mipmap.isha);
        pray_image.add(R.mipmap.witr);
        datebase2 = FirebaseDatabase.getInstance().getReference();
        if (prayers.size() == 0) {



            if( CommonFunctions.getConnectivityStatus(getActivity())) {
                getAll(Globals.getUid(), getActivity());
            }else {
                Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
            }



        }
        apply_ll = (LinearLayout) rootView.findViewById(R.id.apply_ll);
        cat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
                apply_ll.setVisibility(View.VISIBLE);
                edit_count_edt.setText(countprayers.get(pos));
                adap.notifyDataSetChanged();

            }
        });
        signup_done.setOnClickListener(this);
        apply_btn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_done:
               // UserSignup useris = new UserSignup(Globals.getUid(), Globals.email, Globals.gender, Globals.Dob, Globals.Balig_date, "", "","English");
                if( CommonFunctions.getConnectivityStatus(getActivity())) {
                    MovetoHome();
                }else {
                    Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
                }


                break;
            case R.id.apply_btn:
                String valueupdate = edit_count_edt.getText().toString().trim();
                try {
                    Alerts.hideKeyboard(getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (valueupdate.length() > 0) {
                    Log.e("in value update", "update_values    " + (pos + 1) + " " + valueupdate);

                    if( CommonFunctions.getConnectivityStatus(getActivity())) {
                        updateSingle(Globals.getUid(), (pos + 1) + "", valueupdate);
                    }else {
                        Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
                    }


                    apply_ll.setVisibility(View.INVISIBLE);
                    countprayers.set(pos, valueupdate);
                    pos = 90;
                    adap.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Please Fill Count", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }


    private void MovetoHome() {

        startActivity(new Intent(getActivity(), HomeActivity.class));


    }


    void updateSingle(final String Userid, final String key, final String updatedata) {
        datebase2 = FirebaseDatabase.getInstance().getReference();
        ref = datebase2.child(Globals.PRAYERS).child(Userid).child(Globals.DATA).child(key);
        ref.child("pray_Count").setValue(updatedata);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("datasnapsot", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    void getAll(String Userid, final Context c) {
        showCommonDialog(getActivity(), "");
        Query myTopPostsQuery = datebase2.child(Globals.PRAYERS).child(Userid).child(Globals.DATA);
        ref = myTopPostsQuery.getRef();
        Log.e("refrence get all", ref.toString());
        if (isListenerAdded) {
            ref.removeEventListener(listener);
        }
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PrayListData university = dataSnapshot.getValue(PrayListData.class);
                Log.e("string is", university.Pray_name);
                prayers.add(university.Pray_name);
                countprayers.add(university.pray_Count);
                list.add(university);
                Log.e("string is", list.size() + "prayers " + prayers);
                if (prayers.size() == 6) {
                    cancelDialog();
                    adap = new GridAdapter(c, prayers, pray_image, countprayers);
                    cat.setAdapter(adap);
                    adap.notifyDataSetChanged();
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

            }
        };
        if (!isListenerAdded) {
            ref.addChildEventListener(listener);
            isListenerAdded = true;
        }
    }
}
