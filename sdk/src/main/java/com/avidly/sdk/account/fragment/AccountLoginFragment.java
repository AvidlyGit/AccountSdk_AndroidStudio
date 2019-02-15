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

public class AccountLoginFragment extends DialogFragment implements View.OnClickListener, AccountLoginListener {
    private AccountLoginListener mLoginListener;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AvidlyPagerAdapter mPagerAdapter;

    private int mSubFragment = Constants.SUB_FRAGMENT_TYPE_LOGIN;

    public static AccountLoginFragment newInstance(int subFragment) {
        AccountLoginFragment loginFragment = new AccountLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.LOGIN_SUB_FRAGMENT_TYPE, subFragment);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    public void setLoginListener(AccountLoginListener listener) {
        mLoginListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avidly_fragment_login, container, false);
        View iconBack = view.findViewById(R.id.avidly_login_back);
        iconBack.setOnClickListener(this);

        mSubFragment = getArguments().getInt(Constants.LOGIN_SUB_FRAGMENT_TYPE);

        View bindLayout = view.findViewById(R.id.avidly_login_layout_bind);
        View notBindLayout = view.findViewById(R.id.avidly_login_layout_not_bind);

        if (mSubFragment == Constants.SUB_FRAGMENT_TYPE_BIND) {
            bindLayout.setVisibility(View.VISIBLE);
            notBindLayout.setVisibility(View.GONE);

            AccountBindSubFragment bindSubFragment = (AccountBindSubFragment) getChildFragmentManager().findFragmentById(R.id.avidly_new_user_bind);
            bindSubFragment.setLoginListener(this);

        } else {
            bindLayout.setVisibility(View.GONE);
            notBindLayout.setVisibility(View.VISIBLE);

            mTabLayout = view.findViewById(R.id.avidly_tab_layout);
            mViewPager = view.findViewById(R.id.avidly_tab_pager);
            mPagerAdapter = new AvidlyPagerAdapter(getChildFragmentManager());
            AccountLoginSubFragment loginSubFragment = new AccountLoginSubFragment();
            loginSubFragment.setLoginListener(this);
            mPagerAdapter.addFragment(loginSubFragment);
            mPagerAdapter.addTitle(getString(R.string.avidly_string_user_login));
            AccountRegistSubFragment registSubFragment = new AccountRegistSubFragment();
            registSubFragment.setLoginListener(this);
            mPagerAdapter.addFragment(registSubFragment);
            mPagerAdapter.addTitle(getString(R.string.avidly_string_user_regist));
            mViewPager.setAdapter(mPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager, false);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.avidly_login_back) {
            onBackToHomePressed();
        }
    }

    @Override
    public void onBackToHomePressed() {
        if (mLoginListener != null) {
            mLoginListener.onBackToHomePressed();
        }
    }

    @Override
    public void onLoginErrorOccured(String message) {
        if (mLoginListener != null) {
            mLoginListener.onLoginErrorOccured(message);
        }
    }

    @Override
    public void onAccountLoginClicked(String email, String password) {
        if (mLoginListener != null) {
            mLoginListener.onAccountLoginClicked(email, password);
        }
    }

    @Override
    public void onAccountRegistClicked(String email, String password) {
        if (mLoginListener != null) {
            mLoginListener.onAccountRegistClicked(email, password);
        }
    }

    @Override
    public void onAccountBindClicked(String email, String password) {
        if (mLoginListener != null) {
            mLoginListener.onAccountBindClicked(email, password);
        }
    }

    @Override
    public void onForgotPasswordClicked() {
        if (mLoginListener != null) {
            mLoginListener.onForgotPasswordClicked();
        }
    }

    @Override
    public void onReadProtocolClicked() {
        if (mLoginListener != null) {
            mLoginListener.onReadProtocolClicked();
        }
    }
}


