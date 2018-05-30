package com.dangdoan.app.weather.utils;

public class Helpers {
    public static int getFarenheitTemp(double kelvinTemp){
        return (int) ((kelvinTemp - 273.15) * 9/5 + 32);
    }
}
