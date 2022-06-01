package com.example.good_bad_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReadyGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ready_game);
        Button btn_start = findViewById(R.id.btn_start);

        Intent receive_intent = getIntent();
        String id = receive_intent.getStringExtra("id");
        String nick = receive_intent.getStringExtra("nick");
        Toast.makeText(getApplicationContext(),nick,Toast.LENGTH_SHORT).show();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                intent.putExtra("type", "firstIn");
                startActivity(intent);
            }
        });
    }
}