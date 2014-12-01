package cn.android.zonda.retrofit.data;

import com.google.gson.annotations.SerializedName;

public class CoordModel {

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lon")
    private String longitude;

    public final String getLatitude() {
        return latitude;
    }

    public final void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public final String getLongitude() {
        return longitude;
    }

    public final void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
