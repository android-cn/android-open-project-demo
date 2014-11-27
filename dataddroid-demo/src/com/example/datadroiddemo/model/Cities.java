package com.example.datadroiddemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public final class Cities implements Parcelable {

    public ArrayList<CityInfo> cities;

    public Cities() {}

    // Parcelable management
    private Cities(Parcel in) {
        cities = in.createTypedArrayList(CityInfo.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(cities);
    }

    public static final Parcelable.Creator<Cities> CREATOR = new Parcelable.Creator<Cities>() {
        public Cities createFromParcel(Parcel in) {
            return new Cities(in);
        }

        public Cities[] newArray(int size) {
            return new Cities[size];
        }
    };
}
