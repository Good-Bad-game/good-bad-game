package com.example.good_bad_game;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class InGame extends AppCompatActivity {
    private static String TAG = "InGameActivity";
    public TextToSpeech tts;
    private String type = "";
    private int player_num;     //6명 초기값 ( 이후 수정할 것 )

    //투표할 사람을 선택했을 때 번호
    // init은 0으로 초기화
    private int checked_btn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        Intent getintent = getIntent();



//      tts 객체 생성
        tts = new TextToSpeech(InGame.this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.KOREA);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        Toast.makeText(InGame.this, "지원하지 않는 언어입니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 게임 시작

        // GOOD BAD CHOICE ------------------------------------------------------------------------

        if(!TextUtils.isEmpty(getintent.getStringExtra("type"))){
            type = getintent.getStringExtra("type");

            if(type.equals("firstIn")){
                player_num = 6;  //6명 초기값 ( 이후 수정할 것 )

//                tts.speak("굿배드를 선택하세요.", TextToSpeech.QUEUE_FLUSH, null);
                tts_speech("굿배드를 선택하세요.");

                Intent intent = new Intent(this, PopupActivity.class);
                startActivityForResult(intent, 1);
            }
        }



        // 타이머 코딩 부분 --------------------------------------------------------------------------

        TextView count_view = findViewById(R.id.time);

        String player_num2 = Integer.toString(player_num);
        String count_num = "040";

        switch(player_num2){
            case "6": count_num = "200";
                break;
            case "5": count_num = "140";
                break;
            case "4": count_num = "120";
                break;
            case "3": count_num = "100";
                break;
            case "2": count_num = "040";
                break;
        }

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

                if(min.equals("00") && second.equals("59")){
//                    tts.speak("1분남았습니다.", TextToSpeech.QUEUE_FLUSH, null);
                    tts_speech("1분남았습니다.");
                }
                else if(min.equals("00")){
                    if(second.equals("30")){
//                        tts.speak("30초 남았습니다.", TextToSpeech.QUEUE_FLUSH, null);
                        tts_speech("30초 남았습니다.");
                    }
                    else if(second.equals("10")){
//                        tts.speak("10초남았습니다.", TextToSpeech.QUEUE_FLUSH, null);
                        tts_speech("10초 남았습니다.");
                    }
                    else if(second.equals("05")){
//                        tts.speak("5", TextToSpeech.QUEUE_FLUSH, null);
                        tts_speech("5");
                    }
                    else if(second.equals("04")){
//                        tts.speak("4", TextToSpeech.QUEUE_FLUSH, null);
                        tts_speech("4");
                    }
                    else if(second.equals("03")){
//                        tts.speak("3", TextToSpeech.QUEUE_FLUSH, null);
                        tts_speech("3");
                    }
                    else if(second.equals("02")){
//                        tts.speak("2", TextToSpeech.QUEUE_FLUSH, null);
                        tts_speech("2");
                    }
                    else if(second.equals("01")){
//                        tts.speak("1", TextToSpeech.QUEUE_FLUSH, null);
                        tts_speech("1");
                    }
                }

                count_view.setText(min + ":" + second);
//                hour + ":" + min + ":" + second
//                Log.d("시간변경됨", min + ":" + second);
            }



            // 제한시간 종료시
            public void onFinish() {

                // 변경 후
                count_view.setText("토론 시간 종료");
//                tts.speak("토론이 종료되었습니다.", TextToSpeech.QUEUE_FLUSH, null);
                tts_speech("토론 종료");

                // TODO : 타이머가 모두 종료될때 어떤 이벤트를 진행할지
                Intent intent = new Intent(getApplicationContext(), Vote.class);
                intent.putExtra("type", type);
                startActivity(intent);


            }
        }.start();


    }

    // Good-Bad 선택 후에 Good인지 Bad인지 데이터를 받아오는 곳

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
               type = data.getStringExtra("type");
            }
        }
    }

    // 투표하기 클릭시 바로 이동

    public void StrightVote(View view){
//        tts.speak("회의 종료를 눌렀습니다.", TextToSpeech.QUEUE_FLUSH, null);
        tts_speech("회의 종료");
        Intent intent = new Intent(getApplicationContext(), Vote.class);
        intent.putExtra("playerNum", player_num);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    public void team1(View view) {
        if(checked_btn == 1){
            tts_speech("1번 취소");
            checked_btn = 0;
        }
        checked_btn = 1;
        tts_speech("1번");
    }

    public void team2(View view) {
        if(checked_btn == 2){
            tts_speech("2번 취소");
            checked_btn = 0;
        }
        checked_btn = 2;
        tts_speech("2번");
    }

    public void team3(View view) {
        if(checked_btn == 3){
            tts_speech("3번 취소");
            checked_btn = 0;
        }
        if(player_num >= 3){
            checked_btn = 3;
            tts_speech("3번");
        }
    }

    public void team4(View view) {
        if(checked_btn == 4){
            tts_speech("4번 취소");
            checked_btn = 0;
        }
        if(player_num >= 4){
            checked_btn = 4;
            tts_speech("4번");
        }
    }

    public void team5(View view) {
        if(checked_btn == 5){
            tts_speech("5번 취소");
            checked_btn = 0;
        }
        if(player_num >= 5){
            checked_btn = 5;
            tts_speech("5번");
        }
    }

    public void team6(View view) {
        if(checked_btn == 6){
            tts_speech("6번 취소");
            checked_btn = 0;
        }
        if(player_num >= 6){
            checked_btn = 6;
            tts_speech("6번");
        }
    }

    public void tts_speech(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}