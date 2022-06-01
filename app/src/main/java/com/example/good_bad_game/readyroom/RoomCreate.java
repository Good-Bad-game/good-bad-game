package com.example.good_bad_game.readyroom;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class RoomCreate extends AppCompatActivity {
    private String TAG = "RoomCreate";
    private EditText room_name;
    Button room_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_room_create);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.activity_room_create, null);

        builder.setView(dialoglayout);
        builder.setCancelable(false);


    }

    public void create(View view) {
        room_name = (EditText)findViewById(R.id.room_name);
        room_name.getText().toString();

        if ( room_name.length() == 0 ) {//공백일 때 처리할 내용
            Log.d(TAG,"room_mane is null");
            Toast.makeText(getApplicationContext(), "방이름을 정하시오.", Toast.LENGTH_SHORT).show();
        } else {//공백이 아닐 때 처리할 내용
            Log.d(TAG,"room_mane is not null");
            Intent intent = new Intent(getApplicationContext(), ReadyGame.class);
            intent.putExtra("roomname",room_name.toString());
            setResult(RESULT_OK, intent);
            startActivity(intent);
        }

    }
}