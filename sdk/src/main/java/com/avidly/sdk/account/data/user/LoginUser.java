package com.avidly.sdk.account.data.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginUser extends JsonData {

    List<Account> accounts = new ArrayList<>(5);

    private int loginedMode;// 当前登陆的帐号

    public boolean isActived; // 是否激活的帐号，即最后一次登陆帐号为活跃

    public boolean isNowLogined; // 当前是否已经登陆

    public String ggid; // game guest id

    public void addOrUpdateAccount(Account account) {
        if (null == account) {
            return;
        }
        Account a = findAccountByMode(account.mode);
        while (a != null) {
            accounts.remove(a);
            a = findAccountByMode(account.mode);
        }
        accounts.add(account);
    }

    public void setLoginedMode(int mode) {
        loginedMode = mode;
    }

    public Account findActivedAccount() {
        return findAccountByMode(loginedMode);
    }

    public Account findAccountByMode(int mode) {
        if (accounts.size() == 0) {
            return null;
        }
        for (Account account : accounts) {
            if (mode == account.mode) {
                return account;
            }
        }
        return null;
    }

    @Override
    protected void parseJsonString(JSONObject jsonObject) {
        super.parseJsonString(jsonObject);
        if (null != jsonObject) {
            ggid = jsonObject.optString("ggid");
            loginedMode = jsonObject.optInt("loginedMode");
            isActived = jsonObject.optBoolean("actived");
            JSONArray array = jsonObject.optJSONArray("accounts");
            if (array != null) {
                int size = array.length();
                for (int i = 0; i < size; i++) {
                    try {
                        JSONObject o = array.getJSONObject(i);
                        if (o != null) {
                            Account account = new Account();
                            account.parseJsonString(o);
                            accounts.add(account);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected JSONObject converToJsonObject(JSONObject jsonObject) {
        JSONObject o = super.converToJsonObject(jsonObject);
        try {
            o.put("actived", isActived);
            o.put("loginedMode", loginedMode);
            o.put("ggid", ggid);
            if (accounts.size() > 0) {
                JSONArray array = new JSONArray();
                for (Account account : accounts) {
                    array.put(account.converToJsonObject(null));
                }
                o.put("accounts", array);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return o;
    }
}
