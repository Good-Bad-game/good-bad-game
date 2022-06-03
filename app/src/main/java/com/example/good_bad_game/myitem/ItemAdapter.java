package com.example.good_bad_game.myitem;

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {


    private static final String TAG = "ItemAdapter";
    private List<Item> items = new ArrayList<>();
    private ImageView image;
    private TextView name;
    private ItemFragment itemFragment;

    public void addItem(Item item){ items.add(item); }

    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "MyViewHolder");
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_item, parent, false);

        return new ItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolder holder, int position) {
        Item item = items.get(position);
        holder.setItem(item);

/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),item.getName(),Toast.LENGTH_SHORT).show();

            }
        });*/

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView selectedImage;
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

        }

        //규칙3
        public void setItem(Item item){
            image.setImageResource(item.getImage());
            name.setText(item.getName());
        }
    }

}



