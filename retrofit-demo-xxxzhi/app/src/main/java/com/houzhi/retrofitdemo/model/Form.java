package com.houzhi.retrofitdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by houzhi on 15-10-20.
 */
public class Form {

    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("pwd")
    @Expose
    private String pwd;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * @return The log
     */
    public String getLog() {
        return log;
    }

    /**
     * @param log The log
     */
    public void setLog(String log) {
        this.log = log;
    }

    /**
     * @return The pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd The pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Form{" + "\n" +
                "    log='" + log + '\'' + "" +
                ",\n    pwd='" + pwd + '\'' + "" +
                ",\n    type='" + type + '\'' +
                '}' + "\n";
    }
}
