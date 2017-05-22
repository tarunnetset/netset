package com.iqadha_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.Fragments.SignupOne;
import com.iqadha_app.Utils.ActionBar;
import com.iqadha_app.Utils.Alerts;
import com.iqadha_app.Utils.CommonMethods;

/**
 * Created by netset on 8/11/16.
 */
public class StepSignup extends FragmentActivity {
    Fragment fragmnt_signup_one;
    FragmentManager frag_manage;
    FragmentTransaction frag_trans;
    public static ActionBar action;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_frame_container);

        action = (ActionBar) findViewById(R.id.action);
        action.setFilterBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alerts.AlertInfo(StepSignup.this);
            }
        });
        fragmnt_signup_one = new SignupOne();
        frag_manage = getSupportFragmentManager();
        Globals.frag_ID=R.id.container;


        CommonMethods.SwitchFragements(frag_manage,fragmnt_signup_one);
    }
}
