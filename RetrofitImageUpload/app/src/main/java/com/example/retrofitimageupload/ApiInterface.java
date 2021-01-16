package com.example.retrofitimageupload;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("upload.php")
    Call<List<ImageClass>> uploadimage(@Field("title") String title,@Field("image") String image);
}
