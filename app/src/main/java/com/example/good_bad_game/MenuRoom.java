package com.example.good_bad_game;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MenuRoom extends AppCompatActivity {
    private static final String TAG = "MenuRoom";

    private RecyclerView rvRoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate");
//        setContentView(R.layout.activity_read_room_framework);
//
//        rvRoom = findViewById(R.id.rc_roomView);
//
//        RoomAdapter adapter = new RoomAdapter();
//        adapter.addItem(new Room("1"));
//        adapter.addItem(new Room("2"));
//        adapter.addItem(new Room("3"));
//        adapter.addItem(new Room("4"));
//        adapter.addItem(new Room("5"));
//        adapter.addItem(new Room("6"));
//        adapter.addItem(new Room("7"));
//        adapter.addItem(new Room("8"));
//        adapter.addItem(new Room("9"));
//        adapter.addItem(new Room("10"));
//        adapter.addItem(new Room("11"));
//        adapter.addItem(new Room("12"));
//
//        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
//        rvRoom.setLayoutManager(new LinearLayoutManager(this));
//        rvRoom.setAdapter(adapter);
    }
}
