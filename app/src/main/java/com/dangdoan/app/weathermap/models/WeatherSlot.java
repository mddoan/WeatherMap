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
public class WeatherSlot implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherSlot> CREATOR = PaperParcelWeatherSlot.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelWeatherSlot.writeToParcel(this, dest, flags);
    }
}
