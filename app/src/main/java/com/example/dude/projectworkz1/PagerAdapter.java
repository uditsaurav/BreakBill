package com.example.dude.projectworkz1;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;

    public PagerAdapter(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position) {
            case 0:
                fragment = new tab1();
                break;
            case 1:
                fragment = new tab2();
                break;
            case 2:
                fragment = new tab3();
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}

