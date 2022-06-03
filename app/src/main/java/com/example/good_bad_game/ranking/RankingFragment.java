package com.example.good_bad_game.ranking;

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

import com.example.good_bad_game.R;
import com.example.good_bad_game.friend.Friend;
import com.example.good_bad_game.friend.FriendAdapter;
import com.example.good_bad_game.friend.getFriend;
import com.example.good_bad_game.loginout.LoginService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingFragment extends Fragment {
    private static final String TAG = "RankingFragment";

    private RecyclerView rank_view;
    private RankingAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Ranking> RankingList = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        String id = getArguments().getString("id");
        String nick = getArguments().getString("nick");

        View view = inflater.inflate(R.layout.activity_ranking_fragment, container, false);
        rank_view = (RecyclerView) view.findViewById(R.id.rank_view);
        rank_view.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rank_view.setLayoutManager(layoutManager);
        rank_view.scrollToPosition(0);
        adapter = new RankingAdapter();
        initDataset(adapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rank_view.setAdapter(adapter);
                rank_view.setHasFixedSize(true);
                rank_view.setItemAnimator(new DefaultItemAnimator());

            }
        }, 500);

        return view;
    }

    private void initDataset(RankingAdapter adapter) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.180.121.58:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService LoginService = retrofit.create(LoginService.class);

        Call<List<Ranking>> call = LoginService.Ranking();

        call.enqueue(new Callback<List<Ranking>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Ranking>> call, Response<List<Ranking>> response) {
                if (!response.isSuccessful())
                {
                    Log.d("onResponse 발동","Connection은 성공하였으나 code 에러 발생");
                    return;
                }

                Log.d("Ranking : ", "정상 GET 발동");
                List<Ranking> rankings = response.body();

                for ( Ranking ranking : rankings)
                {
                    RankingList.add(ranking);
                }

                int max_idx = 0;
                int max_rank = 0;
                Ranking tmp_ranking;

                for (int i = 0; i < RankingList.size(); i++){
                    max_idx = i;
                    max_rank = Integer.parseInt(RankingList.get(i).getRank());
                    for (int j = i+1; j < RankingList.size(); j++){
                        if(max_rank < Integer.parseInt(RankingList.get(j).getRank())){
                            max_idx = j;
                            max_rank = Integer.parseInt(RankingList.get(j).getRank());
                        }
                    }
                    // i <-> max_idx
                    tmp_ranking = RankingList.get(i);
                    RankingList.set(i, RankingList.get(max_idx));
                    RankingList.set(max_idx, tmp_ranking);
                }

                for (int i = 0; i < RankingList.size(); i++){
                    Ranking tmp = RankingList.get(i);
                    tmp.setRank(Integer.toString(i+1).concat("위"));
                    adapter.addItem(tmp);
                }

            }

            @Override
            public void onFailure(retrofit2.Call<List<Ranking>> call, Throwable t) {
                Log.d("onFailure 발동","Connection Error");
                return;
            }
        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
