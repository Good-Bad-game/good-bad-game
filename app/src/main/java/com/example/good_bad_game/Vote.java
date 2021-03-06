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
                            Log.d("Raking ???????????? ??????!","");
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


//      tts ?????? ??????
        tts = new TextToSpeech(Vote.this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.KOREA);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        Toast.makeText(Vote.this, "???????????? ?????? ???????????????.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//        tts.speak("????????? ???????????????.", TextToSpeech.QUEUE_FLUSH, null);
        tts_speech("????????????");


        // ????????? ?????? ?????? --------------------------------------------------------------------------

        if(!TextUtils.isEmpty(getIntent().getStringExtra("type"))){
            type = intent.getStringExtra("type");
            Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "did'n find type", Toast.LENGTH_SHORT).show();
        }

        TextView count_view = findViewById(R.id.time);

        String count_num = "010";

        String time = "000" + count_num;
        Log.d("????????? :", time);

        long conversionTime = 0;

        String getHour = time.substring(0, 2);
        String getMin = time.substring(2, 4);
        String getSecond = time.substring(4, 6);

        // "00"??? ?????????, ????????? ????????? 0 ?????? ??????
        if (getHour.substring(0, 1) == "0") {
            getHour = getHour.substring(1, 2);
        }

        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }

        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }

        // ????????????
        conversionTime = Long.valueOf(getHour) * 1000 * 3600 + Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;

        // ????????? ?????? : ????????? ?????? (???????????? 30?????? 30 x 1000(??????))
        // ????????? ?????? : ??????( 1000 = 1???)
        new CountDownTimer(conversionTime, 1000) {

            // ?????? ???????????? ??? ??????
            public void onTick(long millisUntilFinished) {

                // ????????????
                String hour = String.valueOf(millisUntilFinished / (60 * 60 * 1000));

                // ?????????
                long getMin = millisUntilFinished - (millisUntilFinished / (60 * 60 * 1000)) ;
                String min = String.valueOf(getMin / (60 * 1000)); // ???

                // ?????????
                String second = String.valueOf((getMin % (60 * 1000)) / 1000); // ?????????

                // ??????????????? ??????
                String millis = String.valueOf((getMin % (60 * 1000)) % 1000); // ???

                // ????????? ???????????? 0??? ?????????
                if (hour.length() == 1) {
                    hour = "0" + hour;
                }

                // ?????? ???????????? 0??? ?????????
                if (min.length() == 1) {
                    min = "0" + min;
                }

                // ?????? ???????????? 0??? ?????????
                if (second.length() == 1) {
                    second = "0" + second;
                }
                if(min.equals("00") && second.equals("59")){
//                    tts.speak("1??????????????????.", TextToSpeech.QUEUE_FLUSH, null);
                    tts_speech("1??? ???????????????.");
                }
                else if(min.equals("00")){
                    if(second.equals("30")){
//                        tts.speak("30??????????????????.", TextToSpeech.QUEUE_FLUSH, null);
                        tts_speech("30??? ???????????????.");
                    }
                    else if(second.equals("10")){
//                        tts.speak("10??? ???????????????.", TextToSpeech.QUEUE_FLUSH, null);
                        tts_speech("10??? ???????????????.");
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
//                Log.d("???????????????", min + ":" + second);
            }



            // ???????????? ?????????
            public void onFinish() {

                // ?????? ???
                count_view.setText("?????? ?????? ??????");
//                tts.speak("????????? ?????????????????????.", TextToSpeech.QUEUE_FLUSH, null);
                tts_speech("?????? ??????");

                // TODO : ???????????? ?????? ???????????? ?????? ???????????? ????????????

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
                    Toast.makeText(getApplicationContext(),"?????? ???????????? ??????????????????",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(),"?????? ???????????? ??????????????????",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(),"?????? ???????????? ??????????????????",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(),"?????????????????????.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"?????? ???????????? ??????????????????",Toast.LENGTH_SHORT).show();
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vote_confirm == "false"){
                    if(choice == -1){
                        Toast.makeText(getApplicationContext(),"????????? ???????????? ???????????????",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        vote_confirm = "true";
                        Toast.makeText(getApplicationContext(),choice + " ?????? ?????????????????????.",Toast.LENGTH_SHORT).show();

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
                                    Log.d("putTarget ??????!","");
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
                    Toast.makeText(getApplicationContext(),"?????? ???????????? ??????????????????",Toast.LENGTH_SHORT).show();
                }

            }
        });


        // ????????? ?????? ?????? --------------------------------------------------------------------------
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

    //???????????? ??????
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    public void tts_speech(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}