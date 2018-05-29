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
public class Coordinate implements Parcelable{
    private double lat;
    private double lon;

    public Coordinate(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Coordinate> CREATOR = PaperParcelCoordinate.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelCoordinate.writeToParcel(this, dest, flags);
    }

}
