package com.iqadha_app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iqadha_app.AsynchClasses.CommonFunctions;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.FireBaseModel.PrayListData;
import com.iqadha_app.FireBaseModel.UserSignup;
import com.iqadha_app.HomeActivity;
import com.iqadha_app.R;
import com.iqadha_app.Utils.Alerts;
import com.iqadha_app.Utils.CommonMethods;
import com.iqadha_app.Utils.MyDialogCloseListener;

import java.util.ArrayList;

import static com.iqadha_app.Utils.Alerts.cancelDialog;
import static com.iqadha_app.Utils.Alerts.showCommonDialog;

/**
 * Created by netset on 9/11/16.
 */

public class SettingFragment extends Fragment implements View.OnClickListener ,MyDialogCloseListener{
    View rootView;
    LinearLayout name_ll, email_ll, gender_ll, phone_ll, sign_out_ll,lang_ll;
    TextView quadha_text, fasts_text;
    public static ArrayList<String> pray_typ, countprayers;
    public static ArrayList<Integer> pray_image;
    String name, email, gender, phone_number,language;
    TextView some_text;
    TextView name_user, email_user, gender_user, phone_number_user;
    ChildEventListener listener;
    boolean isListenerAdded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.setting_layout, container, false);
        HomeActivity.action.ActionBackgroundColor("#5cb28f");
        HomeActivity.action.setMenuButtonVisibility(View.INVISIBLE);
        HomeActivity.action.setTitle("SETTINGS");
        name_ll = (LinearLayout) rootView.findViewById(R.id.name_ll);
        sign_out_ll = (LinearLayout) rootView.findViewById(R.id.sign_out_ll);
        email_ll = (LinearLayout) rootView.findViewById(R.id.email_ll);
        gender_ll = (LinearLayout) rootView.findViewById(R.id.gender_ll);
        phone_ll = (LinearLayout) rootView.findViewById(R.id.phone_ll);
        quadha_text = (TextView) rootView.findViewById(R.id.quadha_text);
        fasts_text = (TextView) rootView.findViewById(R.id.fasts_text);
        some_text = (TextView) rootView.findViewById(R.id.some_text);
        name_user = (TextView) rootView.findViewById(R.id.name_user);
        email_user = (TextView) rootView.findViewById(R.id.email_user);
        gender_user = (TextView) rootView.findViewById(R.id.gender_user);
        lang_ll=(LinearLayout) rootView.findViewById(R.id.lang_ll);
        phone_number_user = (TextView) rootView.findViewById(R.id.phone_number_user);



        if( CommonFunctions.getConnectivityStatus(getActivity())) {
            getProfile(Globals.getUid());
        }else {
            Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
        }

        quadha_text.setOnClickListener(this);
        name_ll.setOnClickListener(this);
        email_ll.setOnClickListener(this);
        gender_ll.setOnClickListener(this);
        phone_ll.setOnClickListener(this);
        fasts_text.setOnClickListener(this);
        sign_out_ll.setOnClickListener(this);
        lang_ll.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quadha_text:
                Globals.Tab_pos = 0;
                Globals.FRAGMENT = new SettingQuadha_fragments();
                pray_typ = new ArrayList<String>();
                countprayers = new ArrayList<String>();
                pray_image = new ArrayList<Integer>();
                pray_image.add(R.mipmap.fajr);
                pray_image.add(R.mipmap.zuhr);
                pray_image.add(R.mipmap.asr);
                pray_image.add(R.mipmap.magrib);
                pray_image.add(R.mipmap.isha);
                pray_image.add(R.mipmap.witr);
                getAll(Globals.getUid(), getActivity(), "qadha");
                break;
            case R.id.name_ll:
                Alerts.alertSettings(getActivity(), false, false, true, false, "Name", name_user.getText().toString(), some_text, false, 0);
                break;
            case R.id.email_ll:
                Alerts.alertSettings(getActivity(), false, false, true, false, "Email", email_user.getText().toString(), some_text, false, 0);
                break;
            case R.id.phone_ll:
                Alerts.alertSettings(getActivity(), false, false, true, false, "Phone Number", phone_number_user.getText().toString(), some_text, false, 0);
                break;
            case R.id.gender_ll:
                Alerts.alertSettings(getActivity(), false, true, false, false, "Gender", gender, some_text, false, 0);
                break;
            case R.id.sign_out_ll:
                Alerts.alertSettings(getActivity(), false, false, false, true, "Sign out", "", some_text, false, 0);
                break;
            case R.id.fasts_text:
                pray_typ = new ArrayList<String>();
                countprayers = new ArrayList<String>();
                pray_image = new ArrayList<Integer>();
                pray_image.add(R.mipmap.fast_img);
                getAll(Globals.getUid(), getActivity(), "fast");
                break;
            case R.id.lang_ll:
                Fragment language_fragment=new Language();
                Bundle b=new Bundle();
                b.putString("language",language);
                language_fragment.setArguments(b);
                CommonMethods.SwitchFragements(HomeActivity.fragmentManager,language_fragment);
                break;
        }
    }



    void getProfile(String Userid) {
        Alerts.showCommonDialog(getActivity(), "");
        DatabaseReference datebase2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = datebase2.child(Globals.USER).child(Userid).child("UserProfile").getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("datasnap shot", dataSnapshot.toString());
                UserSignup useris = dataSnapshot.getValue(UserSignup.class);
                name = useris.Name;
                email = useris.Email;
                gender = useris.Gender;
                phone_number = useris.Phone_number;
                language=useris.Language;
                name_user.setText(name);
                phone_number_user.setText(phone_number);
                email_user.setText(email);
                gender_user.setText(gender);
                cancelDialog();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("on error","error callled");
            }
        });

    }


    void getAll(String Userid, final Context c, final String typ) {
        showCommonDialog(getActivity(), "");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query myTopPostsQuery;
        if (typ.equals("fast")) {
            myTopPostsQuery = mDatabase.child(Globals.FASTS).child(Userid).child(Globals.DATA);
        } else {
            myTopPostsQuery = mDatabase.child(Globals.PRAYERS).child(Userid).child(Globals.DATA);
        }
        DatabaseReference ref = myTopPostsQuery.getRef();
        Log.e("refrence get all", ref.toString());
        if (isListenerAdded) {
            ref.removeEventListener(listener);
            isListenerAdded = false;
        }
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PrayListData university = dataSnapshot.getValue(PrayListData.class);
                Log.e("string is", university.Pray_name);
                pray_typ.add(university.Pray_name);
                countprayers.add(university.pray_Count);
                //list.add(university);
                Log.e("string is", "prayers " + pray_typ);
                if (typ.equals("fast")) {
                    if (pray_typ.size() == 1) {
                        cancelDialog();
                        CommonMethods.SwitchFragements(HomeActivity.fragmentManager,new SettingQuadha_fragments());
                    }
                } else {
                    if (pray_typ.size() == 6) {
                        cancelDialog();
                        CommonMethods.SwitchFragements(HomeActivity.fragmentManager, new SettingQuadha_fragments());

                    }
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
                Log.e("on error","error callled");
            }
        };
        if (!isListenerAdded) {
            ref.addChildEventListener(listener);
            isListenerAdded = true;
        }
    }


    @Override
    public void handleDialogClose(PopupWindow dialog) {
        Log.e("closeee","dialog closeeee");
        getProfile(Globals.getUid());
    }
}
