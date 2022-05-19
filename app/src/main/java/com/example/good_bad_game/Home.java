package com.example.good_bad_game;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Home extends AppCompatActivity {

    private final static String TAG = "home";
    public TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tts = new TextToSpeech(Home.this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.KOREA);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        Toast.makeText(Home.this, "지원하지 않는 언어입니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button start = findViewById(R.id.btn_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyGame.class);
                startActivity(intent);
            }
        });
        Button store = findViewById(R.id.btn_store);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak("상점", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(getApplicationContext(), Store.class);
                startActivity(intent);
            }
        });
        Button myitem = findViewById(R.id.btn_myItem);
        myitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyItem.class);
                startActivity(intent);
            }
        });
        Button ranking = findViewById(R.id.btn_ranking);
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ranking.class);
                startActivity(intent);
            }
        });
        Button friend = findViewById(R.id.btn_friend);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuFriend.class);
                startActivity(intent);
            }
        });

    }


}