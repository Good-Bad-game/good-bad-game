package com.example.good_bad_game.friend;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.good_bad_game.R;

public class FriendFragment extends Fragment {
    private static final String TAG = "FriendFragment";

    private RecyclerView rvFriend;
    private FriendAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static FriendFragment newInstance() {
        return new FriendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.activity_friend_fragment, container, false);
        rvFriend = (RecyclerView) view.findViewById(R.id.rc_view);
        rvFriend.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rvFriend.setLayoutManager(layoutManager);
        rvFriend.scrollToPosition(0);
        adapter = new FriendAdapter();
        initDataset(adapter);

        rvFriend.setAdapter(adapter);
        rvFriend.setHasFixedSize(true);
        rvFriend.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initDataset();

    }

    private void initDataset(FriendAdapter adapter) {
        Log.d(TAG,"initDdataset");
        //for Test
        adapter.addItem(new Friend("써니", "온라인"));
        adapter.addItem(new Friend("완득이", "온라인"));
        adapter.addItem(new Friend("괴물", "온라인"));
        adapter.addItem(new Friend("라디오스타", "온라인"));
        adapter.addItem(new Friend("비열한 거리", "온라인"));
        adapter.addItem(new Friend("왕의 남자", "오프라인"));
        adapter.addItem(new Friend("아일랜드", "오프라인"));
        adapter.addItem(new Friend("웰컴 투 동막골", "오프라인"));
        adapter.addItem(new Friend("헬보이", "오프라인"));
        adapter.addItem(new Friend("백 투더 퓨처", "오프라인"));
        adapter.addItem(new Friend("여인의 향기","오프라인"));
        adapter.addItem(new Friend("쥬라기 공원","오프라인"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
