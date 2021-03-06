package com.example.good_bad_game.store;

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.Context;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {

    // OnClickListener Custom from yohan--------------------

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    //-----------------------------------------------------

    private static final String TAG = "StoreAdapter";
    private List<Store> items = new ArrayList<>();
    private ImageView image;
    private TextView name;

    public void addItem(Store store){ items.add(store); }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "MyViewHolder");
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.store_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Store store = items.get(position);

        holder.setItem(store);

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextToSpeech tts;


        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recylcerview_row_image);
            name = itemView.findViewById(R.id.recylcerview_row_name);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        if (onItemClickListener != null){
                            onItemClickListener.onItemClick(position);
                        }
                    }
                    Store item = items.get(getAdapterPosition());
                    String name = item.getName();
                    tts.speak(name, TextToSpeech.QUEUE_FLUSH, null);
                }
            });

            tts = new TextToSpeech(itemView.getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != ERROR) {
                        // ????????? ????????????.
                        tts.setLanguage(Locale.KOREAN);
                    }
                }
            });

        }

        //??????3
        public void setItem(Store store){
            Log.d(TAG, store.getName());
            image.setImageResource(store.getImage());
            name.setText(store.getName());
        }
    }





}
