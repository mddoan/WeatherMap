package com.dangdoan.app.weathermap.loader;

import android.content.Context;

import com.android.volley.VolleyError;

public class VolleyLoader <T>{
    public static final int STATE_DEFAULT = 0;
    public static final int STATE_LOADING = 1;
    protected LoaderListener loaderListener;
    protected LoaderErrorListener loaderErrorListener;
    public static VolleySingleton mVolley;
    protected int mMethod;
    protected String mUrl;
    protected Context mContext;
    protected String mTag;
    protected int state;

    public VolleyLoader(Context context, int method, String url, String tag,
                        LoaderListener listener, LoaderErrorListener errorListener){
        mContext = context;
        mMethod = method;
        mUrl = url;
        mTag = tag;
        loaderListener = listener;
        loaderErrorListener = errorListener;
        mVolley = VolleySingleton.getInstance(context);
    }

    public interface LoaderListener{
        void onResponse(VolleyLoaderData volleyLoaderData);
    }

    public interface LoaderErrorListener{
        void onResponseError(VolleyError error);
    }

    public static void cancelLoadRequest(String mTag){
        mVolley.getRequestQueue().cancelAll(mTag);
    }
}
