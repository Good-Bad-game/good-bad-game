package com.example.good_bad_game.home;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.good_bad_game.R;
import com.example.good_bad_game.readyroom.ReadyRoomFramework;

import java.util.Locale;

public class HomeFragment extends Fragment {

    public TextToSpeech tts;
    public String TAG = "HomeFragment";
    private String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);


//        String id = getArguments().getString("id");
//        String nick = getArguments().getString("nick");


        tts = new TextToSpeech(getActivity(), status -> {
            if (status == TextToSpeech.SUCCESS){
                int result = tts.setLanguage(Locale.KOREA);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(getContext(), "지원하지 않는 언어입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        View view = inflater.inflate(R.layout.activity_home_fragment, null);
        Button start = view.findViewById(R.id.btn_start);

        start.setOnClickListener(v -> {
            ((Home)getActivity()).replaceFragment(ReadyRoomFramework.newInstance(getArguments().getString("id"),getArguments().getString("nick")));
            tts.speak("게임시작", TextToSpeech.QUEUE_FLUSH, null);

        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
