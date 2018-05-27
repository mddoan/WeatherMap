package com.dangdoan.app.weathermap.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
//    @SerializedName("message")
//    private Double message;
//    @SerializedName("cnt")
//    private Integer cnt;
//    @SerializedName("list")
//    private List<WeatherSlot> list;

    public WeatherObject(String cod){
        this.cod = cod;
//        this.message = message;
//        this.cnt = cnt;
//        this.list = list;
    }

    public String getCod() {
        return cod;
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
