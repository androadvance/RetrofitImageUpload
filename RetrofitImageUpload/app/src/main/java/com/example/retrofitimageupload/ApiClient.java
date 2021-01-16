package com.example.retrofitimageupload;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://100.2.2/uploadimage";
    public static Retrofit retrofit;

    public static Retrofit getRetrofit(){

        if (retrofit == null){

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
