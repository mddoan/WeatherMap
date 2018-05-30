package com.dangdoan.app.weather;

import android.Manifest;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dangdoan.app.weather.loader.VolleyLoader;
import com.dangdoan.app.weather.loader.VolleyLoaderData;
import com.dangdoan.app.weather.models.ShareLocationViewModel;
import com.dangdoan.app.weather.models.WeatherDataViewModel;
import com.dangdoan.app.weather.models.WeatherHourly;
import com.dangdoan.app.weather.models.WeatherObject;
import com.dangdoan.app.weather.page.DailyFragment;
import com.dangdoan.app.weather.loader.JsonVolleyLoader;
import com.dangdoan.app.weather.webservices.GetFiveDaysForecastRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static com.dangdoan.app.weather.utils.Constant.KEY_PARAM_LAT;
import static com.dangdoan.app.weather.utils.Constant.KEY_PARAM_LON;
import static com.dangdoan.app.weather.utils.Constant.REQUEST_CODE_ZOOM_INTO_CURRENT_LOCATION;
import static com.dangdoan.app.weather.utils.Constant.TODAY;
import static com.dangdoan.app.weather.utils.Constant.URL_FORECAST;

public class StartActivity extends AppCompatActivity{
    private static final String TAG = StartActivity.class.getSimpleName();
    private Location mCurrentLocation;

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private WeatherPagerAdapter mAdapter;
    private TextView location;

    private WeatherDataViewModel weatherDataViewModel;
    private ShareLocationViewModel shareLocationViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.sliding_tabs);
        mAdapter = new WeatherPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        location = findViewById(R.id.location);
        initLocation();
    }


    private void initLocation(){

        shareLocationViewModel = ViewModelProviders.of(this).get(ShareLocationViewModel.class);
        shareLocationViewModel.getLocation(this).observe(this, currentLocation->{
            mCurrentLocation = currentLocation;
            loadWeatherData();
        });
    }

    private void loadWeatherData(){
        weatherDataViewModel = ViewModelProviders.of(this).get(WeatherDataViewModel.class);
        weatherDataViewModel.getWeatherObject(this, mCurrentLocation).observe(this, weatherObject ->{
            updateUI(weatherObject);
        });
    }

    private void updateUI(WeatherObject weatherObject){
        location.setText(weatherObject.getCity().getName());
    }




    public class WeatherPagerAdapter extends FragmentStatePagerAdapter{
        public WeatherPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.v(TAG, "getItem");
            return DailyFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int pos){
            switch (pos){
                case 0:
                    return "TODAY";
                case 1:
                    return "TOMOROW";
                case 2:
                    return "DAY 3";
                case 3:
                    return "DAY 4";
                case 4:
                    return "DAY 5";
                    default:
                        return "";
            }
        }

    }
}
