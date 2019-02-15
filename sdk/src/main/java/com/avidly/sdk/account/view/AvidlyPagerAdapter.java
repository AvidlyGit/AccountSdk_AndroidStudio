package com.avidly.sdk.account.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AvidlyPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> mFragmentList = new ArrayList<>();
    ArrayList<String> mTitles = new ArrayList<>();

    public AvidlyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    public void addTitle(String title) {
        mTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList != null && !mFragmentList.isEmpty() ? mFragmentList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
