package com.example.good_bad_game.home;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.good_bad_game.R;
import com.example.good_bad_game.friend.FriendFragment;
import com.example.good_bad_game.myitem.MyItemFragment;
import com.example.good_bad_game.ranking.RankingFragment;
import com.example.good_bad_game.score.StoreFragment;

import java.util.Locale;

public class Home extends AppCompatActivity {

    private final static String TAG = "home";
    public TextToSpeech tts;

    private final int store = 1;
    private final int myitem = 2;
    private final int home = 3;
    private final int ranking = 4;
    private final int friend = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView user_1234 = findViewById(R.id.user_1234);
        Intent receive_intent = getIntent();
        String move_nick = receive_intent.getStringExtra("nickname");
        user_1234.setText(move_nick);

        tts = new TextToSpeech(Home.this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.KOREA);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        Toast.makeText(Home.this, "지원하지 않는 언어입니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.btn_store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentView(store);
            }
        });
        findViewById(R.id.btn_myItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentView(myitem);
            }
        });
        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentView(home);
            }
        });
        findViewById(R.id.btn_ranking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentView(ranking);
            }
        });
        findViewById(R.id.btn_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentView(friend);
            }
        });
        FragmentView(home);
    }
    private void FragmentView(int fragment){

        //FragmentTransactiom를 이용해 프래그먼트를 사용합니다.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment){
            case 1:
                // 첫번 째 프래그먼트 호출
                StoreFragment storeFrag = new StoreFragment();
                transaction.replace(R.id.fragment_container, storeFrag);
                transaction.commit();
                tts.speak("상점", TextToSpeech.QUEUE_FLUSH, null);
                break;

            case 2:
                // 두번 째 프래그먼트 호출
                MyItemFragment myitemFrag = new MyItemFragment();
                transaction.replace(R.id.fragment_container, myitemFrag);
                transaction.commit();
                tts.speak("보관함", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 3:
                // 첫번 째 프래그먼트 호출
                HomeFragment homeFrag = new HomeFragment();
                transaction.replace(R.id.fragment_container, homeFrag);
                transaction.commit();
                tts.speak("메인화면", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 4:
                // 첫번 째 프래그먼트 호출
                RankingFragment rankingFrag = new RankingFragment();
                transaction.replace(R.id.fragment_container, rankingFrag);
                transaction.commit();
                tts.speak("랭킹", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case 5:
                // 첫번 째 프래그먼트 호출
                FriendFragment friendFrag = new FriendFragment();
                transaction.replace(R.id.fragment_container, friendFrag);
                transaction.commit();
                tts.speak("친구", TextToSpeech.QUEUE_FLUSH, null);
                break;

        }

    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}