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
public class WeatherMainCondition implements Parcelable {
    private double temp;
    private double temp_min;
    private double temp_max;
    private double sea_level;
    private double grnd_level;
    private int humidity;
    private double temp_kf;

    public WeatherMainCondition(double temp, double temp_min, double temp_max, double sea_level, double grnd_level,
                                int humidity, double temp_kf){
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
        this.humidity = humidity;
        this.temp_kf = temp_kf;
    }

    public double getTemp() {
        return temp;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getSea_level() {
        return sea_level;
    }

    public double getGrnd_level() {
        return grnd_level;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getTemp_kf() {
        return temp_kf;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherMainCondition> CREATOR = PaperParcelWeatherMainCondition.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelWeatherMainCondition.writeToParcel(this, dest, flags);
    }
}
