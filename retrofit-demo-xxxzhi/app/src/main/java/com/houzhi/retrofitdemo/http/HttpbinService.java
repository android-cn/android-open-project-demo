package com.houzhi.retrofitdemo.http;


import com.houzhi.retrofitdemo.model.Args;
import com.houzhi.retrofitdemo.model.HttpbinRequest;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by houzhi on 15-10-20.
 */
public interface HttpbinService {

    @GET("/get?arg1=hello")
    Call<ResponseBody> testGetResponseBody();


    @GET("/get?arg1=hello")
    Call<HttpbinRequest> testGet();

    @POST("/post")
    Call<HttpbinRequest> testPost();

    @GET("/{path}")
    Call<HttpbinRequest> testManipulation(@Path("path") String path, @Query("arg1") String arg1, @QueryMap Map<String, String> map);

    /**
     * 　利用Post 请求 使用Field参数
     * FormUrlEncoded 是指添加urlEncoded. 没有参数值设置
     *
     * @param log      也可以设置encoded 参数　，排序
     * @param filedMap
     * @return
     */
    @FormUrlEncoded
    @POST("/post")
    Call<HttpbinRequest> postFieldUrlEncode(@Field(value = "type", encoded = false) String log, @FieldMap Map<String, String> filedMap);


    /**
     * 　利用Post 请求 测试Body
     *
     * @param body
     * @return
     */
    @POST("/post")
    Call<HttpbinRequest> postBody(@Body Args body);

    @Multipart
    @POST("/post")
    Call<HttpbinRequest> postMultiPart(@Part("part1") RequestBody part1, @Part("part2") RequestBody part2);


    @Headers("Cache-Control: max-age=640000")
    @GET("/get")
    Call<HttpbinRequest> testHeaderMethod();


    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("/get")
    Call<HttpbinRequest> testHeaderMapMethod();


    @GET("get")
    Call<HttpbinRequest> testHeaderParams(@Header("User-Agent") String agent);
}
