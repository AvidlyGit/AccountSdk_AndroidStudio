package com.avidly.sdk.account.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sdk.avidly.account.R;


public class AccountLoginActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avidly_activity_login);

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction transaction = fragmentManager. beginTransaction();
//        transaction.replace(R.id.fragment_login, null);
//        transaction.commit();

    }
}
