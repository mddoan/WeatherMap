package com.dangdoan.app.weathermap.webservices;

import com.android.volley.Request;

import java.util.Map;

public class GetFiveDaysForecastRequest extends WeatherMapHttpRequest {
    public GetFiveDaysForecastRequest(String url, Map<String, String> queryParams) {
        super(url, Request.Method.GET, queryParams);
    }
}
