package com.dangdoan.app.weathermap.webservices;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.dangdoan.app.weathermap.loader.VolleyLoaderData;
import com.dangdoan.app.weathermap.models.WeatherObject;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class GetFiveDaysForecastRequest extends WeatherMapHttpRequest {
    private static final String TAG = GetFiveDaysForecastRequest.class.getSimpleName();
    public GetFiveDaysForecastRequest(String url, Map<String, String> queryParams) {
        super(url, Request.Method.GET, queryParams);
    }

    public static void pareResponse(Context context, VolleyLoaderData vData){
        if(vData == null){
            return;
        }
        JSONObject responseJson = vData.getPayLoadJSONObject();
        if(responseJson == null){
            return;
        }

        Gson gson = new Gson();
        WeatherObject weatherObject = gson.fromJson(responseJson.toString(), WeatherObject.class);
        Log.v(TAG, "pareResponse");

    }
}
