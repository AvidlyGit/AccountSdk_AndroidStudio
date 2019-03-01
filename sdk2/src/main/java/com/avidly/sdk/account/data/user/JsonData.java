package com.avidly.sdk.account.data.user;

import org.json.JSONObject;

 public class JsonData implements IJsonData {
    @Override
    public void thisFromString(String json) {
        if (json == null) {
            return;
        }

        try {
            JSONObject object = new JSONObject(json);
            parseJsonString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String thisToString() {
        return converToJsonObject(null).toString();
    }

    protected void parseJsonString(JSONObject jsonObject) {
        if (null == jsonObject) {
            return;
        }
    }

    protected JSONObject converToJsonObject(JSONObject jsonObject) {
        if (null == jsonObject) {
            jsonObject = new JSONObject();
        }

        return jsonObject;
    }
}
