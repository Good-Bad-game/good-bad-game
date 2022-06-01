package com.example.good_bad_game;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Vote extends AppCompatActivity {
    private static String TAG = "VoteActivity";
    private TextToSpeech tts;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

//      tts객체 생성
        tts = new TextToSpeech(Vote.this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.KOREA);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        Toast.makeText(Vote.this, "지원하지 않는 언어입니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        tts.speak("투표를 시작합니다.", TextToSpeech.QUEUE_FLUSH, null);


        // 타이머 코딩 부분 --------------------------------------------------------------------------
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();

        TextView count_view = findViewById(R.id.time);

        int player_num = 6;     //6명 초기값 ( 이후 수정할 것 )
        String player_num2 = Integer.toString(player_num);
        String count_num = "040";

        count_num = "030";

        String time = "000" + count_num;
        Log.d("입력값 :", time);

        long conversionTime = 0;

        String getHour = time.substring(0, 2);
        String getMin = time.substring(2, 4);
        String getSecond = time.substring(4, 6);

        // "00"이 아니고, 첫번째 자리가 0 이면 제거
        if (getHour.substring(0, 1) == "0") {
            getHour = getHour.substring(1, 2);
        }

        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }

        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }

        // 변환시간
        conversionTime = Long.valueOf(getHour) * 1000 * 3600 + Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;

        // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
        // 두번쨰 인자 : 주기( 1000 = 1초)
        new CountDownTimer(conversionTime, 1000) {

            // 특정 시간마다 뷰 변경
            public void onTick(long millisUntilFinished) {

                // 시간단위
                String hour = String.valueOf(millisUntilFinished / (60 * 60 * 1000));

                // 분단위
                long getMin = millisUntilFinished - (millisUntilFinished / (60 * 60 * 1000)) ;
                String min = String.valueOf(getMin / (60 * 1000)); // 몫

                // 초단위
                String second = String.valueOf((getMin % (60 * 1000)) / 1000); // 나머지

                // 밀리세컨드 단위
                String millis = String.valueOf((getMin % (60 * 1000)) % 1000); // 몫

                // 시간이 한자리면 0을 붙인다
                if (hour.length() == 1) {
                    hour = "0" + hour;
                }

                // 분이 한자리면 0을 붙인다
                if (min.length() == 1) {
                    min = "0" + min;
                }

                // 초가 한자리면 0을 붙인다
                if (second.length() == 1) {
                    second = "0" + second;
                }

                count_view.setText(min + ":" + second);
//                hour + ":" + min + ":" + second
//                Log.d("시간변경됨", min + ":" + second);
            }



            // 제한시간 종료시
            public void onFinish() {

                // 변경 후
                count_view.setText("토론 시간 종료");

                // TODO : 타이머가 모두 종료될때 어떤 이벤트를 진행할지

                Intent intent = new Intent(getApplicationContext(), InGame.class);
                startActivity(intent);


            }
        }.start();


        // 타이머 코딩 부분 --------------------------------------------------------------------------
    }

    public void CheckOrder(View view){
        Intent intent = new Intent(getApplicationContext(), InGame.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void PassOrder(View view){
        Intent intent = new Intent(getApplicationContext(), InGame.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    //뒤로가기 막기


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}