package com.example.postit;

import com.google.gson.annotations.SerializedName;

public class PostRequestResponse {

    @SerializedName("data")
    private DataPost dataPost;

    public DataPost getDataPost() {
        return dataPost;
    }
}
