package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPanel extends AppCompatActivity {

    EditText memail,mpassword;
    FirebaseAuth auth;
    Button mlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        auth = FirebaseAuth.getInstance();
        memail = findViewById(R.id.ademail);
        mpassword = findViewById(R.id.adpassword);
        mlogin = findViewById(R.id.adlogin);

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email=memail.getText().toString();
                 password=mpassword.getText().toString();
                 if(password.isEmpty()  || email.isEmpty()) {

                   Toast.makeText(AdminPanel.this,"Please fill all the fields properly",Toast.LENGTH_SHORT).show();
                   return ;

                 }


                     auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {
                                 startActivity(new Intent(AdminPanel.this, CreateUsers.class));
                                 finish();
                             } else {
                                 Toast.makeText(AdminPanel.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                     });



            }
        });
    }
}