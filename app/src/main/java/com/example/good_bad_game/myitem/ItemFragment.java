package com.example.good_bad_game.myitem;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.R;
import com.example.good_bad_game.getItem;
import com.example.good_bad_game.home.Home;
import com.example.good_bad_game.home.HomeFragment;
import com.example.good_bad_game.loginout.LoginService;
import com.example.good_bad_game.ranking.Ranking;
import com.example.good_bad_game.store.Store;
import com.example.good_bad_game.store.StoreAdapter;
import com.example.good_bad_game.store.StoreFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemFragment extends Fragment {

    private String rank_num[] = {"1500", "1300", "0", "1700"};
    private static String TAG = "ItemFragment";

    private RecyclerView rvItem;
    private ItemAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private String id;

    int[] skinId = {R.drawable.skin1, R.drawable.skin2, R.drawable.skin3, R.drawable.skin4, R.drawable.skin5, R.drawable.skin6};

    public static ItemFragment newInstance() {
        return new ItemFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        if(getArguments() != null){
            id = getArguments().getString("id");
            String nick = getArguments().getString("nick");
//            Toast.makeText(getActivity(),id,Toast.LENGTH_SHORT).show();
        }


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
                    if (Integer.parseInt(id) == ranking.getUid()){
                        Log.d("getUid : ", Integer.toString(ranking.getSid()));
                        adapter.setNum(ranking.getSid());
                    }
                }


            }

            @Override
            public void onFailure(retrofit2.Call<List<Ranking>> call, Throwable t) {
                Log.d("onFailure 발동","Connection Error");
                return;
            }
        });


        if(getArguments() != null){
            id = getArguments().getString("id");
        }

        view = inflater.inflate(R.layout.activity_my_item_fragment, container, false);
        rvItem = (RecyclerView) view.findViewById(R.id.item_rc_view);
        rvItem.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        rvItem.setLayoutManager(layoutManager);
        rvItem.scrollToPosition(0);
        adapter = new ItemAdapter();

        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("selected idx : ", Integer.toString(pos));
                adapter.Cnt_init();
                adapter.setNum(pos);
                fragmentRefresh();

                // 클릭시 HomeFragment의 프로필 사진 변경.

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://54.180.121.58:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                LoginService LoginService = retrofit.create(LoginService.class);

                Ranking rank = new Ranking(Integer.parseInt(id), pos, rank_num[Integer.parseInt(id)]);

                LoginService.putRanking(Integer.parseInt(id), rank).enqueue(new Callback<Ranking>() {
                    @Override
                    public void onResponse(Call<Ranking> call, Response<Ranking> response) {
                        if(response.isSuccessful()){
                            Log.d("성공", "성공");
                        }
                        else{
                            Log.d("실패","실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<Ranking> call, Throwable t) {

                    }
                });

                Call<List<getItem>> call2 = LoginService.getItems();

                call2.enqueue(new Callback<List<getItem>>() {
                    @Override
                    public void onResponse(Call<List<getItem>> call, Response<List<getItem>> response) {
                        if(response.isSuccessful()){
                            Log.d("성공", "성공");

                            List<getItem> Items = response.body();

                            for( getItem item : Items){
                                if(item.getUserid().equals(id)){
                                    Log.d("같다 발동!","");

                                    getItem tmp_Item = new getItem(id, Integer.toString(pos), item.getCash(), item.getCoin());

                                    LoginService.putItem(Integer.parseInt(id), tmp_Item).enqueue(new Callback<getItem>() {
                                        @Override
                                        public void onResponse(Call<getItem> call, Response<getItem> response) {
                                            Log.d("최종 성공!","");
                                        }

                                        @Override
                                        public void onFailure(Call<getItem> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }
                        else{
                            Log.d("실패","실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<getItem>> call, Throwable t) {

                    }
                });

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initDataset(adapter);
                rvItem.setAdapter(adapter);
                rvItem.setItemAnimator(new DefaultItemAnimator());

            }
        }, 500);

        return view;

    }

    private void initDataset(ItemAdapter adapter) {
        Log.d(TAG,"initDataset");
        adapter.addItem(new Item("회색악마",R.drawable.skin1));
        adapter.addItem(new Item("프랑켄",R.drawable.skin2));
        adapter.addItem(new Item("아수라",R.drawable.skin3));
        adapter.addItem(new Item("터미넴",R.drawable.skin4));
        adapter.addItem(new Item("로봇",R.drawable.skin5));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void fragmentRefresh(){
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.detach(this).attach(this).commit();
        rvItem.setAdapter(rvItem.getAdapter());
    }
}
