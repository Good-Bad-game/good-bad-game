package com.example.good_bad_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class PopupActivity extends Activity {

    TextView txtText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.popup_activity);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.popup_activity, null);

        builder.setView(dialoglayout);
        builder.show();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    public void UpOrder(View view){
        Toast.makeText(getApplicationContext(), "up이 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("type","up");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void DownOrder(View view){
        Toast.makeText(getApplicationContext(), "Down이 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("type","down");
        setResult(RESULT_OK, intent);
        finish();
    }


}

