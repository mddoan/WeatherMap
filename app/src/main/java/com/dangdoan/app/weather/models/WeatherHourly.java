package com.dangdoan.app.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import paperparcel.PaperParcel;

@PaperParcel
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class WeatherHourly implements Parcelable {
    long dt;
    String dt_txt;
    WeatherMainCondition main;
    List<WeatherInfoConditionCode> weather;
    WeatherCloudCondition clouds;
    WeatherWindCondition wind;
    WeatherRainCondition rain;
    WeatherSnowCondition snow;

    public WeatherHourly(long dt, String dt_txt, WeatherMainCondition main, List<WeatherInfoConditionCode>
            weather, WeatherCloudCondition clouds, WeatherWindCondition wind, WeatherRainCondition
            rain, WeatherSnowCondition snow){
        this.dt = dt;
        this.dt_txt = dt_txt;
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.rain = rain;
        this.snow = snow;
    }

    public long getDt() {
        return dt;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public WeatherMainCondition getMain() {
        return main;
    }

    public List<WeatherInfoConditionCode> getWeatherInfoConditionCodes() {
        return weather;
    }

    public WeatherCloudCondition getWeatherCloudConditions() {
        return clouds;
    }

    public WeatherWindCondition getWeatherWindConditions() {
        return wind;
    }

    public WeatherRainCondition getWeatherRainConditions() {
        return rain;
    }

    public WeatherSnowCondition getWeatherSnowConditions() {
        return snow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherHourly> CREATOR = PaperParcelWeatherHourly.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelWeatherHourly.writeToParcel(this, dest, flags);
    }
}
