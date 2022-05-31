package com.example.good_bad_game.store;

import android.content.Context;
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

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {

    private static final String TAG = "StoreAdapter";
    private List<Store> items = new ArrayList<>();
    Context context;
    private ImageView image;
    private TextView name;
    private StoreFragment storeFragment;

    public StoreAdapter(Context context, ArrayList<Store> list, StoreFragment storeFragment) {
        super();
        this.context = context;
        this.items = list;
        this.storeFragment = storeFragment;
    }
    public StoreAdapter() {
        super();
    }
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

    public static int[] LETTER_PAPAER_ARRAY = {
            R.drawable.thumb_up,
            R.drawable.thumb_down,
    };
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView selectedImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recylcerview_row_image);
            name = itemView.findViewById(R.id.recylcerview_row_name);

//            selectedImage = itemView.findViewById(R.id.imageView);
//            selectedImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    id = Defind.L
//                }
//            });
        }

        //규칙3
        public void setItem(Store store){
            Log.d(TAG, store.getName());
            image.setImageResource(store.getImage());
            name.setText(store.getName());
        }
    }

}
