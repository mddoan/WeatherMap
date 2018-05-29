package com.dangdoan.app.weather.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dangdoan.app.weather.R;

public class DailyFragment extends Fragment{
    public static final String TAG = MainPage.class.getSimpleName();

    public static DailyFragment newInstance(){
        return new DailyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_daily, container, false);
        return root;
    }
}
