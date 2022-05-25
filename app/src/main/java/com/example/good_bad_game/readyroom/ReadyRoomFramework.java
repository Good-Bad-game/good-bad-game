package com.example.good_bad_game.readyroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.R;
import com.example.good_bad_game.ReadyGame;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReadyRoomFramework extends Fragment {
    private static String TAG = "ReadyRoomFramework";

    private RecyclerView rvRoom;
    private RoomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static ReadyRoomFramework newInstance() {
        return new ReadyRoomFramework();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Log.d(TAG, "OnCreateView");


        View view = inflater.inflate(R.layout.activity_ready_room_framework,container, false);
        rvRoom = (RecyclerView) view.findViewById(R.id.rc_roomView);
        rvRoom.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rvRoom.setLayoutManager(layoutManager);
        rvRoom.scrollToPosition(0);
        adapter = new RoomAdapter();
        initDataset(adapter);

        rvRoom.setAdapter(adapter);
        rvRoom.setHasFixedSize(true);
        rvRoom.setItemAnimator(new DefaultItemAnimator());
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void initDataset(RoomAdapter adapter) {
        //for Test
        adapter.addItem(new Room("001", "테스트1번방"));
        adapter.addItem(new Room("002", "테스트2번방"));
        adapter.addItem(new Room("003", "테스트3번방"));
        adapter.addItem(new Room("004", "테스트4번방"));
        adapter.addItem(new Room("005", "테스트5번방"));
        adapter.addItem(new Room("006", "테스트6번방"));
        adapter.addItem(new Room("007", "테스트7번방"));
        adapter.addItem(new Room("008", "테스트8번방"));
        adapter.addItem(new Room("009", "테스트9번방"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}