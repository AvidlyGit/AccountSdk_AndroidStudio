package com.avidly.sdk.account.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.avidly.sdk.account.base.utils.Utils;
import com.avidly.sdk.account.request.HttpCallback;
import com.avidly.sdk.account.request.HttpRequest;
import com.avidly.sdk.account.request.URLConstant;
import com.sdk.avidly.account.R;

public class AccountUserPwdAlterFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.avidly_fragment_user_change_pwd, container, false);
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

    private void initView(final View rootview) {
        rootview.findViewById(R.id.avidly_fragment_user_change_pwd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = rootview.findViewById(R.id.avidly_editor_chagne_old_pwd);
                String oldpwd = editText.getText().toString();
                editText = rootview.findViewById(R.id.avidly_editor_chagne_new_pwd);
                String newpwd1 = editText.getText().toString();
                editText = rootview.findViewById(R.id.avidly_editor_chagne_new_pwd2);
                String newpwd2 = editText.getText().toString();
                checkAndSubmit(oldpwd, newpwd1, newpwd2);
            }
        });
    }

    private void checkAndSubmit(String odlpwd, String newpwd1, String newpwd2) {
        if (TextUtils.isEmpty(odlpwd)) {
            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_empty_oldpwd, true);
            return;
        }


        if (TextUtils.isEmpty(newpwd1)) {
            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_empty_newpwd, true);
            return;
        }

        if (newpwd1.length() < 6) {
            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_short_length, true);
            return;
        }

        if (newpwd1.length() >= 16) {
            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_long_lenght, true);
            return;
        }

        if (TextUtils.isEmpty(newpwd2)) {
            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_empty_newpwd2, true);
            return;
        }

        if (!newpwd2.equals(newpwd1)) {
            Utils.showToastTip(getContext(), R.string.avidly_string_user_alter_pwd_wrong_newpwd2, true);
            return;
        }

        odlpwd = Utils.textOfMd5(odlpwd);
        newpwd1 = Utils.textOfMd5(newpwd1);
        String url = URLConstant.getAlterPwdAPI(odlpwd, newpwd1);
        Log.i("xxxx", "alter pwd url:" + url);
        HttpRequest.requestHttpByPost(url, null, new HttpCallback<String>() {
            @Override
            public void onResponseSuccess(String result) {
                Log.i("xxxx", "onResponseSuccess:" + result);
            }

            @Override
            public void onResponedFail(Throwable e, int code) {
                Log.i("xxxx", "onResponedFail, exception:" + e + ", code:" + code);
            }
        });

    }

}
