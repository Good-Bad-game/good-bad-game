package com.example.good_bad_game.readyroom;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.loginout.LoginService;
import com.example.good_bad_game.Matching;
import com.example.good_bad_game.R;
import com.example.good_bad_game.ReadyGame;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {
    private static final String TAG = "RoomAdapter";

    private String mParam1;
    private String mParam2;

    public RoomAdapter(String mParam1, String mParam2){
        this.mParam1 = mParam1;
        this.mParam2 = mParam2;
    }

    //리스트는 무조건 데이터를 필요로함
    private List<Room> items = new ArrayList<>();

    public void addItem(Room room){items.add(room);}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.room_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        Room room = items.get(position);
        holder.setItem(room);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // room_num ->  matchIdx
                // id -> userId
                String userId = mParam1;
                String matchIdx = room.getRoomNumber();
                String nick = mParam2;

                Log.d("id : ", userId);
                Log.d("matchIdx : ", matchIdx);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://54.180.121.58:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Matching matching = new Matching(null, userId, matchIdx,null);

                LoginService LoginService = retrofit.create(LoginService.class);

                Call<Matching> call = LoginService.Matching(matching);

                call.enqueue(new Callback<Matching>() {
                    @Override
                    public void onResponse(Call<Matching> call, Response<Matching> response) {
                        if (!response.isSuccessful()){
                            Log.d("Error Code1 : ", String.valueOf(response.code()));
                            return;
                        }

                        Matching MatchingResponse = response.body();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(v.getContext(), ReadyGame.class);
                                intent.putExtra("id", userId);
                                intent.putExtra("nick", nick);
                                intent.putExtra("room_num", room.getRoomNumber());
                                intent.putExtra("v_type", "visiter");
                                ContextCompat.startActivity(v.getContext(), intent, null);;
                            }
                        }, 500);

                    }

                    @Override
                    public void onFailure(Call<Matching> call, Throwable t) {
                        Log.d("Error Code2",t.getMessage());
                    }
                });




            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return items.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        private TextView roomNumber;
        private TextView roomTitle;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNumber = itemView.findViewById(R.id.room_number);
            roomTitle = itemView.findViewById(R.id.room_title);


        }

        public void setItem(Room room) {
            Log.d(TAG, "MyViewHolder: ");
            roomNumber.setText(room.getRoomNumber());
            roomTitle.setText(room.getRoomTitle());
        }
    }
}
