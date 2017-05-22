package com.iqadha_app.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iqadha_app.Fragments.AboutFragment;
import com.iqadha_app.Fragments.ContactFragments;
import com.iqadha_app.Fragments.FAQFragments;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AboutFragment tab1 = new AboutFragment();
                return tab1;
            case 1:
                FAQFragments tab2 = new FAQFragments();
                return tab2;
            case 2:
                ContactFragments tab3 = new ContactFragments();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}