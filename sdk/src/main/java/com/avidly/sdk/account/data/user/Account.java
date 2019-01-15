package com.avidly.sdk.account.data.user;

import org.json.JSONException;
import org.json.JSONObject;

public class Account extends JsonData {

    public static final int ACCOUNT_MODE_GUEST = 1;
    public static final int ACCOUNT_MODE_AVIDLY = 2;
    public static final int ACCOUNT_MODE_FACEBOOK = 3;
    public static final int ACCOUNT_MODE_GOOGLE = 4;
    public static final int ACCOUNT_MODE_TWITTER = 6;

    public int mode;

    public String accountName; // 帐号

    public String accountPwd; // 密码

    @Override
    protected void parseJsonString(JSONObject jsonObject) {
        super.parseJsonString(jsonObject);
        try {
            this.accountName = jsonObject.getString("accountName");
            this.accountPwd = jsonObject.getString("accountPwd");
            this.mode = jsonObject.optInt("mode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject converToJsonObject(JSONObject jsonObject) {
        JSONObject object = super.converToJsonObject(jsonObject);
        try {
            object.put("accountName", accountName);
            object.put("accountPwd", accountPwd);
            object.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
