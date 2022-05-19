package com.example.good_bad_game.readyroom;

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

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {
    private static final String TAG = "RoomAdapter";

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
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return items.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        private TextView roomNumber;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            roomNumber = itemView.findViewById(R.id.room_number);
        }


        public void setItem(Room room) {
            Log.d(TAG, "MyViewHolder: ");
            roomNumber.setText(room.getRoomNumber());
        }
    }
}