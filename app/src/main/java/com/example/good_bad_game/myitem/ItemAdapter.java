package com.example.good_bad_game.myitem;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.R;
import com.example.good_bad_game.store.Store;
import com.example.good_bad_game.store.StoreAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    // OnClickListener Custom from yohan--------------------

    private int selected_idx = 0;
    private int cnt = 0;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public void setNum(int i){
        selected_idx = i;
        Log.d("TAG", "selected_idx가 " + selected_idx + "로 변경되었습니다.");
    }

    public void Cnt_init(){
        cnt = 0;
    }

    //-----------------------------------------------------

    private static final String TAG = "ItemAdapter";
    private List<Item> items = new ArrayList<>();
    private ImageView image;
    private TextView name;

    public void addItem(Item item){ items.add(item); }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "MyViewHolder");
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = items.get(position);
        holder.setItem(item);


    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount: ", String.valueOf(items.size()));
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextToSpeech tts;


        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.rc_row_image);
            name = itemView.findViewById(R.id.rc_row_name);

            tts = new TextToSpeech(itemView.getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != ERROR) {
                        // 언어를 선택한다.
                        tts.setLanguage(Locale.KOREAN);
                    }
                }
            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        if (onItemClickListener != null){
                            onItemClickListener.onItemClick(position);
                        }
                    }
//                    Item item = items.get(getAdapterPosition());
//                    String name = item.getName();
//                    Log.d("name ", name);
//                    tts.speak(name, TextToSpeech.QUEUE_FLUSH, null);
                }
            });

        }

        //규칙3
        public void setItem(Item item){
            Log.d("selected_idx : ", Integer.toString(selected_idx));
            Log.d("cnt : ", Integer.toString(cnt));



            if(cnt == selected_idx){
                name.setTextColor(Color.GREEN);
                Log.d("setTextColor : ", "Green 발동");
            }
            image.setImageResource(item.getImage());
            name.setText(item.getName());
            cnt++;
        }
    }

}



