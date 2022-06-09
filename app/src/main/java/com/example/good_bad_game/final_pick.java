package com.example.good_bad_game;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class final_pick extends AppCompatActivity  {

    private String id;
    private String type;
    private String room_num;
    private int userList[];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_pick);

        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type"); // 방문 타입
        room_num = intent.getStringExtra("room_num");
        userList = intent.getIntArrayExtra("userList");

        ImageView good_pick = findViewById(R.id.good_pick);
        ImageView bad_pick = findViewById(R.id.bad_pick);

        good_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Bad.class);
                    intent.putExtra("room_num", room_num);
                    intent.putExtra("id",id);
                    intent.putExtra("type", type);
                    intent.putExtra("userList",userList);
                    startActivity(intent);

            }
        });

        good_pick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Good.class);
                intent.putExtra("room_num", room_num);
                intent.putExtra("id",id);
                intent.putExtra("type", type);
                intent.putExtra("userList",userList);
                startActivity(intent);

                return false;
            }
        });



        bad_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Good.class);
                intent.putExtra("room_num", room_num);
                intent.putExtra("id",id);
                intent.putExtra("type", type);
                intent.putExtra("userList",userList);
                startActivity(intent);
            }
        });

        bad_pick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Bad.class);
                intent.putExtra("room_num", room_num);
                intent.putExtra("id",id);
                intent.putExtra("type", type);
                intent.putExtra("userList",userList);
                startActivity(intent);

                return false;
            }
        });

    }


}
