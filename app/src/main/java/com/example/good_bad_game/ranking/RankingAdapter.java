package com.example.good_bad_game.ranking;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.R;
import com.example.good_bad_game.home.Home;
import com.example.good_bad_game.loginout.Login;
import com.example.good_bad_game.loginout.LoginService;
import com.example.good_bad_game.loginout.SignIn;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    private static final String TAG = "RankingAdapter";

    private List<Ranking> items = new ArrayList<>();

    public void addItem(Ranking ranking){items.add(ranking);}

    int[] skinId = {R.drawable.skin1, R.drawable.skin2, R.drawable.skin3, R.drawable.skin4, R.drawable.skin5, R.drawable.skin6};

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "oncreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ranking_item,parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    Log.d(TAG, "onBindViewHolder:"+position);
    Ranking ranking = items.get(position);
    holder.setItem(ranking);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView rank;
        private TextView nick_name;
        private ImageView skin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rank=itemView.findViewById(R.id.ranking);
            nick_name=itemView.findViewById(R.id.ranking_nickname);
            skin=itemView.findViewById(R.id.user_skin);
        }

        public void setItem(Ranking ranking){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://54.180.121.58:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            LoginService LoginService = retrofit.create(LoginService.class);

            Call<List<Login>> call = LoginService.getPosts();

            call.enqueue(new Callback<List<Login>>() {
                @Override
                public void onResponse(retrofit2.Call<List<Login>> call, Response<List<Login>> response) {
                    if (!response.isSuccessful())
                    {
                        Log.d("onResponse 발동","Connection은 성공하였으나 code 에러 발생");
                        return;

                    }

                    List<Login> Login_infos = response.body();

                    for ( Login login_info : Login_infos)
                    {
                        if(login_info.get_id().equals(Integer.toString(ranking.getUid()))){
                            nick_name.setText(login_info.get_nickname());
                        }
                    }

                }

                @Override
                public void onFailure(retrofit2.Call<List<Login>> call, Throwable t) {
                }
            });

            rank.setText(ranking.getRank());
            skin.setImageResource(skinId[ranking.getUid()]);

        }
    }
}
