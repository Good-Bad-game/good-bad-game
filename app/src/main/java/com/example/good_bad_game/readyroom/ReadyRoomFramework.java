package com.example.good_bad_game.readyroom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.R;

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
        adapter.addItem(new Room("1"));
        adapter.addItem(new Room("2"));
        adapter.addItem(new Room("3"));
        adapter.addItem(new Room("4"));
        adapter.addItem(new Room("5"));
        adapter.addItem(new Room("6"));
        adapter.addItem(new Room("7"));
        adapter.addItem(new Room("8"));
        adapter.addItem(new Room("9"));
        adapter.addItem(new Room("10"));
        adapter.addItem(new Room("11"));
        adapter.addItem(new Room("12"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}