package com.dangdoan.app.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import paperparcel.PaperParcel;

@PaperParcel
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class WeatherSnowCondition implements Parcelable{
    @SerializedName("3h")
    private double threeHourVolume;

    public WeatherSnowCondition(double threeHourVolume){
        this.threeHourVolume = threeHourVolume;
    }

    public double getThreeHourVolume() {
        return threeHourVolume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherSnowCondition> CREATOR = PaperParcelWeatherSnowCondition.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelWeatherSnowCondition.writeToParcel(this, dest, flags);
    }
}
