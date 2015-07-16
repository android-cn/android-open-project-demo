package com.guogavin.retorifit.demo;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by gavinguo on 7/8/2015.
 */
public class RetrofitService {
    private static RetrofitService retrofitService = new RetrofitService();
    private RetrofitInterface retrofitInterface;
    private String methodName;

    private RetrofitService(){initRetrofit();}

    public static RetrofitService getInstance(){
        if(retrofitService == null){
            retrofitService = new RetrofitService();
        }
        return retrofitService;
    }

    private void initRetrofit(){
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setConverter(new StringConverter())//设置数据转换器，默认是使用GsonConvert，但是因为测试接口是在新浪云上面布置，并且没有通过实名认证，返回数据不规范，所以一直使用自定义的convert
                .setLogLevel(RestAdapter.LogLevel.FULL)//设置log参数，设置了之后可以看到一些log参数，比如会在logcat中打印出来heads
//                .setRequestInterceptor(requestInterceptor)//Headers that need to be added to every request，如果对每个请求都需要添加特定的heads，可以这样来配置
//                .setErrorHandler(new MyErrorHandler())//f you need custom error handling for requests在异常抛出框架层之前（比如到达你的回调函数之前），给你一个自定义的机会
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Cache-Control","max-age=640000");
        }
    };

    class MyErrorHandler implements ErrorHandler{

        @Override
        public Throwable handleError(RetrofitError retrofitError) {
            return new Exception("do nothing");
        }
    };

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/index.php
     * GET方式
     */
    public void callIndexSynchronous() {
        methodName = "callIndexSynchronous";
        new Thread(){
            @Override
            public void run() {
                super.run();
                String result = retrofitInterface.callIndexSynchronous();
                Log.e(methodName,""+result);
            }
        }.start();
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/index.php
     * GET方式
     */
    public void callIndexSynchronousResponse() {
        methodName = "callIndexSynchronousResponse";
        new Thread(){
            @Override
            public void run() {
                super.run();
                Response result = retrofitInterface.callIndexSynchronousResponse();
                try {
                    Log.e(methodName,""+new StringConverter().fromBody(result.getBody(),null));
                } catch (ConversionException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/index.php
     * GET方式
     */
    public void callIndexSynchronousObservable() {
        methodName = "callIndexSynchronous";
        new Thread(){
            @Override
            public void run() {
                super.run();
                Observable<String> result = retrofitInterface.callIndexSynchronousObservable();
                result.subscribe(mySubscriber);
            }
        }.start();
    }

    Subscriber<String> mySubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.e(methodName,"onCompleted");
        }

        @Override
        public void onError(Throwable throwable) {
            Log.e(methodName,"onError");
        }

        @Override
        public void onNext(String s) {
            Log.e(methodName,"onNext:"+s);
        }
    };

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/index.php
     * GET方式
     */
    public void callIndex() {
        methodName = "callIndex";
        retrofitInterface.callIndex(callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/index.php
     * GET方式
     * 添加headers
     */
    public void callIndexAddHeaders(){
        methodName = "callIndexAddHeaders";
        retrofitInterface.callIndexAddHeaders(callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/index.php
     * GET方式
     * 添加headers
     */
    public void callIndexAddHeaders2(){
        methodName = "callIndexAddHeaders2";
        retrofitInterface.callIndexAddHeaders2(callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/index.php
     * GET方式
     * 添加headers
     */
    public void callIndexAddHeaders3(){
        methodName = "callIndexAddHeaders3";
        retrofitInterface.callIndexAddHeaders3("max-age=640000",callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/gavin2/index.php
     * GET方式
     */
    public void callGavin2Index() {
        methodName = "callGavin2Index";
        retrofitInterface.callGavin2Index("gavin2", callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/gavin2/query.php
     * GET方式
     */
    public void callGavin2Query() {
        methodName = "callGavin2Query";
        retrofitInterface.callGavin2Query("gavin2", "mySortQuery", callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/gavin2/query.php
     * GET方式
     */
    public void callGavin2PathQuery() {
        methodName = "callGavin2PathQuery";
        retrofitInterface.callGavin2PathQuery("gavin2", callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/gavin2/query.php
     * GET方式
     */
    public void callGavin2QueryMap() {
        methodName = "callGavin2QueryMap";
        Map<String,String> params = new HashMap<String,String>();
        params.put("sort","mySort");
        params.put("name","callGavin2QueryMap");
        retrofitInterface.callGavin2QueryMap("gavin2", params, callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/postParams.php
     * POST方式，会返回你传递的post参数
     */
    public void callPostParamsBody(){
        methodName = "callPostParamsBody";
        retrofitInterface.callPostParamsBody(new User("callPostParamsBody",15,20.58), callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/postParams.php
     * POST方式，会返回你传递的post参数
     */
    public void callPostParams(){
        methodName = "callPostParams";
        retrofitInterface.callPostParams("callPostParams", 100, callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/postParams.php
     * POST方式，会返回你传递的post参数
     */
    public void callPostParamsMap(){
        methodName = "callPostParamsMap";
        Map<String,String> params = new HashMap<String,String>();
        params.put("name","callPostParamsMap");
        params.put("age","1000");
        retrofitInterface.callPostParamsMap(params, callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/postParams.php
     * POST方式，会返回你传递的post参数
     */
    public void callPostParams2(){
        methodName = "callPostParams2";
        retrofitInterface.callPostParams2("callPostParams2", "100", callback);
    }

    /**
     * 访问接口http://1.mirror3.sinaapp.com/gavin/postParams.php
     * POST方式，会返回你传递的post参数
     */
    public void callPostParams2Map(){
        methodName = "callPostParams2Map";
        Map<String,String> params = new HashMap<String,String>();
        params.put("name","callPostParams2Map");
        params.put("age","1000");
        retrofitInterface.callPostParams2Map(params, callback);
    }

    Callback<String> callback = new Callback<String>() {
        @Override
        public void success(String s, Response response) {
            Log.e(methodName, response.getUrl()+":success:" + s);
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(methodName, error.getUrl()+":error:" + error.toString());
        }
    };
}
