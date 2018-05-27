package com.dangdoan.app.weathermap.webservices;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WeatherMapHttpRequest {
    private Map<String, String> mQueryParams = new HashMap<String, String>();
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
    private static final String APP_KEY = "8820fc98bcf759a40c5515433e13e777";
    private String mRequestUrl;
    private int mMethod = Request.Method.GET;

    public WeatherMapHttpRequest(String url, int method, Map<String, String> queryParams){
        mRequestUrl = getBaseUrl() + url;
        mMethod = method;
        mQueryParams = queryParams;
    }

    public String getUrl(){
        StringBuilder stringBuilder = new StringBuilder(mRequestUrl);
        Iterator it = mQueryParams.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(i == 0 && !stringBuilder.toString().contains("?")){
                stringBuilder.append( "?" + pair.getKey().toString() + "=" + pair.getValue().toString());
            }else{
                stringBuilder.append("&" + pair.getKey().toString() + "=" + pair.getValue().toString());
            }
            it.remove(); // avoids a ConcurrentModificationException
            i++;
        }
        stringBuilder.append("&appid=" + APP_KEY);
        final String completeUrl = stringBuilder.toString();
        return completeUrl;
    }

    public int getMethod(){
        return mMethod;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

}
