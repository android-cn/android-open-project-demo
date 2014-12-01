package cn.android.zonda.retrofit.data;

import com.google.gson.annotations.SerializedName;

public class WindModel {

    private float speed;

    private float    deg;

    @SerializedName("var_beg")
    private float    varBeg;

    @SerializedName("var_end")
    private float    varEnd;

    public final float getSpeed() {
        return speed;
    }

    public final void setSpeed(float speed) {
        this.speed = speed;
    }

    public final float getDeg() {
        return deg;
    }

    public final void setDeg(float deg) {
        this.deg = deg;
    }

    public final float getVarBeg() {
        return varBeg;
    }

    public final void setVarBeg(float varBeg) {
        this.varBeg = varBeg;
    }

    public final float getVarEnd() {
        return varEnd;
    }

    public final void setVarEnd(float varEnd) {
        this.varEnd = varEnd;
    }

    
}
