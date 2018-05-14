package com.mirzaadil.assignmentpayconiq.networks;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mirzaadil.assignmentpayconiq.utils.Constants;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mirza Adil on 5/10/2018.
 * <p>
 * This class provides the implementation of Retrofit RestClient.
 */

public class RestClient {

    private static ServicesInterface servicesInterface;
    private static Retrofit retrofit;

    static {

        //Configuring the Rest Client
        setUpRestClient();
    }

    private static void setUpRestClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static Retrofit getRetrofitClient() {
        return retrofit;
    }

    public static ServicesInterface getAdapter() {
        return servicesInterface;
    }

}
