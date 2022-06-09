package com.example.good_bad_game.readyroom;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.good_bad_game.R;
import com.example.good_bad_game.ReadyGame;
import com.example.good_bad_game.home.Home;
import com.example.good_bad_game.loginout.LoginService;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomCreate extends AppCompatActivity {
    private String TAG = "RoomCreate";
    private EditText room_name;
    Button room_create;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_room_create);

        tts = new TextToSpeech(RoomCreate.this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.KOREA);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        Toast.makeText(RoomCreate.this, "지원하지 않는 언어입니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Intent receive_intent = getIntent();
        String id = receive_intent.getStringExtra("id");

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.activity_room_create, null);

        builder.setView(dialoglayout);
        builder.setCancelable(false);


    }

    public void create(View view) {

        Intent receive_intent = getIntent();
        String id = receive_intent.getStringExtra("id");
        String nick = receive_intent.getStringExtra("nick");

        room_name = (EditText)findViewById(R.id.room_name);
        String room_title = room_name.getText().toString();

        if ( room_name.length() == 0 ) {//공백일 때 처리할 내용
            Log.d(TAG,"room_mane is null");
//            Toast.makeText(getApplicationContext(), "방이름을 정하시오.", Toast.LENGTH_SHORT).show();
            tts_speech("방이름을 정하시오.");
        } else {//공백이 아닐 때 처리할 내용
            String tmp = "방 이름 " + room_title + "이 만들어 졌습니다.";
            Log.d(TAG,"room_name is not null");
            tts_speech(tmp);
            Log.d(TAG,"POST ROOM INFOMATION START");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://54.180.121.58:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            LoginService LoginService = retrofit.create(LoginService.class);

            String host = id;
            String match_room = room_title;



            Room room = new Room(null, match_room, host);

            Log.d("TAG1",room.getHost());

            Call<Room> call = LoginService.Room(room);

            call.enqueue(new Callback<Room>() {
                @Override
                public void onResponse(Call<Room> call, Response<Room> response) {
                        if (!response.isSuccessful()){
                        Log.d("Error Code1 : ", String.valueOf(response.code()));
                        return;
                        }

                        Room RoomResponse = response.body();

                        Log.d("room_num : ", RoomResponse.getRoomNumber());
                        Log.d("Success Code : ", "Post 성공");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), ReadyGame.class);
                                intent.putExtra("id", id);
                                intent.putExtra("nick", nick);
                                intent.putExtra("v_type", "creator");
                                intent.putExtra("room_num", RoomResponse.getRoomNumber());
                                startActivity(intent);
                            }
                        }, 500);

                    }

                    @Override
                    public void onFailure(Call<Room> call, Throwable t) {
                        Log.d("Error Code2",t.getMessage());
                    }
                });

            }

        }

    public void tts_speech(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    }
