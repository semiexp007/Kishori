package com.uietsocial.kishori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class s2 extends AppCompatActivity {
 LinearLayout mclaout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2);
        //final int splashTimeOut = 200;

        mclaout=findViewById(R.id.splas);
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
                    Intent intent =new Intent(s2.this,studentLogin.class);

                    startActivity(intent);
                    finish();
                }
            }
        };
        splashThread.start();


                   // startActivity(intent);
                  //  finish();

    }
}