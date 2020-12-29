package com.example.myapplication.retrofit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myapplication.utils.MyConfig.URL_SERVER;


public class APIClient {
 
    private static Retrofit retrofit = null;
 
    public static Retrofit getClient() {
 
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //OkHttpClient client = new MyOkHttpClient().getUnsafeOkHttpClient().newBuilder().readTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        /*OkHttpClient client = new OkHttpClient.Builder()
                //.readTimeout(30, TimeUnit.SECONDS)
                //.connectTimeout(30, TimeUnit.SECONDS)
                .build();*/
 
        retrofit = new Retrofit.Builder()
                .baseUrl(URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build(); 
 
 
 
        return retrofit; 
    } 
 
} 