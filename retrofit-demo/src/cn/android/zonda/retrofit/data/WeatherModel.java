package cn.android.zonda.retrofit.data;

public class WeatherModel {

    private int id;

    private String description;

    private String icon;

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final String getIcon() {
        return icon;
    }

    public final void setIcon(String icon) {
        this.icon = icon;
    }
}
