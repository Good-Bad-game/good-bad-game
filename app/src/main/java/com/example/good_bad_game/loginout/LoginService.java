package com.example.good_bad_game.loginout;

import com.example.good_bad_game.Matching;
import com.example.good_bad_game.friend.getFriend;
import com.example.good_bad_game.getItem;
import com.example.good_bad_game.readyroom.Room;
import com.example.good_bad_game.ranking.Ranking;
import com.example.good_bad_game.readyroom.getRoom;
import com.example.good_bad_game.friend.getFriend;
import com.example.good_bad_game.readyroom.getRoom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LoginService {

    @GET("user/")
    retrofit2.Call<List<Login>> getPosts();

    @POST("user/")
    Call<Post> createPost(@Body Post post);

    @GET("friend/")
    retrofit2.Call<List<getFriend>> getFriends();

    @GET("room/")
    retrofit2.Call<List<getRoom>> getRoom();

    @POST("room/")
    Call<Room>  Room(@Body Room room);

    @DELETE("room/{id}")
    Call<Void> deleteRoom(@Path("id") int id);

    @GET("user-matching/")
    retrofit2.Call<List<getMatching>> getMatching();

    @POST("user-matching/")
    Call<Matching>  Matching(@Body Matching matching);

    @DELETE("user-matching/{userId}/")
    Call<Void> deleteMatching(@Path("userId") int userId);

    @GET("ranking/")
    retrofit2.Call<List<Ranking>> Ranking();

    @POST("ranking/")
    Call<Ranking>  Ranking(@Body Ranking ranking);

    @GET("item/")
    retrofit2.Call<List<getItem>> getItems();
}
