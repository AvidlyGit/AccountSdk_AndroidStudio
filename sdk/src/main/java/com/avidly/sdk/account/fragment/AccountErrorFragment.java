package com.avidly.sdk.account.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import account.avidly.com.sdk.R;

/**
 * Created by t.wang on 2019/1/15.
 * <p>
 * Copyright © 2018 Adrealm. All rights reserved.
 */
public class AccountErrorFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avidly_fragment_error, container, false);
        return view;
    }

}
