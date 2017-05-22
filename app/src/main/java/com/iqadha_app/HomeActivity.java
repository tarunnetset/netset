package com.iqadha_app;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.Fragments.Chart_LocationFragment;
import com.iqadha_app.Fragments.FastFragment;
import com.iqadha_app.Fragments.HomeFragment;
import com.iqadha_app.Fragments.InfoFragment;
import com.iqadha_app.Fragments.SettingFragment;
import com.iqadha_app.Utils.ActionBar;
import com.iqadha_app.Utils.CommonMethods;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


/**
 * Created by netset on 3/10/16.
 */

public class HomeActivity extends FragmentActivity {
    BottomBar bottomBar;
    public static FragmentManager fragmentManager;
    public static ActionBar action;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    //  LocationActivity locationActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.light_grey));
        }
        setContentView(R.layout.act_home);
        action = (ActionBar) findViewById(R.id.action_bar);
        action.setVisibility(View.VISIBLE);
        action.ActionBackgroundColor("#0Db3b3b3");
        action.setTitle("iQadha");
        action.setFilterBtnVisibility(View.INVISIBLE);
        Globals.frag_ID = R.id.contentContainer;
        fragmentManager = getSupportFragmentManager();
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setGravity(Gravity.CENTER);
        bottomBar.setDefaultTabPosition(0);
        bottomBar.setTabTitleTextAppearance(0);
        Globals.Tab_pos = 0;
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                switch (tabId) {
                    case R.id.tab_home:
                        Globals.Tab_pos = 0;
                        Globals.FRAGMENT = new HomeFragment();

                        break;
                    case R.id.tab_fast:
                        Globals.FRAGMENT = new FastFragment();
                        Globals.Tab_pos = 1;
                        break;
                    case R.id.tab_loc:
                        Globals.FRAGMENT = new Chart_LocationFragment();
                        Globals.Tab_pos = 2;
                        break;
                    case R.id.tab_info:
                        Globals.FRAGMENT = new InfoFragment();
                        Globals.Tab_pos = 3;
                        break;

                    case R.id.tab_setting:
                        Globals.FRAGMENT = new SettingFragment();
                        Globals.Tab_pos = 4;
                        break;
                    default:
                        break;
                }
                CommonMethods.SwitchFragements(fragmentManager, Globals.FRAGMENT);
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                finish();
            } else {
                try {
                    // UtilsMethods.ShowSnackBar(getCurrentFocus(),"Tap back button again in order to exit");
                } catch (Exception e) {
                    Toast.makeText(HomeActivity.this, "Tap back button again in order to exit", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
            mBackPressed = System.currentTimeMillis();

        }

    }


}
