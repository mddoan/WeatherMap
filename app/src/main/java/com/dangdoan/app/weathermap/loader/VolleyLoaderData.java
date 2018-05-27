package com.dangdoan.app.weathermap.loader;

import android.text.TextUtils;

import org.json.JSONObject;

public class VolleyLoaderData {
    private JSONObject mData;
    private Meta metaData;
    private String payLoad;

    public VolleyLoaderData(JSONObject response){
        mData = response;
        metaData = readMeta();
        payLoad = readPayLoad();

    }

    public static class Meta {
        public int errorCode = -1;
        public String errorMsg = null;
        public int statusCode = -1;
        public String statusMsg = null;
        public String baseUrl = null;
        public long totalCount = 0;


        public boolean isSuccess() {
            return errorCode == 0;
        }
        public boolean isSuccessful(){
            return (statusCode == 0 && statusMsg.equalsIgnoreCase("OK"));
        }
    }

    private Meta readMeta() {
        if (mData == null) { return null; }

        Meta meta = new Meta();
        JSONObject metaJson = mData.optJSONObject("meta");
        meta.errorCode = metaJson.optInt("errorCode", -1);
        meta.errorMsg = metaJson.optString("errorMsg");
        meta.statusCode = metaJson.optInt("statusCode");
        meta.statusMsg = metaJson.optString("statusMsg");
        if(metaJson.has("base_url")){
            meta.baseUrl = metaJson.optString("base_url");
        }
        if(metaJson.has("totalCount")){
            meta.totalCount = metaJson.optLong("totalCount");
        }

        if(TextUtils.isEmpty(meta.errorMsg))
            meta.errorMsg = metaJson.optString("errorMessage");

        return meta;
    }

    private String readPayLoad(){
        return mData.optString("response");
    }

    public Meta getMeta() {
        if (metaData != null) {
            return metaData;
        }

        if (metaData == null) {
            metaData = readMeta();
        }


        if (metaData != null) {
            return metaData;
        }

        Meta tempMeta = new Meta();
        tempMeta.statusCode = -1;
        tempMeta.errorCode = -1;
        return tempMeta;
    }

    public boolean success(){
        return metaData.isSuccess();
    }
}
