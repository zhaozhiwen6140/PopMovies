package com.example.test.popmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;



/**
 * Created by Administrator on 2015/12/22.
 */
public class SplashActivity extends AppCompatActivity {
    private static final int TIME=5000;
    private static final int GO_MAIN=100;
    private static final int GO_GUIDE=101;

    Handler mhandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_MAIN:
                    goMain();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();

    }

    private void init() {
        SharedPreferences sf=getSharedPreferences("data", MODE_PRIVATE);
        boolean isFirstIn=sf.getBoolean("isFirstIn", true);
        SharedPreferences.Editor editor=sf.edit();
        if(isFirstIn){
            editor.putBoolean("isFirstIn", false);
            mhandler.sendEmptyMessageDelayed(GO_GUIDE,TIME);

        }else{
            mhandler.sendEmptyMessageDelayed(GO_MAIN,TIME);
        }
        editor.commit();

    }

    private void goMain() {
        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
    private void goGuide() {
        Intent intent=new Intent(SplashActivity.this,GuideActivity.class);
        startActivity(intent);
        finish();
    }


}
