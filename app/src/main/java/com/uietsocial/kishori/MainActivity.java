package com.uietsocial.kishori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.time.Instant;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mclaout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mclaout=findViewById(R.id.splash);
        final int splashTimeOut = 200;

        Thread splashThread = new Thread(){
            int wait = 0;
            @Override
            public void run() {
                try {
                    super.run();
                    while(wait < splashTimeOut){
                        sleep(200);
                        wait += 10;
                    }
                } catch (Exception e) {
                }finally{

                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    Intent intent =new Intent(MainActivity.this,studentLogin.class);

                    startActivity(intent);
                    finish();
                }
            }
        };
        splashThread.start();
    }
}