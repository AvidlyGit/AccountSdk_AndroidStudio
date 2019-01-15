package com.avidly.sdk.account.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdk.avidly.account.R;

/**
 * Created by t.wang on 2019/1/14.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountLoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avidly_fragment_login, container, false);
        return view;
    }


}
