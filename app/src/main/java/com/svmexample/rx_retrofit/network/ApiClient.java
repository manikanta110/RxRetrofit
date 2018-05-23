package com.svmexample.rx_retrofit.network;

import android.content.Context;
import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.svmexample.rx_retrofit.Constants;
import com.svmexample.rx_retrofit.PrefUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit = null;

    private static OkHttpClient httpClient;

    private static int REQUEST_TIME = 60;

    public static  Retrofit getRetrofit(Context context) {

        if (httpClient == null)
            getHttpClient(context);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build();

        }
        return retrofit;
    }

  public static void getHttpClient(final Context context) {
        OkHttpClient.Builder httpbuilder = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIME, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIME, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIME, TimeUnit.SECONDS);


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpbuilder.addInterceptor(interceptor);

        httpbuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                Request.Builder newrequest = original.newBuilder().
                        addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");


                if (!TextUtils.isEmpty(PrefUtils.getApiKey(context))) {
                    newrequest.addHeader("Authorization", PrefUtils.getApiKey(context));
                }
                Request request = newrequest.build();


                return chain.proceed(request);

            }
        });

        httpClient = httpbuilder.build();


    }

}
