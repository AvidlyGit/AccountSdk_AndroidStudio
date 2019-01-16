package com.avildy.account.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import account.avidly.com.accountsdk.R;

public class TestFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.testfragment, container, false);

        view.findViewById(R.id.click_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AvidlyAccountSdk.accountLogin(getContext());
            }
        });

        return view;
    }
}
