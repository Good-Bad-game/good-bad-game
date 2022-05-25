package com.example.good_bad_game.friend;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MenuFriend extends AppCompatActivity {
    private static final String TAG = "MenuFriend";

    private RecyclerView rvFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate");
//        setContentView(R.layout.activity_friend);
//
//        rvFriend=findViewById(R.id.rc_view);
//
//        FriendAdapter adapter=new FriendAdapter();
//        adapter.addItem(new Friend("써니"));
//        adapter.addItem(new Friend("완득이"));
//        adapter.addItem(new Friend("괴물"));
//        adapter.addItem(new Friend("라디오스타"));
//        adapter.addItem(new Friend("비열한 거리"));
//        adapter.addItem(new Friend("왕의 남자"));
//        adapter.addItem(new Friend("아일랜드"));
//        adapter.addItem(new Friend("웰컴 투 동막골"));
//        adapter.addItem(new Friend("헬보이"));
//        adapter.addItem(new Friend("백 투더 퓨처"));
//        adapter.addItem(new Friend("여인의 향기"));
//        adapter.addItem(new Friend("쥬라기 공원"));
//
//        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
//        rvFriend.setLayoutManager(layoutManager);
//        rvFriend.setAdapter(adapter);
    }

}