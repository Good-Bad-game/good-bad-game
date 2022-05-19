package com.example.good_bad_game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.activity_home_fragment, null);
        Button start = view.findViewById(R.id.btn_start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home)getActivity()).replaceFragment(ReadyRoomFramework.newInstance());
//                ((Home)getActivity()).replaceFragment(FriendFragment.newInstance());
            }
        });
//        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);
//        Button start = view.findViewById(R.id.btn_start);
//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),InGame.class);
//                startActivity(intent);
//
//            }
//        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
