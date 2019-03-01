package com.avidly.sdk.account.data.user;

import org.json.JSONObject;

public class Account extends JsonData {

    public static final int ACCOUNT_MODE_GUEST = 1;
    public static final int ACCOUNT_MODE_AVIDLY = 2;
    public static final int ACCOUNT_MODE_FACEBOOK = 3;
    public static final int ACCOUNT_MODE_GOOGLEPLAY = 4;
    public static final int ACCOUNT_MODE_TWITTER = 6;

    public int mode;

    public String accountName=""; // 帐号

    public String accountPwd; // 密码

    public String nickname; // 昵称

    public boolean isBinded; // 是否绑定到ggid

    @Override
    protected void parseJsonString(JSONObject jsonObject) {
        super.parseJsonString(jsonObject);
        try {
            this.accountName = jsonObject.optString("accountName");
            this.nickname = jsonObject.optString("nickname");
            this.accountPwd = jsonObject.optString("accountPwd");
            this.mode = jsonObject.optInt("mode");
            this.isBinded = jsonObject.optBoolean("isBinded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject converToJsonObject(JSONObject jsonObject) {
        JSONObject object = super.converToJsonObject(jsonObject);
        try {
            object.put("accountName", accountName);
            object.put("nickname", nickname);
            object.put("accountPwd", accountPwd);
            object.put("mode", mode);
            object.put("isBinded", isBinded);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public String toString() {
        return "Account{" +
                "mode=" + mode +
                ", accountName='" + accountName + '\'' +
                ", accountPwd='" + accountPwd + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isBinded=" + isBinded +
                '}';
    }
}
