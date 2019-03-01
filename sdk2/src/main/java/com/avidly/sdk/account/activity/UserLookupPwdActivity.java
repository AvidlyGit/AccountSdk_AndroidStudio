package com.avidly.sdk.account.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.avidly.sdk.account.fragment.BaseFragment;
import com.sdk.avidly.account.R;

import static android.view.KeyEvent.KEYCODE_BACK;

public class UserLookupPwdActivity extends BaseEditorActivity {

    private BaseFragment baseFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avidly_activity_user_lookup_pwd_layout);
        baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.avidly_fragment_user_lookup_pwd);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && baseFragment != null) {
            if (baseFragment.onBackKeyDown()) {
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }


}
