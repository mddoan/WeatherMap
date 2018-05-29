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
public class City implements Parcelable{
    private int id;
    private String name;
    private Coordinate coord;
    private String country;
    private long population;

    public City(int id, String name, Coordinate coord, String country, long population){
        this.name = name;
        this.coord = coord;
        this.country = country;
        this.population = population;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }

    public long getPopulation() {
        return population;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<City> CREATOR = PaperParcelCity.CREATOR;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PaperParcelCity.writeToParcel(this, dest, flags);
    }
}
