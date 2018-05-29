package com.dangdoan.app.weather.models;


import android.os.Parcel;
import android.os.Parcelable;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import paperparcel.PaperParcel;

@PaperParcel
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class WeatherWindCondition implements Parcelable{
    private double speed;
    private double deg;

    public WeatherWindCondition(double speed, double deg){
        this.speed = speed;
        this.deg = deg;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherWindCondition> CREATOR = PaperParcelWeatherWindCondition.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelWeatherWindCondition.writeToParcel(this, dest, flags);
    }
}
