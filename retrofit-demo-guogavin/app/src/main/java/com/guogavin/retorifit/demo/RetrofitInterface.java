package com.guogavin.retorifit.demo;

import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
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
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by gavinguo on 7/8/2015.
 */
public interface RetrofitInterface {

    /******************************************* 下面测试同步执行 ***************************************************/

    //直接get访问一个地址
    @GET("/index.php")
    String callIndexSynchronous();

    //直接get访问一个地址，直接返回Response
    @GET("/index.php")
    Response callIndexSynchronousResponse();

    @GET("/index.php")
    Observable<String> callIndexSynchronousObservable();//返回一个RxJava的Observable<T>，具体请参考https://github.com/ReactiveX/RxJava/wiki

    /******************************************* 下面测试异步执行 ***************************************************/
    /************************** 下面的方法为测试get方法，传递值以及获取callback的方式 ***********************************/

    //直接get访问一个地址
    @GET("/index.php")
    void callIndex(Callback<String> call);

    //get方式拼接地址
    @GET("/{next}/index.php")
    void callGavin2Index(@Path("next") String next,Callback<String> call);

    //在get地址后面添加参数
    @GET("/{next}/query.php")
    void callGavin2Query(@Path("next") String next,@Query("sort") String sort,Callback<String> call);

    //在get地址后面直接拼接参数，和上面的方法是一个效果
    @GET("/{next}/query.php?sort=myQuery")
    void callGavin2PathQuery(@Path("next") String next,Callback<String> call);

    //在get地址后面添加参数,使用QueryMap来添加多个参数，当然也可以使用多个Query来替代
    @GET("/{next}/query.php")
    void callGavin2QueryMap(@Path("next") String next,@QueryMap Map<String,String> params,Callback<String> call);

    /************************** 下面的方法为测试post方法，传递值以及获取callback的方式 ************************************/

    //直接访问一个post地址，使用Body传递参数,
    //需要注意的是，使用这种方式来传递参数的话，需要自定义Converter，具体请参考StringConverter的toBody方法
    @POST("/postParams.php")
    void callPostParamsBody(@Body User user,Callback<String> call);

    //直接访问一个post地址，使用Field传递参数
    @POST("/postParams.php")
    @FormUrlEncoded
    void callPostParams(@Field("name") String name,@Field("age") int age,Callback<String> call);

    //直接访问一个post地址，使用FieldMap传递参数
    @POST("/postParams.php")
    @FormUrlEncoded
    void callPostParamsMap(@FieldMap Map<String,String> params,Callback<String> call);

    //直接访问一个post地址，使用Part传递参数
    @POST("/postParams.php")
    @Multipart//注意阅读Part的注释，int等其他类型，需要特殊处理
    void callPostParams2(@Part("name") String name,@Part("age") String age,Callback<String> call);

    //直接访问一个post地址，使用PartMap传递参数
    @POST("/postParams.php")
    @Multipart
    void callPostParams2Map(@PartMap Map<String,String> params,Callback<String> call);

    /**************************** 下面的方法是测试添加header的方法 ************************************************************/
    @Headers("Cache-Control: max-age=640000")
    @GET("/index.php")
    void callIndexAddHeaders(Callback<String> call);

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("/index.php")
    void callIndexAddHeaders2(Callback<String> call);


    @GET("/index.php")
    void callIndexAddHeaders3(@Header("Cache-Control") String headers,Callback<String> call);

}
