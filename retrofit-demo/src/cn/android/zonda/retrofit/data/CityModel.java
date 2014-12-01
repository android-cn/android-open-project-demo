package cn.android.zonda.retrofit.data;

public class CityModel {

    private String     id;

    private String     name;

    private CoordModel coord;

    private String     country;

    public final String getId() {
        return id;
    }

    public final void setId(String id) {
        this.id = id;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final CoordModel getCoord() {
        return coord;
    }

    public final void setCoord(CoordModel coord) {
        this.coord = coord;
    }

    public final String getCountry() {
        return country;
    }

    public final void setCountry(String country) {
        this.country = country;
    }
}
