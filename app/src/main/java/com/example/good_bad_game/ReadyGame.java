package com.example.good_bad_game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.good_bad_game.loginout.LoginService;
import com.example.good_bad_game.readyroom.Room;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadyGame extends AppCompatActivity {

    private String id;
    private String v_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ready_game);
        Button btn_start = findViewById(R.id.btn_start);

        Intent receive_intent = getIntent();
        id = receive_intent.getStringExtra("id");
        v_type = receive_intent.getStringExtra("v_type"); // 방문 타입
        String nick = receive_intent.getStringExtra("nick");
        String room_num = receive_intent.getStringExtra("room_num");
        Toast.makeText(getApplicationContext(),v_type,Toast.LENGTH_SHORT).show();
        Log.d("v_type : ", v_type);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                intent.putExtra("type", "firstIn");
                startActivity(intent);
            }
        });

        if (v_type.equals("creator")){

            Log.d("v_type : ","감지 후 if문 실행됨.");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://54.180.121.58:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Log.d("ID : ", id);
            Log.d("Room_num : ", room_num);

            Matching matching = new Matching(null, id, room_num);

            LoginService LoginService = retrofit.create(LoginService.class);

            Call<Matching> call = LoginService.Matching(matching);

            call.enqueue(new Callback<Matching>() {
                @Override
                public void onResponse(Call<Matching> call, Response<Matching> response) {
                    if (!response.isSuccessful()){
                        Log.d("Error Code1 : ", String.valueOf(response.code()));
                        return;
                    }

                    Matching MatchingResponse = response.body();

                    Log.d("Success Code : ", "Post 성공1");

                }

                @Override
                public void onFailure(Call<Matching> call, Throwable t) {
                    Log.d("Error Code2",t.getMessage());
                }
            });

        }

    }

    @Override
    public void onBackPressed() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.180.121.58:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService LoginService = retrofit.create(LoginService.class);

        LoginService.deleteMatching(1).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "삭제 실패" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

        finish();
    }
}