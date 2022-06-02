package com.example.good_bad_game.store;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.R;

public class StoreFragment extends Fragment {
    private static String TAG = "StoreFragment";

    private RecyclerView rvStore;
    private StoreAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;

    private String mParam1;
    private String mParam2;

    public static StoreFragment newInstance() {
        return new StoreFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        if(getArguments() != null){
            String id = getArguments().getString("id");
            String nick = getArguments().getString("nick");
            Toast.makeText(getActivity(),id,Toast.LENGTH_SHORT).show();
        }

        view = inflater.inflate(R.layout.activity_store_fragment, container, false);
        rvStore = (RecyclerView) view.findViewById(R.id.grid_recyclerview);
        rvStore.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.HORIZONTAL,false);
        rvStore.setLayoutManager(layoutManager);
        rvStore.scrollToPosition(0);
        adapter = new StoreAdapter();
        initDataset(adapter);

        rvStore.setAdapter(adapter);

        rvStore.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    private void initDataset(StoreAdapter adapter) {
        Log.d(TAG,"initDataset");
        //for Test
        adapter.addItem(new Store("상품1",R.drawable.skin1));
        adapter.addItem(new Store("상품2",R.drawable.skin2));
        adapter.addItem(new Store("상품3",R.drawable.skin3));
        adapter.addItem(new Store("상품4",R.drawable.skin4));
        adapter.addItem(new Store("상품5",R.drawable.skin5));
        adapter.addItem(new Store("상품6",R.drawable.skin6));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
