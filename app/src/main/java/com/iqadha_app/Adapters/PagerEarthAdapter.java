package com.iqadha_app.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iqadha_app.Fragments.ChartFragment;
import com.iqadha_app.Fragments.MapFragment;

/**
 * Created by netset on 10/11/16.
 */

public class PagerEarthAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerEarthAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ChartFragment tab1 = new ChartFragment();
                return tab1;
            case 1:
                MapFragment tab2 = new MapFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
