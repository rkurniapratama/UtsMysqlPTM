package com.rizal.utsmysql.api;

import android.os.SystemClock;

import java.io.IOException;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, boolean withDelay, final long timeDelay){
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(logging);

        if(withDelay) {
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(1);

            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    SystemClock.sleep(timeDelay);
                    return chain.proceed(chain.request());
                }
            };

            okHttpClientBuilder.addNetworkInterceptor(interceptor);
            okHttpClientBuilder.dispatcher(dispatcher);
        }

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
        }
        return retrofit;
    }
}