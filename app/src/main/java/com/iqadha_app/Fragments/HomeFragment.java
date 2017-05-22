package com.iqadha_app.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
import android.widget.ProgressBar;

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

import static com.iqadha_app.Utils.Alerts.cancelDialog;
import static com.iqadha_app.Utils.Alerts.showCommonDialog;

/**
 * Created by netset on 9/11/16.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    GridView cat_home;
    Button pray_btn;
    View rootView;
    ArrayList<String> pray_typ, countprayers;
    ArrayList<Integer> pray_image, pray_image1;
    //ViewPagerAdapterPager adap;
    //  ImageView image_ll;
    ViewPager mPager;
    public static int total_pray, count;
    CircleIndicator indicator;
    HomeFragmentAdapter adapter;
    HomeGridAdap adap_home;
    private DatabaseReference datebase2;
    DatabaseReference ref;
    ChildEventListener listener;
    boolean isListenerAdded = false;
    ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home, container, false);
        HomeActivity.action.ActionBackgroundColor("#00000000");
        HomeActivity.action.setMenuButtonVisibility(View.INVISIBLE);
        cat_home = (GridView) rootView.findViewById(R.id.cat_home);
        pray_btn = (Button) rootView.findViewById(R.id.pray_btn);
        indicator = (CircleIndicator) rootView.findViewById(R.id.indicator);
        progress = (ProgressBar) rootView.findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        pray_image1 = new ArrayList<Integer>();
        pray_image1.add(1);
        pray_image1.add(2);
        ImageView img_back_view = (ImageView) rootView.findViewById(R.id.img_back_view);
        pray_typ = new ArrayList<String>();
        countprayers = new ArrayList<String>();
        pray_image = new ArrayList<Integer>();
        if ((Splash.currentmin >= Splash.Nightmin) || (Splash.currentmin <= Splash.Daymin)) {
            img_back_view.setBackgroundResource(R.mipmap.moon_up);
            pray_btn.setBackground(getResources().getDrawable(R.drawable.purple_btn));
            pray_btn.setPadding(20, 0, 20, 0);
        } else {
            img_back_view.setBackgroundResource(R.mipmap.day_image);
            pray_btn.setBackground(getResources().getDrawable(R.drawable.yellow_btn));
            pray_btn.setPadding(20, 0, 20, 0);
        }
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Charts"));
        tabLayout.addTab(tabLayout.newTab().setText("Live"));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.green));
        tabLayout.setVisibility(View.INVISIBLE);
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        setupViewPager(mPager);
        indicator.setViewPager(mPager);
        HomeActivity.action.setTitle("iQadha");
        pray_image.add(R.mipmap.fajr);
        pray_image.add(R.mipmap.zuhr);
        pray_image.add(R.mipmap.asr);
        pray_image.add(R.mipmap.magrib);
        pray_image.add(R.mipmap.isha);
        pray_image.add(R.mipmap.witr);
        //getAll(Globals.getUid(), getActivity());
       if( CommonFunctions.getConnectivityStatus(getActivity())) {
            getallPrayers(Globals.getUid(), getActivity());
        }else {
           Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
       }
        pray_btn.setOnClickListener(this);
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
        return rootView;
    }


    void getprayerPercent(String Userid) {
        datebase2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = datebase2.child(Globals.USER).child(Userid).child("Total_Qadha").getRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Globals.Totalprayers = dataSnapshot.getValue().toString();
                Globals.setUserTotalDayTillBaligh(getActivity(),Globals.Totalprayers);
                Globals.total_p = (Integer.parseInt(Globals.Totalprayers)) * 6;
                Globals.averagesume = incassoMargherita(Globals.total_p);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public int incassoMargherita(int total) {
        int sum = 0;
        Globals.Totaldone_prayers = 0;
        for (int i = 0; i < 6; i++) {
            sum += Integer.parseInt(countprayers.get(i));
        }
        Globals.Totaldone_prayers = sum;
        Log.e("Totaldone_prayers ", Globals.Totaldone_prayers + "");
        int per = 100 * sum / total;

        return per;
    }


    @Override
    public void onClick(View view) {

            isListenerAdded = false;
            Alerts.AlertPray(getActivity(), pray_typ, pray_image, countprayers, getResources().getDrawable(R.drawable.yellow_btn), Gravity.TOP, adap_home, "prayers");
        }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("main home", "Home fragment");
    }

    private void setupViewPager(final ViewPager viewPager) {
        adapter = new HomeFragmentAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new HomeImageFrag(), "");
        adapter.addFragment(new ProgressBarFrag(), "");
        viewPager.setAdapter(adapter);
        viewPager.invalidate();
    }


    void getallPrayers(String user_id, final Activity c) {
        showCommonDialog(getActivity(), "");
        datebase2 = FirebaseDatabase.getInstance().getReference();
        Query myTopPostsQuery = datebase2.child(Globals.PRAYERS).child(user_id).child(Globals.DATA);
        ref = myTopPostsQuery.getRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total_pray = (int) dataSnapshot.getChildrenCount();
                count = 0;
                ref.orderByKey().addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        count++;
                        PrayListData university = dataSnapshot.getValue(PrayListData.class);
                        pray_typ.add(university.Pray_name);
                        countprayers.add(university.pray_Count);

                        Log.e("total_pray",total_pray+"  countprayers "+countprayers.size());
                        if ( total_pray==countprayers.size()) {
                            cancelDialog();
                            Log.e("averagesume is", Globals.averagesume + "");
                            adap_home = new HomeGridAdap(getActivity(), pray_typ, pray_image, countprayers);
                            cat_home.setAdapter(adap_home);
                            adap_home.notifyDataSetChanged();
                            getprayerPercent(Globals.getUid());
                        }else if (countprayers.size()>6)
                        {
                            cancelDialog();
                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        if (!isListenerAdded) {
                            isListenerAdded = true;
                            pray_typ.clear();
                            countprayers.clear();
                            getallPrayers(Globals.getUid(), c);
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
                        Log.e("error===", databaseError.toString());
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
