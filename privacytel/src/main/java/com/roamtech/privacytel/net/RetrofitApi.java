package com.roamtech.privacytel.net;

import android.text.TextUtils;
import android.util.Log;

import com.roamtech.privacytel.manager.RoamNumManager;
import com.roamtech.privacytel.net.request.Request;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {
    private static final int DEFAULT_TIMEOUT = 10;//超时时间
    private static volatile Request request_interface = null;


    /**
     * 创建网络请求接口实例
     *
     * @return Request
     */
    public static Request getRequestInterface() {
        if (null == request_interface) {
            synchronized (Request.class) {
                if(null == request_interface){
                    request_interface = provideRetrofit().create(Request.class);
                }
            }
        }
        return request_interface;
    }

    /**
     * 初始化必要对象和参数
     *
     * @return Retrofit
     */
    private static Retrofit provideRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(getHttpLoggingInterceptor())//Application拦截器
                .addNetworkInterceptor(getRequestHeader())//Network拦截器
                .build();
        //设置网络请求的Url地址
        //设置数据解析器
        //支持RxJava平台
        return new Retrofit.Builder()
                .baseUrl(Request.HOST)//设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create())//设置数据解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava平台
                .client(client)
                .build();
    }

    /**
     * 日志拦截器
     *
     * @return HttpLoggingInterceptor
     */
    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("OkHttpLog", "log = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * 请求头拦截器
     *
     * @return Interceptor
     */
    private static Interceptor getRequestHeader() {
        return chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request.Builder builder = originalRequest.newBuilder();
            builder.addHeader("Content-Type", "application/json;charset=UTF-8");
            if(!TextUtils.isEmpty(RoamNumManager.getInstance().getToken())){
                builder.addHeader("Authorization", RoamNumManager.getInstance().getToken());
            }
            okhttp3.Request.Builder requestBuilder = builder.method(originalRequest.method(), originalRequest.body());
            okhttp3.Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }
}
