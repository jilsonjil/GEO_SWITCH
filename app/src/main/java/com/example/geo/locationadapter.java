package com.example.geo;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class locationadapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    @Override
    public int getCount() {
        return totalTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Location Based";
            case 1:
                return "Time Based";
        }
        return "";
    }

    public locationadapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                location_tab loctab = new location_tab();
                return loctab;
            case 1:
                time_tab timetab = new time_tab();
                return timetab;
            default:
                return null;
        }
    }

}
