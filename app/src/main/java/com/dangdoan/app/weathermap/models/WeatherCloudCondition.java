package com.dangdoan.app.weathermap.models;

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
public class WeatherCloudCondition implements Parcelable{
    private int all;

    public WeatherCloudCondition(int all){
        this.all = all;
    }

    public int getAll() {
        return all;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherCloudCondition> CREATOR = PaperParcelWeatherCloudCondition.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelWeatherCloudCondition.writeToParcel(this, dest, flags);
    }
}
