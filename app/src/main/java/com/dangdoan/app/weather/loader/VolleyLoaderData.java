package com.dangdoan.app.weather.loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VolleyLoaderData {
    private JSONObject mData;
    private String payLoad;

    public VolleyLoaderData(JSONObject response){
        mData = response;
        payLoad = readPayLoad();

    }

    private String readPayLoad(){
        return mData.toString();
    }

    public JSONObject getPayLoadJSONObject(){
        try {
            return new JSONObject(payLoad);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // In case the response is a json array
    public JSONArray getPayLoadJSONArray(){
        try {
            return new JSONArray(payLoad);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
