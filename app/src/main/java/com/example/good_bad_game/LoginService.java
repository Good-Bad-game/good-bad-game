package com.example.good_bad_game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginService {

    @GET("users/")
    retrofit2.Call<List<Login>> getPosts();

    @POST("users/")
    Call<Post> createPost(@Body Post post);
}
