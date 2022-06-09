package com.example.good_bad_game;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.good_bad_game.loginout.Login;
import com.example.good_bad_game.loginout.LoginService;
import com.example.good_bad_game.ranking.Ranking;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Vote extends AppCompatActivity {
    private static String TAG = "VoteActivity";
    private TextToSpeech tts;
    private String type = "";
    private int player_num = 6;
    private String room_num;
    private String id;
    private int choice = -1;
    private String vote_confirm = "false";
    private int userList[];
    int[] skinId = {R.drawable.skin1, R.drawable.skin2, R.drawable.skin3, R.drawable.skin4, R.drawable.skin5, R.drawable.skin6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        Intent intent = getIntent();

        ImageView team1 = findViewById(R.id.team1);
        ImageView team2 = findViewById(R.id.team2);
        ImageView team3 = findViewById(R.id.team3);

        TextView pass = findViewById(R.id.end_number);
        TextView confirm = findViewById(R.id.end_meeting);

        room_num = intent.getStringExtra("room_num");
        id = intent.getStringExtra("id");
        userList = intent.getIntArrayExtra("userList");

        Toast.makeText(getApplicationContext(),room_num,Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.d("userList[0] : ", Integer.toString(userList[0]));
                Log.d("userList[1] : ", Integer.toString(userList[1]));
                Log.d("userList[2] : ", Integer.toString(userList[2]));

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://54.180.121.58:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                LoginService LoginService = retrofit.create(LoginService.class);

                Call<List<Ranking>> call3 = LoginService.Ranking();

                call3.enqueue(new Callback<List<Ranking>>() {
                    @Override
                    public void onResponse(Call<List<Ranking>> call, Response<List<Ranking>> response) {
                        if(response.isSuccessful()){
                            Log.d("Raking 가져오기 성공!","");
                            List<Ranking> rankings = response.body();
                            for( Ranking ranking : rankings){
                                if(ranking.getUid() == userList[0]){
                                    team1.setImageResource(skinId[ranking.getSid()]);
                                }
                                else if(ranking.getUid() == userList[1]){
                                    team2.setImageResource(skinId[ranking.getSid()]);
                                }
                                else if(ranking.getUid() == userList[2]){
                                    team3.setImageResource(skinId[ranking.getSid()]);
                                }
                            }
                        }
                        else{

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Ranking>> call, Throwable t) {

                    }
                });

            }
        }, 500);


//      tts 객체 생성
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
//        tts.speak("투표를 시작합니다.", TextToSpeech.QUEUE_FLUSH, null);
        tts_speech("투표시작");


        // 타이머 코딩 부분 --------------------------------------------------------------------------

        if(!TextUtils.isEmpty(getIntent().getStringExtra("type"))){
            type = intent.getStringExtra("type");
            Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "did'n find type", Toast.LENGTH_SHORT).show();
        }

        TextView count_view = findViewById(R.id.time);

        String count_num = "010";

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
                    tts_speech("1분 남았습니다.");
                }
                else if(min.equals("00")){
                    if(second.equals("30")){
//                        tts.speak("30초남았습니다.", TextToSpeech.QUEUE_FLUSH, null);
                        tts_speech("30초 남았습니다.");
                    }
                    else if(second.equals("10")){
//                        tts.speak("10초 남았습니다.", TextToSpeech.QUEUE_FLUSH, null);
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
//                tts.speak("투표가 종료돠었습니다.", TextToSpeech.QUEUE_FLUSH, null);
                tts_speech("투표 종료");

                // TODO : 타이머가 모두 종료될때 어떤 이벤트를 진행할지

                Intent intent = new Intent(getApplicationContext(), InGame.class);
                intent.putExtra("id", id);
                intent.putExtra("room_num", room_num);
                intent.putExtra("die","true");
                intent.putExtra("userList",userList);
                startActivity(intent);


            }
        }.start();

        team1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vote_confirm == "false"){
                    choice = 0;
                    team1.setBackgroundResource(R.drawable.vote_select);
                    team2.setBackgroundResource(R.drawable.iamge);
                    team3.setBackgroundResource(R.drawable.iamge);
                }
                else{
                    Toast.makeText(getApplicationContext(),"이미 투표권을 행사했습니다",Toast.LENGTH_SHORT).show();
                }

            }
        });

        team2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vote_confirm == "false"){
                    choice = 1;
                    team1.setBackgroundResource(R.drawable.iamge);
                    team2.setBackgroundResource(R.drawable.vote_select);
                    team3.setBackgroundResource(R.drawable.iamge);
                }
                else{
                    Toast.makeText(getApplicationContext(),"이미 투표권을 행사했습니다",Toast.LENGTH_SHORT).show();
                }

            }
        });

        team3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vote_confirm == "false"){
                    choice = 2;
                    team1.setBackgroundResource(R.drawable.iamge);
                    team2.setBackgroundResource(R.drawable.iamge);
                    team3.setBackgroundResource(R.drawable.vote_select);
                }
                else{
                    Toast.makeText(getApplicationContext(),"이미 투표권을 행사했습니다",Toast.LENGTH_SHORT).show();
                }

            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vote_confirm == "false"){
                    vote_confirm = "true";
                    team1.setBackgroundResource(R.drawable.iamge);
                    team2.setBackgroundResource(R.drawable.iamge);
                    team3.setBackgroundResource(R.drawable.iamge);
                    Toast.makeText(getApplicationContext(),"기권하였습니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"이미 투표권을 행사했습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vote_confirm == "false"){
                    if(choice == -1){
                        Toast.makeText(getApplicationContext(),"투표할 대상자를 클릭하세요",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        vote_confirm = "true";
                        Toast.makeText(getApplicationContext(),choice + " 에게 투표하였습니다.",Toast.LENGTH_SHORT).show();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://54.180.121.58:8080/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        LoginService LoginService = retrofit.create(LoginService.class);

                        Target target = new Target(Integer.parseInt(id), userList[choice]);

                        LoginService.putTarget(Integer.parseInt(id), target).enqueue(new Callback<Target>() {
                            @Override
                            public void onResponse(Call<Target> call, Response<Target> response) {
                                if(response.isSuccessful()){
                                    Log.d("putTarget 성공!","");
                                }
                                else{

                                }
                            }

                            @Override
                            public void onFailure(Call<Target> call, Throwable t) {

                            }
                        });

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"이미 투표권을 행사했습니다",Toast.LENGTH_SHORT).show();
                }

            }
        });


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

    public void tts_speech(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}