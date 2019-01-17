package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avidly.sdk.account.adapter.BaseAdapter;
import com.avidly.sdk.account.adapter.GusetBindAdatper;
import com.avidly.sdk.account.business.LoginCenter;
import com.avidly.sdk.account.data.adapter.GuestBindData;
import com.sdk.avidly.account.R;

import java.util.ArrayList;
import java.util.List;

public class AccountGuestUserBindFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.avidly_user_guest_bind_layout, container, false);
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

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.avidly_usermanger_bind_listview);
        GusetBindAdatper adatper = new GusetBindAdatper(getContext());
        recyclerView.setAdapter(adatper);
        final int gridnum = LoginCenter.isScreenLandscape() ? 2 : 1;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), gridnum);
        recyclerView.setLayoutManager(layoutManager);
        fillGuestAdatper(adatper, gridnum > 1);

        adatper.setOnRecyclerViewItemClickListener(new BaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    private void fillGuestAdatper(GusetBindAdatper adatper, boolean isgrid) {
        List<GuestBindData> list = new ArrayList<>(3);
        list.add(createGuestBindData(getString(R.string.avidly_string_usermanger_bind_avidly),
                R.drawable.avidly_icon_user,
                GuestBindData.BIND_TYPE_AVIDLY,
                isgrid));

        list.add(createGuestBindData(getString(R.string.avidly_string_usermanger_bind_switch),
                R.drawable.avidly_icon_switch,
                GuestBindData.BIND_TYPE_SWITCH,
                isgrid));

        list.add(createGuestBindData(getString(R.string.avidly_string_usermanger_bind_other),
                R.drawable.avidly_icon_bind,
                GuestBindData.BIND_TYPE_OTHER,
                isgrid));

        adatper.setDataList(list);
    }

    private GuestBindData createGuestBindData(String text, int id, int type, boolean isgrid) {
        GuestBindData data = new GuestBindData();
        data.text = text;
        data.iconid = id;
        data.type = type;
        data.isgrid = isgrid;
        return data;
    }
}
