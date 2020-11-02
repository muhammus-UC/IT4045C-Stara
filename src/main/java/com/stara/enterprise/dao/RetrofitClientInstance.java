package com.stara.enterprise.dao;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static String BASE_URL = "https://api.tvmaze.com";

    /**
     * Setup Retrofit for use with TVMaze API
     * TVMaze API Reference: https://www.tvmaze.com/api
     *
     * @return retrofit - Retrofit object configured for TVMaze API
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
