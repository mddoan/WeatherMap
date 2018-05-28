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
public class WeatherInfoConditionCode implements Parcelable {
    private int id;
    private String main;
    private String description;
    private String icon;

    public WeatherInfoConditionCode(int id, String main, String description, String icon){
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherInfoConditionCode> CREATOR = PaperParcelWeatherInfoConditionCode.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelWeatherInfoConditionCode.writeToParcel(this, dest, flags);
    }
}
