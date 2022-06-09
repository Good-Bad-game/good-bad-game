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
import androidx.databinding.DataBindingUtil;

import com.example.good_bad_game.home.Home;
import com.example.good_bad_game.databinding.ActivityInGameBinding;
import com.example.good_bad_game.loginout.LoginService;
import com.example.good_bad_game.ranking.Ranking;
import com.remotemonster.sdk.Config;
import com.remotemonster.sdk.RemonConference;
import com.remotemonster.sdk.RemonException;

import java.util.List;
import org.webrtc.SurfaceViewRenderer;

import java.util.Arrays;
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

    // 음성 통신 관련 코드
    ActivityInGameBinding binding;
    private boolean[] availableView;
    RemonConference remonConference;

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


        binding = DataBindingUtil.setContentView(this, R.layout.activity_in_game);

        SurfaceViewRenderer[] surfaceRendererArray = {
                binding.surfRendererRemote1,
                binding.surfRendererRemote2,
                binding.surfRendererRemote3,
                binding.surfRendererRemote4,
                binding.surfRendererRemote5,
                binding.surfRendererRemote6
        };


//... // 비어있는 View를 처리하기 위한 배열로, 각 서비스에 따라 구현 필요
        availableView = new boolean[surfaceRendererArray.length];
        Arrays.fill(availableView, false);


//        RemonConference 클래스를 생성하고, 자신의 영상을 송출하기 위한 설정을 합니다.
        remonConference = new RemonConference();

        Config config = new Config();
        config.context = this;
        config.serviceId = "a9553294-aa66-45de-8b07-6b1b922ef105";
        config.key = "e3d66ede5091133201de590d62275a55b93ce6b0d5dc0322cf0360c10329eb71";


        String num_name = room_num + "Room";
        Log.d(TAG, room_num);

//        remonConference.create(num_name, config, participant -> {
//            // 자신의 View를 초기화
////             얼굴을 보이게 한다
//            participant.setLocalView(null);
//            Log.d(TAG, num_name);
//
//            // View 설정
//            availableView[0] = true; // boolean : 할당된 미디어가 있는지 여부
//        }).close(() -> {
//            // 클라이언트의 사용자(참여자)가 연결된 채널이 종료되면 호출됨
//            // 송출이 중단되면 그룹 통화에서 연결이 끊어진 것이며, 다른 사용자와의 연결도 모두 끊어짐
//        }).error(e -> {
//            // 클라이언트의 사용자(참여자)가 연결된 채널에서 오류 발생 시 호출됨
//            // 오류로 연결이 종료되면 error -> close 순으로 호출됨
//            Log.d(TAG, "error!!!");
//        });


        // 만들어진 방에 들어가는 건가?
        remonConference.create(num_name, config, (participant) -> {

//            participant.setLocalView(surfaceRendererArray[0]);
//            room_name = "1";
            Log.d(TAG, num_name);

        }).on("onRoomCreated", (participant) -> {


            // 방에 입장 이후에 호출, 자신의 미디어 송출 시작
            // TODO: 실제 사용자 정보는 각 서비스에서 관리하므로, 서비스에서 채널과 실제 사용자 매핑 작업 진행

            // tag 객체에 holder 패턴 형태로 객체를 지정하여 사용
            // 예제에서는 View 설정을 위해 단순히 View의 index를 저장함
            participant.tag = 0;

        }).on("onUserJoined", (participant) -> {

            Log.d(TAG, "Joined new user");
            // 그룹 통화에 새로운 참여자가 입장했을 때 호출됨
            // 다른 사용자가 입장한 경우 초기화를 위해 호출됨
            // TODO: 실제 사용자 매핑 : it.id 값으로 연결된 실제 사용자와 매핑


            // 뷰(View) 설정
            int index = getAvailableView();
            if (index > 0) {
                participant.getConfig().localView = null;
                participant.getConfig().remoteView = surfaceRendererArray[index];
                participant.tag = index;
            }

            // 해당 참여자가 연결이 완되었을 때 처리할 작업이 있는 경우
            participant.on("onComplete", (participant2) -> {
                // updateView()
            });
        }).on("onUserStreamConnected", (participant) -> {

            // 새로운 참여자의 영상을 성공적으로 수신하기 시작하면 호출

        }).on("onUserLeft", (participant) -> {
            // 참여자가 그룹 통화에서 퇴장하거나 연결이 종료된 경우 호출됨
            // id와 tag를 참조하여 어떤 사용자가 퇴장했는지 확인 후 퇴장 처리를 진행
            int index = (Integer) participant.tag;
            availableView[index] = false;
        }).close(() -> {
            // 그룹 통화 종료 시 호출됨
        }).error((RemonException error) -> {
            // 오류 발생시 호출됨
            Log.d(TAG, "error2!!!");
        });


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
        Timer();

    }


    private void Timer(){

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
                remonConference.leave();

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

    public void tts_speech(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    // 비어있는 View는 아래처럼 얻어올 수 있음
    // 서비스에 해당하는 부분이므로 각 서비스 UI에 맞게 구성 필요
    private int getAvailableView() {
        for (int i = 0; i < availableView.length; i++) {
            if (!availableView[i]) {
                availableView[i] = true;
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        remonConference.leave();
        Log.d(TAG, "destroy");
        super.onDestroy();
    }
}