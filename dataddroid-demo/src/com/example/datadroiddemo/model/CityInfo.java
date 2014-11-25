package com.example.datadroiddemo.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class CityInfo implements Parcelable {

    public String name;
    public String postalCode;
    public String state;
    public String country;

    public CityInfo() {}

    // Parcelable management
    private CityInfo(Parcel in) {
        name = in.readString();
        postalCode = in.readString();
        state = in.readString();
        country = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(postalCode);
        dest.writeString(state);
        dest.writeString(country);
    }

    public static final Parcelable.Creator<CityInfo> CREATOR = new Parcelable.Creator<CityInfo>() {
        public CityInfo createFromParcel(Parcel in) {
            return new CityInfo(in);
        }

        public CityInfo[] newArray(int size) {
            return new CityInfo[size];
        }
    };
}
