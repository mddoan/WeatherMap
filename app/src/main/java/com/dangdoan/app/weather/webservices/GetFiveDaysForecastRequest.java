package com.dangdoan.app.weather.webservices;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.dangdoan.app.weather.loader.VolleyLoaderData;
import com.dangdoan.app.weather.models.WeatherObject;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

public class GetFiveDaysForecastRequest extends WeatherMapHttpRequest {
    private static final String TAG = GetFiveDaysForecastRequest.class.getSimpleName();
    public GetFiveDaysForecastRequest(String url, Map<String, String> queryParams) {
        super(url, Request.Method.GET, queryParams);
    }

    public static WeatherObject pareResponse(Context context, VolleyLoaderData vData){
        if(vData == null){
            return null;
        }
        JSONObject responseJson = vData.getPayLoadJSONObject();
        if(responseJson == null){
            return null;
        }

        Gson gson = new Gson();
        WeatherObject weatherObject = gson.fromJson(responseJson.toString(), WeatherObject.class);
        return weatherObject;
    }
}
