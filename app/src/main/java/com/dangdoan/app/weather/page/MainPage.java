package com.dangdoan.app.weather.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dangdoan.app.weather.R;

public class MainPage extends Fragment{
    public static final String TAG = MainPage.class.getSimpleName();

    public static MainPage newInstance(){
        return new MainPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_main_page, container, false);
        return root;
    }
}
