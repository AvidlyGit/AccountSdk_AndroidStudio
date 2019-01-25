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
import com.avidly.sdk.account.adapter.UserAccountBindAdatper;
import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.business.LoginCenter;
import com.avidly.sdk.account.data.adapter.UserBindData;
import com.avidly.sdk.account.data.user.Account;
import com.avidly.sdk.account.data.user.LoginUser;
import com.avidly.sdk.account.data.user.LoginUserManager;
import com.sdk.avidly.account.R;

import java.util.ArrayList;
import java.util.List;

public class AccountUserBindFragment extends Fragment {

    private BaseAdapter.onRecyclerViewItemClickListener itemClickListener;

    private UserAccountBindAdatper adatper;


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
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void setItemClickListener(BaseAdapter.onRecyclerViewItemClickListener clickListener) {
        itemClickListener = clickListener;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.avidly_useraccount_bind_listview);
        adatper = new UserAccountBindAdatper(getContext());
        recyclerView.setAdapter(adatper);
        if (!LoginCenter.isScreenLandscape() && !LoginCenter.isScreenPortrait()) {
            LoginCenter.checkScreenOrietation(getActivity());
        }
        freshAdapter();

        adatper.setOnRecyclerViewItemClickListener(new BaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(Object data, int position) {
                LogUtils.i("onItemClick type:" + position);
                //itemClick((UserBindData) data, position);
                if (null != itemClickListener) {
                    itemClickListener.onItemClick(data, position);
                }
            }
        });
    }

    public void freshAdapter() {
        if (getView() != null) {
            final int gridnum = LoginCenter.isScreenLandscape() ? 2 : 1;
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), gridnum);
            RecyclerView recyclerView = getView().findViewById(R.id.avidly_useraccount_bind_listview);
            recyclerView.setLayoutManager(layoutManager);
            fillGuestAdatper(adatper, gridnum > 1);
        }
    }


    private boolean isBindAccount(int mode) {
        LoginUser user = LoginUserManager.getCurrentActiveLoginUser();
        if (user == null) {
            return false;
        }

        Account account = user.findAccountByMode(mode);
        if (account == null) {
            return false;
        }

        return account.isBinded;
    }

    private void fillGuestAdatper(UserAccountBindAdatper adatper, boolean isgrid) {
        List<UserBindData> list = new ArrayList<>(3);

        boolean isbind = isBindAccount(Account.ACCOUNT_MODE_FACEBOOK);

        list.add(createGuestBindData(getString(R.string.avidly_string_user_bind_account_facebook),
                getString(isbind ? R.string.avidly_string_user_binded_status : R.string.avidly_string_user_unbind_status),
                R.drawable.avidly_facebook_logo,
                Account.ACCOUNT_MODE_FACEBOOK,
                isgrid,
                false));

        isbind = isBindAccount(Account.ACCOUNT_MODE_TWITTER);
        list.add(createGuestBindData(getString(R.string.avidly_string_user_bind_account_twitter),
                getString(isbind ? R.string.avidly_string_user_binded_status : R.string.avidly_string_user_unbind_status),
                R.drawable.avidly_twitter_logo,
                Account.ACCOUNT_MODE_TWITTER,
                isgrid,
                false));

        isbind = isBindAccount(Account.ACCOUNT_MODE_GOOGLEPLAY);
        list.add(createGuestBindData(getString(R.string.avidly_string_user_bind_account_googleplay),
                getString(isbind ? R.string.avidly_string_user_binded_status : R.string.avidly_string_user_unbind_status),
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
