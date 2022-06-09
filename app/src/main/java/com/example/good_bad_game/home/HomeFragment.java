package com.example.good_bad_game.home;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.good_bad_game.R;
import com.example.good_bad_game.loginout.LoginService;
import com.example.good_bad_game.ranking.Ranking;
import com.example.good_bad_game.readyroom.ReadyRoomFramework;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    int[] skinId = {R.drawable.skin1, R.drawable.skin2, R.drawable.skin3, R.drawable.skin4, R.drawable.skin5, R.drawable.skin6};

    public TextToSpeech tts;
    public String TAG = "HomeFragment";
    private String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        Log.d("inflater : ",inflater.toString());

        View view = inflater.inflate(R.layout.activity_home_fragment, null);

        String id = getArguments().getString("id");
        String nick = getArguments().getString("nick");

        tts = new TextToSpeech(getActivity(), status -> {
            if (status == TextToSpeech.SUCCESS){
                int result = tts.setLanguage(Locale.KOREA);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(getContext(), "지원하지 않는 언어입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView myskin = view.findViewById(R.id.Skin);
        Button start = view.findViewById(R.id.btn_start);

        start.setOnClickListener(v -> {
            ((Home)getActivity()).replaceFragment(ReadyRoomFramework.newInstance(getArguments().getString("id"),getArguments().getString("nick")));
            tts.speak("게임시작", TextToSpeech.QUEUE_FLUSH, null);

        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.180.121.58:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService LoginService = retrofit.create(LoginService.class);

        Call<List<Ranking>> call = LoginService.Ranking();

        call.enqueue(new Callback<List<Ranking>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Ranking>> call, Response<List<Ranking>> response) {
                if (!response.isSuccessful())
                {
                    Log.d("onResponse 발동","Connection은 성공하였으나 code 에러 발생");
                    return;
                }

                Log.d("Ranking : ", "정상 GET 발동");
                List<Ranking> rankings = response.body();

                for ( Ranking ranking : rankings)
                {
                    if (Integer.parseInt(id) == ranking.getUid()){
                        Log.d("getUid : ", Integer.toString(ranking.getSid()));
                        myskin.setImageResource(skinId[ranking.getSid()]);
                    }
                }


            }

            @Override
            public void onFailure(retrofit2.Call<List<Ranking>> call, Throwable t) {
                Log.d("onFailure 발동","Connection Error");
                return;
            }
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
