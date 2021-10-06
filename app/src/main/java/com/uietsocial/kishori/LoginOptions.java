package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.Instant;

public class LoginOptions extends AppCompatActivity {
    Button mFaculty,mStudent;
    FirebaseAuth.AuthStateListener firebaseAuthStaticListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        mStudent=findViewById(R.id.studentlogin);
        mFaculty=findViewById(R.id.facultlogin);
        mStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =new Intent(LoginOptions.this,studentLogin.class);
               intent.putExtra("UserCat","Student");
               startActivity(intent);
            }
        });
        mFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginOptions.this,facultyLogin.class);
                intent.putExtra("UserCat","Faculty");
                startActivity(intent);
            }
        });
    }
}