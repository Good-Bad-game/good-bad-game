package com.example.good_bad_game.friend;

import android.os.Bundle;
import android.os.Handler;
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

import com.example.good_bad_game.loginout.LoginService;
import com.example.good_bad_game.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendFragment extends Fragment {
    private static final String TAG = "FriendFragment";

    private RecyclerView rvFriend;
    private FriendAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static FriendFragment newInstance() {
        return new FriendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.activity_friend_fragment, container, false);
        rvFriend = (RecyclerView) view.findViewById(R.id.rc_view);
        rvFriend.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rvFriend.setLayoutManager(layoutManager);
        rvFriend.scrollToPosition(0);
        adapter = new FriendAdapter();
        initDataset(adapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvFriend.setAdapter(adapter);
                rvFriend.setHasFixedSize(true);
                rvFriend.setItemAnimator(new DefaultItemAnimator());

            }
        }, 500);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void initDataset(FriendAdapter adapter) {

        ArrayList FriendList = new ArrayList();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.180.121.58:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService LoginService = retrofit.create(LoginService.class);
        Call<List<getFriend>> call = LoginService.getFriends();

        call.enqueue(new Callback<List<getFriend>>() {
            @Override
            public void onResponse(retrofit2.Call<List<getFriend>> call, Response<List<getFriend>> response) {
                if (!response.isSuccessful())
                {
                    Log.d("onResponse 발동","Connection은 성공하였으나 code 에러 발생");
                    return;

                }

                List<getFriend> Friends = response.body();

                for ( getFriend getFriend : Friends)
                {
                    Log.d("onResponse 발동","데이터 가져오기 시작");
                    Log.d("nick2 : ", getFriend.get_nick2());
                    adapter.addItem(new Friend(getFriend.get_nick2(), "ON"));
                }

            }

            @Override
            public void onFailure(retrofit2.Call<List<getFriend>> call, Throwable t) {
                Log.d("onFailure 발동","Connection Error");
                return;
            }
        });


        Log.d(TAG,"initDdataset");



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
