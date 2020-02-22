package com.example.tripstory;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES =
            new String[] { "Profile", "Jobs", "Search" };
    private final Context mContext;
    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ProfileFragment.newInstance();
            case 1:
                return JobsFragment.newInstance();
            case 2:
                return SearchFragment.newInstance();
            default:
                return ProfileFragment.newInstance();
        }
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}