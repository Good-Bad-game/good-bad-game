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
    View fragmentView;


    public StoreAdapter(Context context, ArrayList<Store> list) {
        super();
        this.context = context;
        this.items = list;
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
        fragmentView = inflater.inflate(R.layout.activity_store_fragment,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Store store = items.get(position);
        holder.setItem(store);
//        holder.image.setImageResource(items.get(position).image);
//        holder.name.setText(items.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, store.getName());
                Log.d(TAG, String.valueOf(store.getImage()));
//                Intent intent = new Intent(v.getContext(), );
//                intent.putExtra("itemName", store.getName());
//                ContextCompat.startActivity(v.getContext(), intent, null);

                image = (ImageView) fragmentView.findViewById(R.id.imageView);
                image.setImageResource(R.drawable.thumb_down);

            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return items.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recylcerview_row_image);
            name = itemView.findViewById(R.id.recylcerview_row_name);
        }

        //규칙3
        public void setItem(Store store){
            Log.d(TAG, "MyViewHolder: ");
            image.setImageResource(store.getImage());
            name.setText(store.getName());
        }
    }
}
