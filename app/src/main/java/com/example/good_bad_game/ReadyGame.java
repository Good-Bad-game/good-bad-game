package com.example.good_bad_game;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.good_bad_game.loginout.LoginService;
import com.example.good_bad_game.readyroom.Room;
import com.example.good_bad_game.readyroom.getRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadyGame extends AppCompatActivity {

    int imgId[] = {
            R.id.team1, R.id.team2, R.id.team3,
            R.id.team4, R.id.team5, R.id.team6,
    };

    int[] skinId = {R.drawable.skin1, R.drawable.skin2, R.drawable.skin3,
            R.drawable.skin4, R.drawable.skin5, R.drawable.skin6};

    List<String> userList = new ArrayList<>();
    List<String> newUserList = new ArrayList<>();
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ready_game);
        Button btn_start = findViewById(R.id.btn_start);

        Intent receive_intent = getIntent();
        String id = receive_intent.getStringExtra("id");
        String nick = receive_intent.getStringExtra("nick");
        String room_num = receive_intent.getStringExtra("room_num");
//        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.180.121.58:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService LoginService = retrofit.create(LoginService.class);


        Thread t = new Thread(){
            @Override
            public void run() {

                while(!isInterrupted()){
                    try {
                        Thread.sleep(3000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Call<List<getMatching>> calls = LoginService.getMatching();

                                calls.enqueue(new Callback<List<getMatching>>() {
                                    @Override
                                    public void onResponse(Call<List<getMatching>> calls, Response<List<getMatching>> response) {
                                        if (!response.isSuccessful())
                                        {
                                            Log.d("onResponse 발동","Connection은 성공하였으나 code 에러 발생");
                                            return;
                                        }
                                        List<getMatching> Match = response.body();

                                        for ( getMatching items : Match)
                                        {
                                            if(items.getMatchIdx().equals(room_num)&& !userList.contains(items.getUserId())){
                                                userList.add(items.getUserId());

                                                Call<List<getItem>> call = LoginService.getItems();

                                                call.clone().enqueue(new Callback<List<getItem>>() {
                                                    @Override
                                                    public void onResponse(retrofit2.Call<List<getItem>> call, Response<List<getItem>> response) {
                                                        if (!response.isSuccessful())
                                                        {
                                                            Log.d("onResponse 발동","Connection은 성공하였으나 code 에러 발생");
                                                            return;
                                                        }
                                                        List<getItem> Items = response.body();
                                                        int i = 0;
                                                        for ( getItem item : Items)
                                                        {
                                                            if(userList.contains(item.getUserid())){
                                                                img = (ImageView)findViewById(imgId[i]);

                                                                Log.d("imuid", img.toString());
                                                                img.setImageResource(skinId[Integer.parseInt(item.getShopid())-1]);
                                                                i++;

                                                            }
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(retrofit2.Call<List<getItem>> call, Throwable t) {
                                                        Log.d("onFailure 발동","Connection Error");
                                                        return;
                                                    }
                                                });


                                            }

                                        }
                                        Log.d("userList : ", userList.toString());

                                    }

                                    @Override
                                    public void onFailure(Call<List<getMatching>> calls, Throwable t) {
                                        Log.d("onFailure 발동","Connection Error");
                                        return;
                                    }
                                });


                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                intent.putExtra("type", "firstIn");
                startActivity(intent);
            }
        });

        t.start();
    }
}