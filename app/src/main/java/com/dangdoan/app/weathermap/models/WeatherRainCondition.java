package com.dangdoan.app.weathermap.models;

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
public class WeatherRainCondition implements Parcelable{
    @SerializedName("3h")
    private double threeHourVolume;

    public WeatherRainCondition(double threeHourVolume){
        this.threeHourVolume = threeHourVolume;
    }

    public double getThreeHourVolume() {
        return threeHourVolume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherRainCondition> CREATOR = PaperParcelWeatherRainCondition.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelWeatherRainCondition.writeToParcel(this, dest, flags);
    }
}
