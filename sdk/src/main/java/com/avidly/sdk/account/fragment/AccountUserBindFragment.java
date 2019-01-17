package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avidly.sdk.account.adapter.BaseAdapter;
import com.avidly.sdk.account.adapter.UserAccountBindAdatper;
import com.avidly.sdk.account.business.LoginCenter;
import com.avidly.sdk.account.data.adapter.UserBindData;
import com.avidly.sdk.account.data.user.Account;
import com.sdk.avidly.account.R;

import java.util.ArrayList;
import java.util.List;

public class AccountUserBindFragment extends Fragment {

    private BaseAdapter.onRecyclerViewItemClickListener itemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.avidly_fragment_user_account_bind, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setItemClickListener(BaseAdapter.onRecyclerViewItemClickListener clickListener) {
        itemClickListener = clickListener;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.avidly_useraccount_bind_listview);
        UserAccountBindAdatper adatper = new UserAccountBindAdatper(getContext());
        recyclerView.setAdapter(adatper);
        if (!LoginCenter.isScreenLandscape() && !LoginCenter.isScreenPortrait()) {
            LoginCenter.checkScreenOrietation(getActivity());
        }
        final int gridnum = LoginCenter.isScreenLandscape() ? 2 : 1;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), gridnum);
        recyclerView.setLayoutManager(layoutManager);
        fillGuestAdatper(adatper, gridnum > 1);

        adatper.setOnRecyclerViewItemClickListener(new BaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(Object data, int position) {
                Log.d("xxxx", "onItemClick type:" + position);
                if (null != itemClickListener) {
                    itemClickListener.onItemClick(data, position);
                }
            }
        });
    }

    private void fillGuestAdatper(UserAccountBindAdatper adatper, boolean isgrid) {
        List<UserBindData> list = new ArrayList<>(3);


        list.add(createGuestBindData(getString(R.string.avidly_string_user_bind_account_facebook),
                getString(R.string.avidly_string_user_unbind_status),
                R.drawable.avidly_facebook_logo,
                Account.ACCOUNT_MODE_FACEBOOK,
                isgrid,
                false));


        list.add(createGuestBindData(getString(R.string.avidly_string_user_bind_account_twitter),
                getString(R.string.avidly_string_user_unbind_status),
                R.drawable.avidly_twitter_logo,
                Account.ACCOUNT_MODE_TWITTER,
                isgrid,
                false));

        list.add(createGuestBindData(getString(R.string.avidly_string_user_bind_account_googleplay),
                getString(R.string.avidly_string_user_unbind_status),
                R.drawable.avidly_google_logo,
                Account.ACCOUNT_MODE_GOOGLEPLAY,
                isgrid,
                false));

        adatper.setDataList(list);
    }

    private UserBindData createGuestBindData(String text, String status, int id, int type, boolean isgrid, boolean isbinded) {
        UserBindData data = new UserBindData();
        data.text = text;
        data.status = status;
        data.iconid = id;
        data.accountMode = type;
        data.isgrid = isgrid;
        data.isbinded = isbinded;
        return data;
    }
}
