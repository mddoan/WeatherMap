package com.dangdoan.app.weathermap.loader;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dangdoan.app.weathermap.webservices.WeatherMapHttpRequest;

import org.json.JSONObject;

public class JsonVolleyLoader extends VolleyLoader{
    public static final String TAG = JsonVolleyLoader.class.getSimpleName();
    public JsonVolleyLoader(Context context, WeatherMapHttpRequest request, String tag, LoaderListener listener,
                            LoaderErrorListener
            errorListener) {
        super(context, request.getMethod(), request.getUrl(), tag, listener, errorListener);
    }

    public void loadJsonObjectData(){
        // Create request
        state = STATE_LOADING;
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (mMethod, mUrl, null, new Response
                        .Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "RESPONSE = " + response);
                        VolleyLoaderData vData = new VolleyLoaderData(response);
                        deliverResult(vData);
                        state = STATE_DEFAULT;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if(networkResponse != null && networkResponse.data != null &&
                                networkResponse.statusCode == 401){
                            // Handle error
                        }else{
                            reportError(error);
                            state = STATE_DEFAULT;
                        }

                    }
                });

        jsonRequest.setTag(mTag);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 1, 2));
        mVolley.addToRequestQueue(jsonRequest);

    }

    public void deliverResult(VolleyLoaderData vData){
        loaderListener.onResponse(vData);
    }

    public void reportError(VolleyError error){
        loaderErrorListener.onResponseError(error);

    }

}
