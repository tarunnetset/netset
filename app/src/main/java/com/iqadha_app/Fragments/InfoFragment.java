package com.iqadha_app.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iqadha_app.Adapters.PagerAdapter;
import com.iqadha_app.AsynchClasses.CommonFunctions;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.FireBaseModel.UserSignup;
import com.iqadha_app.HomeActivity;
import com.iqadha_app.R;
import com.iqadha_app.Utils.Alerts;

import static com.iqadha_app.Utils.Alerts.cancelDialog;

/**
 * Created by netset on 9/11/16.
 */

public class InfoFragment extends Fragment {

    View rootView;
    public static String language;
     ViewPager viewPager;
     PagerAdapter adapter;

     TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.tab_information, container, false);

          tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        HomeActivity.action.setTitle("INFORMATION");
        HomeActivity.action.setMenuButtonVisibility(View.INVISIBLE);
        tabLayout.addTab(tabLayout.newTab().setText("ABOUT"));
        tabLayout.addTab(tabLayout.newTab().setText("FAQ"));
        tabLayout.addTab(tabLayout.newTab().setText("CONTACT"));
         viewPager = (ViewPager) rootView.findViewById(R.id.pager);
          adapter = new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount());

        if( CommonFunctions.getConnectivityStatus(getActivity())) {
            getLanguage(Globals.getUid());
        }else {
            Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
        }


        return rootView;
    }





    void getLanguage(final String Userid) {
        Alerts.showCommonDialog(getActivity(), "");
        DatabaseReference datebase2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = datebase2.child(Globals.USER).child(Userid).child("UserProfile").getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("datasnap shot", dataSnapshot.toString());
                UserSignup useris = dataSnapshot.getValue(UserSignup.class);
                language = useris.Language;
                Log.e("language===", language);
                viewPager.setAdapter(adapter);
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.green));
                //  tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                tabLayout.setTabTextColors(getResources().getColor(R.color.light_grey_new), getResources().getColor(R.color.green));
                changeTabsFont(tabLayout);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                cancelDialog();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void changeTabsFont(TabLayout tabLayout) {


        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Semibold.otf");
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int k = 0; k < tabChildsCount; k++) {
                View tabViewChild = vgTab.getChildAt(k);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(tf, Typeface.NORMAL);
                }
            }
        }
    }

}
