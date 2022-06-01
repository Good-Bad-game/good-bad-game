package com.example.good_bad_game.readyroom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.good_bad_game.LoginService;
import com.example.good_bad_game.R;
import com.example.good_bad_game.ReadyGame;
import com.example.good_bad_game.friend.Friend;
import com.example.good_bad_game.getFriend;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        Button create = (Button) view.findViewById(R.id.make_room);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RoomCreate.class);
                startActivity(intent);

            }
        });

        rvRoom = (RecyclerView) view.findViewById(R.id.rc_roomView);
        rvRoom.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rvRoom.setLayoutManager(layoutManager);
        rvRoom.scrollToPosition(0);
        adapter = new RoomAdapter();
        initDataset(adapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvRoom.setAdapter(adapter);
                rvRoom.setHasFixedSize(true);
                rvRoom.setItemAnimator(new DefaultItemAnimator());
            }
        }, 500);


        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void initDataset(RoomAdapter adapter) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.180.121.58:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService LoginService = retrofit.create(LoginService.class);
        Call<List<getRoom>> call = LoginService.getRoom();

        call.enqueue(new Callback<List<getRoom>>() {
            @Override
            public void onResponse(retrofit2.Call<List<getRoom>> call, Response<List<getRoom>> response) {
                if (!response.isSuccessful())
                {
                    Log.d("onResponse 발동","Connection은 성공하였으나 code 에러 발생");
                    return;

                }

                List<getRoom> Rooms = response.body();

                for ( getRoom Roomitem : Rooms)
                {
                    Log.d("onResponse 발동","데이터 가져오기 시작");
                    Log.d("Room_num : ", Roomitem.getRoomNumber());
                    Log.d("Room_Title : ", Roomitem.getRoomTitle());
                    adapter.addItem(new Room (Roomitem.getRoomNumber(), Roomitem.getRoomTitle()));
                }

            }

            @Override
            public void onFailure(retrofit2.Call<List<getRoom>> call, Throwable t) {
                Log.d("onFailure 발동","Connection Error");
                return;
            }
        });


        //for Test


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}