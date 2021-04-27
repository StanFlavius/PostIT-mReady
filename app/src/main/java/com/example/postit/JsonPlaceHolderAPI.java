package com.example.postit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderAPI {

    @POST("auth/register")
    Call<UserRegisterResponse> registerUser(@Body RequestBody body);

    @POST("auth/login")
    Call<UserRegisterResponse> logInUser(@Body RequestBody body);

    @POST("posts")
    Call<PostRequestResponse> post(@Body RequestBody body);

    @GET("posts")
    Call<PostRequestResponse> getPosts();
}
