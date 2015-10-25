package com.houzhi.retrofitdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by houzhi on 15-10-20.
 */
public class Headers {

    @SerializedName("Accept")
    @Expose
    private String Accept;
    @SerializedName("Content-Length")
    @Expose
    private String ContentLength;
    @SerializedName("Content-Type")
    @Expose
    private String ContentType;
    @SerializedName("Host")
    @Expose
    private String Host;
    @SerializedName("User-Agent")
    @Expose
    private String UserAgent;

    /**
     * @return The Accept
     */
    public String getAccept() {
        return Accept;
    }

    /**
     * @param Accept The Accept
     */
    public void setAccept(String Accept) {
        this.Accept = Accept;
    }

    /**
     * @return The ContentLength
     */
    public String getContentLength() {
        return ContentLength;
    }

    /**
     * @param ContentLength The Content-Length
     */
    public void setContentLength(String ContentLength) {
        this.ContentLength = ContentLength;
    }

    /**
     * @return The ContentType
     */
    public String getContentType() {
        return ContentType;
    }

    /**
     * @param ContentType The Content-Type
     */
    public void setContentType(String ContentType) {
        this.ContentType = ContentType;
    }

    /**
     * @return The Host
     */
    public String getHost() {
        return Host;
    }

    /**
     * @param Host The Host
     */
    public void setHost(String Host) {
        this.Host = Host;
    }

    /**
     * @return The UserAgent
     */
    public String getUserAgent() {
        return UserAgent;
    }

    /**
     * @param UserAgent The User-Agent
     */
    public void setUserAgent(String UserAgent) {
        this.UserAgent = UserAgent;
    }

    @Override
    public String toString() {
        return "Headers{" + "\n" +
                "   Accept='" + Accept + '\'' +
                ",\n    ContentLength='" + ContentLength + '\'' +
                ",\n    ContentType='" + ContentType + '\'' +
                ",\n    Host='" + Host + '\'' +
                ",\n    UserAgent='" + UserAgent + '\'' +
                '}' + "\n";
    }
}
