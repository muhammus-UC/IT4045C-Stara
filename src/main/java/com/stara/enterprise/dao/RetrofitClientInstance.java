package com.stara.enterprise.dao;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static String BASE_URL = "https://api.tvmaze.com";

    /*
    * Convert the API to a Java interface.
    * Uses Retrofit to convert the tvmaze API to a Java interface.
    *
    * @return the Java interface.
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
