package com.dangdoan.app.weather.models;

import android.arch.lifecycle.MutableLiveData;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import paperparcel.PaperParcel;

@PaperParcel
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class WeatherObject implements Parcelable {
    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private Double message;
    @SerializedName("cnt")
    private Integer cnt;
    @SerializedName("list")
    private List<WeatherHourly> list;

    private City city;

    public WeatherObject(String cod, Double message, Integer cnt, List<WeatherHourly> list, City city){
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public Double getMessage() {
        return message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public List<WeatherHourly> getList() {
        return list;
    }

    public City getCity() {
        return city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherObject> CREATOR = PaperParcelWeatherObject.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelWeatherObject.writeToParcel(this, dest, flags);
    }

}
