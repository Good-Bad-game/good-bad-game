package com.example.good_bad_game.myitem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.R;
import com.example.good_bad_game.store.Store;
import com.example.good_bad_game.store.StoreAdapter;
import com.example.good_bad_game.store.StoreFragment;

public class ItemFragment extends Fragment {

    private static String TAG = "ItemFragment";

    private RecyclerView rvItem;
    private ItemAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;
    private View view;

    int[] skinId = {R.drawable.skin1, R.drawable.skin2, R.drawable.skin3, R.drawable.skin4, R.drawable.skin5, R.drawable.skin6};

    public static ItemFragment newInstance() {
        return new ItemFragment();
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

        view = inflater.inflate(R.layout.activity_my_item_fragment, container, false);
        rvItem = (RecyclerView) view.findViewById(R.id.item_rc_view);
        rvItem.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        rvItem.setLayoutManager(layoutManager);
        rvItem.scrollToPosition(0);
        adapter = new ItemAdapter();
        initDataset(adapter);
        rvItem.setAdapter(adapter);
        rvItem.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    private void initDataset(ItemAdapter adapter) {
        Log.d(TAG,"initDataset");
        adapter.addItem(new Item("아이템1",R.drawable.skin1));
        adapter.addItem(new Item("아이템2",R.drawable.skin2));
        adapter.addItem(new Item("아이템3",R.drawable.skin3));
        adapter.addItem(new Item("아이템4",R.drawable.skin4));
        adapter.addItem(new Item("아이템5",R.drawable.skin5));
        adapter.addItem(new Item("아이템6",R.drawable.skin6));
        adapter.addItem(new Item("아이템7",R.drawable.skin1));
        adapter.addItem(new Item("아이템8",R.drawable.skin2));
        adapter.addItem(new Item("아이템9",R.drawable.skin3));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
