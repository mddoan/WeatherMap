package com.dangdoan.app.weather.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.VolleyError;
import com.dangdoan.app.weather.StartActivity;
import com.dangdoan.app.weather.loader.JsonVolleyLoader;
import com.dangdoan.app.weather.loader.VolleyLoader;
import com.dangdoan.app.weather.loader.VolleyLoaderData;
import com.dangdoan.app.weather.webservices.GetFiveDaysForecastRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static com.dangdoan.app.weather.utils.Constant.KEY_PARAM_LAT;
import static com.dangdoan.app.weather.utils.Constant.KEY_PARAM_LON;
import static com.dangdoan.app.weather.utils.Constant.TODAY;
import static com.dangdoan.app.weather.utils.Constant.URL_FORECAST;

public class WeatherDataViewModel extends ViewModel{
    public static final String TAG = WeatherDataViewModel.class.getSimpleName();
    List<WeatherHourly> [] fiveDaysWeather = null;
    private int mDay;
    Location mCurrentLocation;

    private MutableLiveData<WeatherObject> mWeatherObject;

    public LiveData<WeatherObject> getWeatherObject(Context context, Location location){
        mCurrentLocation = location;
        if(mWeatherObject == null){
            mWeatherObject = new MutableLiveData<>();
            loadFiveDaysWeatherData(context);
        }
        return mWeatherObject;
    }

    public List<WeatherHourly> getWeatherForDay(int day){
        if(fiveDaysWeather != null && fiveDaysWeather.length > day){
            return fiveDaysWeather[day];
        }
        return null;
    }

    private void loadFiveDaysWeatherData(final Context context){
        if(mCurrentLocation == null){
            return;
        }
        final GetFiveDaysForecastRequest request = new GetFiveDaysForecastRequest(URL_FORECAST, getRequestParams
                (mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
        JsonVolleyLoader loader = new JsonVolleyLoader(context, request, "", new VolleyLoader.LoaderListener() {
            @Override
            public void onResponse(VolleyLoaderData volleyLoaderData) {
                Log.v(TAG, "onResponse");
                WeatherObject weatherObject = GetFiveDaysForecastRequest.pareResponse(context,
                        volleyLoaderData);
                if(weatherObject != null){
                    filter(weatherObject.getList());
                }
                mWeatherObject.setValue(weatherObject);


            }
        }, new VolleyLoader.LoaderErrorListener() {
            @Override
            public void onResponseError(VolleyError error) {
                Log.v(TAG, "onResponseError");
            }
        });
        loader.loadJsonObjectData();
    }

    private Map<String, String> getRequestParams(double lat, double lon){
        Map<String, String> params = new HashMap<>();
        params.put(KEY_PARAM_LAT, String.valueOf(lat));
        params.put(KEY_PARAM_LON, String.valueOf(lon));
        return params;
    }

    public void filter(List<WeatherHourly> list){
        fiveDaysWeather = new List[5];
        String current = new StringTokenizer(list.get(0).getDt_txt()).nextToken();
        for(int i=0; i<5; i++) {
            fiveDaysWeather[i] = new ArrayList<>();
        }

        int day = 0;
        for(WeatherHourly item : list){
            if(day < 5) {
                String date = new StringTokenizer(item.getDt_txt()).nextToken();
                if (date.equals(current)) {
                    fiveDaysWeather[day].add(item);
                }else if(day < 4){
                    current = date;
                    day++;
                }

            }
        }
        Log.v(TAG, "filter");
    }

}
