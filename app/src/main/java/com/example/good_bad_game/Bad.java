package com.example.good_bad_game;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;

public class Bad extends AppCompatActivity  {

    private String id;
    private String type;
    private String room_num;
    private int userList[];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad);

        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type"); // 방문 타입
        room_num = intent.getStringExtra("room_num");
        userList = intent.getIntArrayExtra("userList");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ReadyGame.class);
                intent.putExtra("room_num", room_num);
                intent.putExtra("id",id);
                intent.putExtra("type", type);
                intent.putExtra("v_type", "visitor");
                intent.putExtra("userList",userList);
                startActivity(intent);
            }
        }, 4000);


    }
}
