package com.example.good_bad_game.ranking;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.R;

import java.util.ArrayList;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    private static final String TAG = "RankingAdapter";

    private List<Ranking> items = new ArrayList<>();

    public void addItem(Ranking ranking){items.add(ranking);}

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
        private TextView nick_name;
        private ImageView skin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nick_name=itemView.findViewById(R.id.ranking_nickname);
            skin=itemView.findViewById(R.id.user_skin);
        }

        public void setItem(Ranking ranking){
            Log.d(TAG, "MyViewHolder: ");
            nick_name.setText(ranking.getNickname());


        }
    }
}
