package com.iqadha_app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iqadha_app.Adapters.HomeGridAdap;
import com.iqadha_app.AsynchClasses.CommonFunctions;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.FireBaseModel.PrayListData;
import com.iqadha_app.HomeActivity;
import com.iqadha_app.R;
import com.iqadha_app.Splash;
import com.iqadha_app.Utils.Alerts;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by netset on 9/11/16.
 */

public class FastFragment extends Fragment implements View.OnClickListener {
    GridView cat_home;
    Button pray_btn;
    View rootView;
    ArrayList<String> pray_typ, count;
    ArrayList<Integer> pray_image, pray_image1;
    ImageView img_back_view_fast;
    ViewPager mPager;
    CircleIndicator indicator;
    public static int current = 0;
    public static HomeFragmentAdapter adapter;

    private DatabaseReference datebase2;
    DatabaseReference ref;
    ChildEventListener listener;
    boolean isListenerAdded = false;
    HomeGridAdap adap_home;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fast_layout, container, false);
        cat_home = (GridView) rootView.findViewById(R.id.cat_home);
        pray_btn = (Button) rootView.findViewById(R.id.add_fast_btn);
        HomeActivity.action.setTitle("iQadha");
        HomeActivity.action.setMenuButtonVisibility(View.INVISIBLE);
        pray_typ = new ArrayList<String>();
        count = new ArrayList<String>();
        HomeActivity.action.ActionBackgroundColor("#00000000");
        pray_image = new ArrayList<Integer>();
        pray_image.add(R.mipmap.fast_img);


        if( CommonFunctions.getConnectivityStatus(getActivity())) {
            getAll(Globals.getUid(), getActivity());
        }else {
            Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
        }
        pray_btn.setOnClickListener(this);
        img_back_view_fast = (ImageView) rootView.findViewById(R.id.img_back_view_fast);

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        indicator = (CircleIndicator) rootView.findViewById(R.id.indicator);
        pray_image1 = new ArrayList<Integer>();
        pray_image1.add(1);
        pray_image1.add(2);

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        setupViewPager(mPager);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageSelected(int position) {
                //setupViewPager(mViewPager);
                if (position == 1) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //adapter.notifyDataSetChanged();
            }
        });
        indicator.setViewPager(mPager);
        if ((Splash.currentmin >= Splash.Nightmin) || (Splash.currentmin <= Splash.Daymin)) {
            img_back_view_fast.setBackgroundResource(R.mipmap.moon_up);
            pray_btn.setBackground(getResources().getDrawable(R.drawable.purple_btn));
            pray_btn.setPadding(30, 0, 30, 0);
        } else {
            img_back_view_fast.setBackgroundResource(R.mipmap.day_image);
            pray_btn.setBackground(getResources().getDrawable(R.drawable.yellow_btn));
            pray_btn.setPadding(30, 0, 30, 0);
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (pray_typ.size()>0) {
            isListenerAdded = false;
            pray_typ.remove(0);
            pray_typ.add("Fasts");
            Alerts.AlertPray(getActivity(), pray_typ, pray_image, count, getResources().getDrawable(R.drawable.purple_btn), Gravity.TOP, adap_home, "fasts");
        }  }

    private void setupViewPager(final ViewPager viewPager) {
        adapter = new HomeFragmentAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new HomeImageFrag(), "");
        adapter.addFragment(new ProgressBarFrag(), "");
        viewPager.setAdapter(adapter);
        viewPager.invalidate();

    }


    void getprayerPercent(String Userid) {
        datebase2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = datebase2.child(Globals.USER).child(Userid).child("Total_Qadha").getRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("total fast", dataSnapshot.getValue().toString());
                Globals.Totalprayers = dataSnapshot.getValue().toString();
                Globals.total_p = (Integer.parseInt(Globals.Totalprayers));
                Globals.averagesume = incassoMargherita(Globals.total_p);
                Log.e("average", Globals.averagesume + "");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    void getAll(String Userid, final Context c) {
        //showCommonDialog(getActivity(), "");
        datebase2 = FirebaseDatabase.getInstance().getReference();
        Query myTopPostsQuery = datebase2.child(Globals.FASTS).child(Userid).child(Globals.DATA);
        ref = myTopPostsQuery.getRef();
        Log.e("refrence get all", ref.toString());
  /*      if (isListenerAdded) {
            ref.removeEventListener(listener);
        }*/

        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PrayListData university = dataSnapshot.getValue(PrayListData.class);
                Log.e("string is", university.Pray_name);
                pray_typ.add(university.Pray_name);
                count.add(university.pray_Count);
                //list.add(university);
                Log.e("string is", "prayers " + pray_typ);
                if (pray_typ.size() == 1) {
                   // cancelDialog();
                      getprayerPercent(Globals.getUid());
                    adap_home = new HomeGridAdap(getActivity(), pray_typ, pray_image, count);
                    cat_home.setAdapter(adap_home);
                    adap_home.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                if (!isListenerAdded)
                {
                    isListenerAdded = true;
                    pray_typ.clear();
                    count.clear();
                    getAll(Globals.getUid(), getActivity());
                }
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
        /*if (!isListenerAdded) {
            ref.addChildEventListener(listener);
            isListenerAdded = true;
        }*/
        ref.addChildEventListener(listener);
    }


    public int incassoMargherita(int total) {
        int sum = 0;
        for (int i = 0; i < count.size(); i++) {
            sum += Integer.parseInt(count.get(i));
        }
        Globals.Totaldone_prayers = sum;

        int per = 100 * sum / total;

        return per;
    }


}

