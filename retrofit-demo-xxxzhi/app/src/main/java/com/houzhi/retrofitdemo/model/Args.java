package com.houzhi.retrofitdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by houzhi on 15-10-20.
 */
public class Args {

    @SerializedName("arg1")
    @Expose
    private String arg1;
    @SerializedName("arg2")
    @Expose
    private String arg2;
    @SerializedName("arg3")
    @Expose
    private String arg3;

    /**
     * @return The arg1
     */
    public String getArg1() {
        return arg1;
    }

    /**
     * @param arg1 The arg1
     */
    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    /**
     * @return The arg2
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * @param arg2 The arg2
     */
    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    /**
     * @return The arg3
     */
    public String getArg3() {
        return arg3;
    }

    /**
     * @param arg3 The arg3
     */
    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    @Override
    public String toString() {
        return "Args{" + "\n" +
                "    arg1='" + arg1 + '\'' + "" +
                ",\n    arg2='" + arg2 + '\'' + "" +
                ",\n arg3='" + arg3 + '\'' +
                '}' + "\n";
    }
}