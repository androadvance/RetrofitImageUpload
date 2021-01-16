package com.example.retrofitimageupload;

import com.google.gson.annotations.SerializedName;

public class ImageClass {

    @SerializedName("image")
    String Image;

    @SerializedName("title")
    String Title;

    @SerializedName("response")
    String Response;

    public String getResponse() {
        return Response;
    }
}
