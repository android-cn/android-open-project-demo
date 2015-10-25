package com.houzhi.retrofitdemo.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by houzhi on 15-10-20.
 */

public class HttpbinRequest {

    @SerializedName("args")
    @Expose
    private Args args;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("files")
    @Expose
    private Files files;
    @SerializedName("form")
    @Expose
    private Form form;
    @SerializedName("headers")
    @Expose
    private Headers headers;
    @SerializedName("json")
    @Expose
    private Object json;
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * @return The args
     */
    public Args getArgs() {
        return args;
    }

    /**
     * @param args The args
     */
    public void setArgs(Args args) {
        this.args = args;
    }

    /**
     * @return The data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return The files
     */
    public Files getFiles() {
        return files;
    }

    /**
     * @param files The files
     */
    public void setFiles(Files files) {
        this.files = files;
    }

    /**
     * @return The form
     */
    public Form getForm() {
        return form;
    }

    /**
     * @param form The form
     */
    public void setForm(Form form) {
        this.form = form;
    }

    /**
     * @return The headers
     */
    public Headers getHeaders() {
        return headers;
    }

    /**
     * @param headers The headers
     */
    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    /**
     * @return The json
     */
    public Object getJson() {
        return json;
    }

    /**
     * @param json The json
     */
    public void setJson(Object json) {
        this.json = json;
    }

    /**
     * @return The origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin The origin
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "HttpbinRequest{" + "\n" +
                "    args=" + (args != null ? args.toString() : "null") + "" +
                ",\n    data='" + data + '\'' +
                ",\n    files=" + (files != null ? files.toString() : "null") +
                ",\n    form=" + (form != null ? form.toString() : "null") +
                ",\n    headers=" + (headers != null ? headers.toString() : "null") +
                ",\n    json=" + json +
                ",\n    origin='" + origin + '\'' +
                ",\n    url='" + url + '\'' +
                '}';
    }
}