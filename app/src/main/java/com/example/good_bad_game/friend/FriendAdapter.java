package com.example.good_bad_game.friend;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.R;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder>{
    private static final String TAG = "FriendAdepter";

    //리스트는 무조건 데이터를 필요로함
    private List<Friend> items = new ArrayList<>();

    public void addItem(Friend friend){
        items.add(friend);
    }
    //껍데기만 만듬. 1번 실행
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }
    //껍데기에 데이터 바인딩. 2번 실행
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        Friend friend = items.get(position);
        holder.setItem(friend);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return items.size();
    }
    //ViewHolder : 뷰들의 책꽂이
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //규칙1
        private TextView fri_nick;
        private TextView online;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //규칙2
            fri_nick=itemView.findViewById(R.id.fri_nick);
            online=itemView.findViewById(R.id.online);
        }

        //규칙3
        public void setItem(Friend friend){
            Log.d(TAG, "MyViewHolder: ");
            fri_nick.setText(friend.getNickname());
            if (friend.getOnline() == "ON"){
                online.setTextColor(Color.parseColor("#66FFB2"));
            }
            else {
                online.setTextColor(Color.parseColor("#FF3333"));
            }
            online.setText(friend.getOnline());
        }
    }

}
