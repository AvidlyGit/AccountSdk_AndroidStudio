package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.avidly.sdk.account.base.utils.LogUtils;
import com.avidly.sdk.account.base.utils.Utils;
import com.avidly.sdk.account.request.HttpCallback;
import com.avidly.sdk.account.request.HttpRequest;
import com.avidly.sdk.account.request.URLConstant;
import com.sdk.avidly.account.R;

public class AccountUserLookupPwdFragment extends BaseFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.avidly_fragment_user_lookup_pwd, container, false);
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

    @Override
    public boolean onBackKeyDown() {
        return false;
    }

    private void initView(final View view) {

        view.findViewById(R.id.avidly_user_manager_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onBackKeyDown()) {
                    getActivity().finish();
                }
            }
        });

        TextView textView = view.findViewById(R.id.avidly_user_common_title_textview);
        textView.setText(R.string.avidly_string_userpwd_lookup_btn);

        view.findViewById(R.id.avidly_fragment_user_lookup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = view.findViewById(R.id.avidly_editor_email_address);
                String address = editText.getText().toString();
                checkAndSubmit(address);
            }
        });
    }

    private void checkAndSubmit(String address) {
        if (TextUtils.isEmpty(address)) {
            Utils.showToastTip(getContext(), R.string.avidly_string_user_lookup_email_tip, true);
            return;
        }


        if (!Utils.validEmail1(address)) {
            Utils.showToastTip(getContext(), R.string.avidly_string_email_format_error, true);
            return;
        }

        String url = URLConstant.retrievePwd(address);
        LogUtils.i("retrievePwd url:" + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                LogUtils.i("onResponseSuccess:" + result);
                Utils.showToastTip(getContext(), R.string.avidly_string_user_lookup_eamil_send_success, true);
                hideLoadingUI();
            }

            @Override
            public void onResponedFail(Throwable e, int code) {
                LogUtils.i("onResponedFail, exception:" + e + ", code:" + code);
                Utils.showToastTip(getContext(), R.string.avidly_string_user_lookup_eamil_send_fail, true);
                hideLoadingUI();
            }
        });

        showLoadingUI();

    }

    private void showLoadingUI() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().findViewById(R.id.avidly_loading_layout).setVisibility(View.VISIBLE);
            }
        });

    }

    private void hideLoadingUI() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().findViewById(R.id.avidly_loading_layout).setVisibility(View.GONE);
            }
        });
    }
}
