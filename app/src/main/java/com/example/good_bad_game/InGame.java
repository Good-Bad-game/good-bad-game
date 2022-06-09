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
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.example.good_bad_game.home.Home;
import com.example.good_bad_game.loginout.LoginService;
import com.example.good_bad_game.ranking.Ranking;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InGame extends AppCompatActivity {
    private static String TAG = "InGameActivity";
    public TextToSpeech tts;
    private String type = "";
    private int player_num;     //6명 초기값 ( 이후 수정할 것 )
    private String room_num;
    private String id;
    private int userList[] = {-1, -1, -1};
    private int targetList[] = {-2, -2, -2};
    private int cnt = 0;
    private String die;
    private int vote_num[] = {0, 0, 0};
    private int max_idx = -1;
    int[] skinId = {R.drawable.skin1, R.drawable.skin2, R.drawable.skin3, R.drawable.skin4, R.drawable.skin5, R.drawable.skin6};

    //투표할 사람을 선택했을 때 번호
    // init은 0으로 초기화
    private int checked_btn = 0;

//      뒤로가기
    private long touchPressedTime = 0;
    private long resetTime = 2000; // 리셋 타임 설정 - 2초

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);
        Intent getintent = getIntent();

        room_num = getintent.getStringExtra("room_num");
        id = getintent.getStringExtra("id");
        die = getintent.getStringExtra("die");
        Toast.makeText(getApplicationContext(),room_num,Toast.LENGTH_SHORT).show();

        player_num = 3;

        ImageView team1 = findViewById(R.id.team1);
        ImageView team2 = findViewById(R.id.team2);
        ImageView team3 = findViewById(R.id.team3);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.180.121.58:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService LoginService = retrofit.create(LoginService.class);

        Call<List<getMatching>> call = LoginService.getMatching();

        call.enqueue(new Callback<List<getMatching>>() {
            @Override
            public void onResponse(Call<List<getMatching>> call, Response<List<getMatching>> response) {
                if(response.isSuccessful()){
                    Log.d("데이터 접근 시작!","");
                    List<getMatching> matchings = response.body();
                    cnt = 0;
                    for( getMatching matching : matchings){
                        Log.d("room_num : ", room_num);
                        Log.d("matchingIDX : ", matching.getMatchIdx());
                        if(matching.getMatchIdx().equals(room_num)){
                            Log.d("if문 발동!","");
                            userList[cnt] = Integer.parseInt(matching.getUserId());
                            cnt++;
                        }
                    }
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<getMatching>> call, Throwable t) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("userList[0] : ", Integer.toString(userList[0]));
                Log.d("userList[1] : ", Integer.toString(userList[1]));
                Log.d("userList[2] : ", Integer.toString(userList[2]));

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


        if( die != null ){

            player_num = 2;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Call<List<Target>> call2 = LoginService.getTarget();

                    call2.enqueue(new Callback<List<Target>>() {
                        @Override
                        public void onResponse(Call<List<Target>> call, Response<List<Target>> response) {
                            if(response.isSuccessful()){
                                List<Target> Targets = response.body();
                                for(Target target : Targets){
                                    for(int i = 0 ; i < 3 ; i++){
                                        if(target.getUid() == userList[i]){
                                            targetList[i] = target.getTarget();
                                        }
                                    }
                                }
                            }
                            else{

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Target>> call, Throwable t) {

                        }
                    });

                }
            }, 500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("targetList[0] : ", Integer.toString(targetList[0]));
                    Log.d("targetList[1] : ", Integer.toString(targetList[1]));
                    Log.d("targetList[2] : ", Integer.toString(targetList[2]));

                    for(int i = 0 ; i < 3 ; i ++){
                        for (int j = 0 ; j < 3 ; j++){
                            if(userList[i] == targetList[j]){
                                vote_num[i]++;
                            }
                        }
                    }

                    int max_num = 0;

                    for(int i = 0 ; i < 3 ; i++){
                        if(vote_num[i] > max_num){
                            max_num = vote_num[i];
                            max_idx = i;
                        }
                    }

                    if(max_idx == 0){
                        team1.setImageResource(R.drawable.bullet);
                    }
                    else if(max_idx == 1){
                        team2.setImageResource(R.drawable.bullet);
                    }
                    else{
                        team3.setImageResource(R.drawable.bullet);
                    }


                }
            }, 1000);


        }



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

//                tts.speak("굿배드를 선택하세요.", TextToSpeech.QUEUE_FLUSH, null);
                tts_speech("굿배드를 선택하세요.");

                Intent intent = new Intent(this, PopupActivity.class);
                startActivityForResult(intent, 1);
            }
        }



        // 타이머 코딩 부분 --------------------------------------------------------------------------

        TextView count_view = findViewById(R.id.time);

        String player_num2 = Integer.toString(player_num);
        String count_num = "030";

        switch(player_num2){
/*            case "6": count_num = "200";
                break;
            case "5": count_num = "140";
                break;
            case "4": count_num = "120";
                break;*/
            case "3": count_num = "020";
                break;
            case "2": count_num = "010";
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
                Log.d("player_num : ", Integer.toString(player_num));

                if(player_num > 2){
                    Intent intent = new Intent(getApplicationContext(), Vote.class);
                    intent.putExtra("room_num", room_num);
                    intent.putExtra("id",id);
                    intent.putExtra("type", type);
                    intent.putExtra("userList",userList);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), final_pick.class);
                    intent.putExtra("room_num", room_num);
                    intent.putExtra("id",id);
                    intent.putExtra("type", type);
                    intent.putExtra("userList",userList);
                    startActivity(intent);
                }

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
        if (System.currentTimeMillis() > touchPressedTime + resetTime ) {
            // 첫번째 터치
            if(checked_btn == 0){
                tts_speech("선택 하세요");
                return;
            }else {
                tts_speech("투표완료 시 다시 클릭");
                Toast.makeText(getApplicationContext(), "다시 클릭", Toast.LENGTH_SHORT).show();
                touchPressedTime = System.currentTimeMillis();
                return;
            }
        }
        // 첫번째 터치후 두번째 터치를 resetTime에 설정된 2초안에 하지 않을시 아래 두번째 터치부분은 실행되지 않음.
        if (System.currentTimeMillis() <= touchPressedTime + resetTime ) {
            // 두번째 터치
            // 동작 수행.
            if (checked_btn == 0) return;

//        tts.speak("회의 종료를 눌렀습니다.", TextToSpeech.QUEUE_FLUSH, null);
            tts_speech("회의 종료");
            Intent intent = new Intent(getApplicationContext(), Vote.class);
            intent.putExtra("playerNum", player_num);
            intent.putExtra("type", type);
            intent.putExtra("room_num", room_num);
            startActivity(intent);
        }
    }

    // 뒤로가기 막기
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    public void team1(View view) {
        if(checked_btn == 1){
            tts_speech("1번 취소");
            checked_btn = 0;
        }else{
        checked_btn = 1;
        tts_speech("1번");
        }
    }

    public void team2(View view) {
        if(checked_btn == 2){
            tts_speech("2번 취소");
            checked_btn = 0;
        }else{
            checked_btn = 2;
            tts_speech("2번");
        }
    }

    public void team3(View view) {
        if(player_num >= 3){
            if(checked_btn == 3){
                tts_speech("3번 취소");
                checked_btn = 0;
            }else{
                checked_btn = 3;
                tts_speech("3번");
            }
        }
    }

    public void team4(View view) {
        if(player_num >= 4){
            if(checked_btn == 4){
                tts_speech("4번 취소");
                checked_btn = 0;
            }else {
                checked_btn = 4;
                tts_speech("4번");
            }
        }
    }

    public void team5(View view) {
        if(player_num >= 5){
            if(checked_btn == 5){
                tts_speech("5번 취소");
                checked_btn = 0;
            }else {
                checked_btn = 5;
                tts_speech("5번");
            }
        }
    }

    public void team6(View view) {
        if(player_num >= 6){
            if(checked_btn == 6){
                tts_speech("6번 취소");
                checked_btn = 0;
            }else{
                checked_btn = 6;
                tts_speech("6번");
            }
        }
    }

    public void tts_speech(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}