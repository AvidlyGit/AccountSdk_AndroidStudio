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
import com.avidly.sdk.account.adapter.UserManagerAdatper;
import com.avidly.sdk.account.business.LoginCenter;
import com.avidly.sdk.account.data.adapter.UserOperationData;
import com.sdk.avidly.account.R;

import java.util.ArrayList;
import java.util.List;

public class AccountUserManagerFragment extends Fragment {

    private BaseAdapter.onRecyclerViewItemClickListener itemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.avidly_fragment_user_account_manager, container, false);
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
        RecyclerView recyclerView = view.findViewById(R.id.avidly_usermanger_listview);
        UserManagerAdatper adatper = new UserManagerAdatper(getContext());
        recyclerView.setAdapter(adatper);
        if (!LoginCenter.isScreenLandscape() && !LoginCenter.isScreenPortrait()) {
            LoginCenter.checkScreenOrietation(getActivity());
        }
        final int gridnum = LoginCenter.isScreenLandscape() ? 2 : 1;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), gridnum);
        recyclerView.setLayoutManager(layoutManager);
        fillGuestAdatper(adatper, gridnum > 1, false);

        adatper.setOnRecyclerViewItemClickListener(new BaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(Object data, int position) {
                Log.d("xxxx", "onItemClick type:" + position);
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(data, position);
                }
            }
        });
    }

    private void fillGuestAdatper(UserManagerAdatper adatper, boolean isgrid, boolean isguest) {
        List<UserOperationData> list = new ArrayList<>(3);

        if (isguest) {
            list.add(createGuestBindData(getString(R.string.avidly_string_usermanger_bind_avidly),
                    R.drawable.avidly_icon_user,
                    UserOperationData.USER_OPERATION_TYPE_AVIDLY,
                    isgrid));
        } else {
            list.add(createGuestBindData(getString(R.string.avidly_string_usermanger_bind_alter_pwd),
                    R.drawable.avidly_icon_change_password,
                    UserOperationData.USER_OPERATION_TYPE_CHANGE_PWD,
                    isgrid));
        }

        list.add(createGuestBindData(getString(R.string.avidly_string_usermanger_bind_switch),
                R.drawable.avidly_icon_switch,
                UserOperationData.USER_OPERATION_TYPE_SWITCH,
                isgrid));

        list.add(createGuestBindData(getString(R.string.avidly_string_usermanger_bind_other),
                R.drawable.avidly_icon_bind,
                UserOperationData.USER_OPERATION_TYPE_BIND_OTHER,
                isgrid));

        adatper.setDataList(list);
    }

    private UserOperationData createGuestBindData(String text, int id, int type, boolean isgrid) {
        UserOperationData data = new UserOperationData();
        data.text = text;
        data.iconid = id;
        data.type = type;
        data.isgrid = isgrid;
        return data;
    }
}
