package com.example.good_bad_game;

import android.telecom.Call;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginService {

    @GET("users/")
    Call<List<Login>> getPosts();

    @POST("users/")
    Call<Post> createPost(@Body Post post);
}
