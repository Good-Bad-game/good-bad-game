package com.example.good_bad_game;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.good_bad_game.databinding.ActivityReadyGameBinding;
import com.example.good_bad_game.loginout.LoginService;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import org.webrtc.SurfaceViewRenderer;

public class ReadyGame extends AppCompatActivity {

    ActivityReadyGameBinding binding;

    private String id;
    private String v_type;
    private String room_num;
    private int num = 0;
    private int ready_num = 0;
    private static Thread t, t1;

    int imgId[] = {
            R.id.team1, R.id.team2, R.id.team3,
            R.id.team4, R.id.team5, R.id.team6,
    };

    int[] skinId = {R.drawable.skin1, R.drawable.skin2, R.drawable.skin3,
            R.drawable.skin4, R.drawable.skin5, R.drawable.skin6};

    List<String> userList = Collections.synchronizedList(new ArrayList<String>());
    ImageView img;

    public int userList2[] = {-1, -1, -1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ready_game);
        Button btn_start = findViewById(R.id.btn_start);

        Intent receive_intent = getIntent();
        id = receive_intent.getStringExtra("id");
        v_type = receive_intent.getStringExtra("v_type"); // 방문 타입
        room_num = receive_intent.getStringExtra("room_num");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.180.121.58:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService LoginService = retrofit.create(LoginService.class);

        if (v_type.equals("creator")){

            Log.d("v_type : ","감지 후 if문 실행됨.");
            Log.d("ID : ", id);
            Log.d("Room_num : ", room_num);

            Matching matching = new Matching(null, id, room_num);
            Call<Matching> call = LoginService.Matching(matching);

            call.enqueue(new Callback<Matching>() {
                @Override
                public void onResponse(Call<Matching> call, Response<Matching> response) {
                    if (!response.isSuccessful()){
                        Log.d("Error Code1 : ", String.valueOf(response.code()));
                        return;
                    }
                    Log.d("Success Code : ", "Post 성공1");
                }
                @Override
                public void onFailure(Call<Matching> call, Throwable t) {
                    Log.d("Error Code2",t.getMessage());
                }
            });

        }


        t = new Thread(){
            @Override
            public void run() {

                while(!isInterrupted()){
                    try {
                        Thread.sleep(3000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Call<List<getMatching>> calle = LoginService.getMatching();

                                calle.enqueue(new Callback<List<getMatching>>() {
                                    @Override
                                    public void onResponse(Call<List<getMatching>> calle, Response<List<getMatching>> response) {
                                        if (!response.isSuccessful())
                                        {
                                            Log.d("onResponse 발동","Connection은 성공하였으나 code 에러 발생");
                                            return;
                                        }
                                        List<getMatching> Match = response.body();

                                        for ( getMatching items : Match)
                                        {
                                            if(items.getMatchIdx().equals(room_num)&& !userList.contains(items.getUserId())){
                                                userList.add(items.getUserId());

                                                Call<List<getItem>> call = LoginService.getItems();

                                                call.clone().enqueue(new Callback<List<getItem>>() {
                                                    @Override
                                                    public void onResponse(retrofit2.Call<List<getItem>> call, Response<List<getItem>> response) {
                                                        if (!response.isSuccessful())
                                                        {
                                                            Log.d("onResponse 발동","Connection은 성공하였으나 code 에러 발생");
                                                            return;
                                                        }
                                                        List<getItem> Items = response.body();
                                                        int i = 0;
/*                                                        Log.d("userList1 : ", userList.toString());*/

                                                        for ( getItem item : Items)
                                                        {
                                                            if(userList.contains(item.getUserid())){
                                                                img = (ImageView)findViewById(imgId[i]);
                                                                img.setImageResource(skinId[(Integer.parseInt(item.getShopid()))]);
                                                                i++;
                                                            }
                                                        }
                                                        userList.clear();
                                                    }
                                                    @Override
                                                    public void onFailure(retrofit2.Call<List<getItem>> call, Throwable t) {
                                                        Log.d("onFailure 발동","Connection Error");
                                                        return;
                                                    }
                                                });


                                            }

                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<List<getMatching>> calle, Throwable t) {
                                        Log.d("onFailure 발동","Connection Error");
                                        return;
                                    }
                                });


                            }
                        });
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        };

        //Toast.makeText(getApplicationContext(),"방 번호 : " + room_num,Toast.LENGTH_SHORT).show();
        Log.d("v_type : ", v_type);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // 6명 정원찰 때 시작할 것.
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                intent.putExtra("userList", userList2);
                intent.putExtra("room_num", room_num);
                intent.putExtra("id", id);
                intent.putExtra("type", "firstIn");
                intent.putExtra("room_num",room_num);
                startActivity(intent);
                t.interrupt();


            }
        });

        t.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.interrupt();
        //t1.interrupt();
    }

    @Override
    public void onBackPressed() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.180.121.58:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService LoginService = retrofit.create(LoginService.class);
        LoginService.deleteMatching(Integer.parseInt(id)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.d("Matching 삭제 성공!", "Matching 삭제 성공!");

                    Call<List<getMatching>> call2 = LoginService.getMatching();

                    call2.enqueue(new Callback<List<getMatching>>() {
                        @Override
                        public void onResponse(retrofit2.Call<List<getMatching>> call, Response<List<getMatching>> response) {
                            if (!response.isSuccessful())
                            {
                                Log.d("onResponse 발동","Connection은 성공하였으나 code 에러 발생");
                                return;
                            }
                            List<getMatching> Matching_infos = response.body();

                            t1 = new Thread() {
                                @Override
                                public void run() {

                                    while (!isInterrupted()) {
                                        try {
                                            Thread.sleep(3000);

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    for( getMatching items : Matching_infos)
                                                    {
                                                        userList.remove(items.getUserId());
                                                    }
                                                    Log.d("userListRemove : ", userList.toString());

                                                }
                                            });
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            };
                            t1.start();

                            for ( getMatching matching_info : Matching_infos) {
                                if ( matching_info.getMatchIdx().equals(room_num)){
                                    num = num + 1;
                                }
                            }
                            Log.d("num : ", Integer.toString(num));

                            if (num == 0){
                                Log.d("room_num : ", room_num);

                                LoginService.deleteRoom(Integer.parseInt(room_num)).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) Log.d("방 삭제 성공!", "방 삭제 성공!");
                                        else Log.d("방 삭제 실패!", "방 삭제 실패!");
                                    }
                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {}
                                });
                            }
                            else finish();

                            Log.d("interrupt ","interrupt 발생");

                        }

                        @Override
                        public void onFailure(retrofit2.Call<List<getMatching>> call, Throwable t) {
                            Log.d("onFailure 발동","Connection 실패");
                        }
                    });

                }
                else Log.d("Matching 삭제 실패!", "Matching 삭제 실패!");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        finish();

    }



}