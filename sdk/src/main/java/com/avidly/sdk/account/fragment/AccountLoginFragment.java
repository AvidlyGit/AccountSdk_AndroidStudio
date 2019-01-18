package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avidly.sdk.account.base.Constants;
import com.avidly.sdk.account.listener.AccountLoginListener;
import com.avidly.sdk.account.view.AvidlyPagerAdapter;
import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/15.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountLoginFragment extends DialogFragment implements View.OnClickListener {
    private AccountLoginListener mCallback;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AvidlyPagerAdapter mPagerAdapter;

    private int mOperationType = Constants.LOGIN_TYPE_LOGIN;
    private static String OPERATION_KEY = "operation";

    public static AccountLoginFragment newInstance(int operationType) {
        AccountLoginFragment loginFragment = new AccountLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(OPERATION_KEY, operationType);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    public void setCallback(AccountLoginListener callback) {
        mCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avidly_fragment_login, container, false);
        View iconBack = view.findViewById(R.id.avidly_login_back);
        iconBack.setOnClickListener(this);

        mOperationType = getArguments().getInt(OPERATION_KEY);

        View bindLayout = view.findViewById(R.id.avidly_login_layout_bind);
        View notBindLayout = view.findViewById(R.id.avidly_login_layout_not_bind);
        
        if (mOperationType == Constants.LOGIN_TYPE_BIND) {
            bindLayout.setVisibility(View.VISIBLE);
            notBindLayout.setVisibility(View.GONE);

        } else {
            bindLayout.setVisibility(View.GONE);
            notBindLayout.setVisibility(View.VISIBLE);

            mTabLayout = view.findViewById(R.id.avidly_tab_layout);
            mViewPager = view.findViewById(R.id.avidly_tab_pager);
            mPagerAdapter = new AvidlyPagerAdapter(getChildFragmentManager());
            mPagerAdapter.addFragment(new AccountLoginSubFragment());
            mPagerAdapter.addTitle(getString(R.string.avidly_string_user_login));
            mPagerAdapter.addFragment(new AccountRegistSubFragment());
            mPagerAdapter.addTitle(getString(R.string.avidly_string_user_regist));
            mViewPager.setAdapter(mPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager, false);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (mCallback == null) {

            return;
        }

        if (id == R.id.avidly_login_back) {
            mCallback.onBackToHomePressed();
        }
    }
}


