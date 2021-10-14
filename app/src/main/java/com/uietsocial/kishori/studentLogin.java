package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class studentLogin extends AppCompatActivity {
    EditText memail,mpassword;
    Button mlogin;
    FirebaseAuth auth,vrauth;
    CardView cardView;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        database=FirebaseDatabase.getInstance();

        memail=findViewById(R.id.semail);
        mpassword=findViewById(R.id.spassword);
        cardView=findViewById(R.id.tran2);
        cardView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
        auth=FirebaseAuth.getInstance();
        vrauth=FirebaseAuth.getInstance();
        mlogin=findViewById(R.id.slogin);
        String usercat=getIntent().getStringExtra("UserCat");
        DatabaseReference reference=database.getReference().child("user").child("Student");
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email=memail.getText().toString();
                password=mpassword.getText().toString();
                if(password.isEmpty()  || email.isEmpty()) {

                    Toast.makeText(studentLogin.this,"Please fill all the fields properly",Toast.LENGTH_SHORT).show();
                    return ;

                }

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid=vrauth.getCurrentUser().getUid();

                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange( DataSnapshot snapshot) {
                                    int f=0;
                                    for(DataSnapshot s:snapshot.getChildren())
                                    {
                                        if(s.getKey().equals(uid))
                                        {
                                           f=1;
                                        }
                                    }
                                    if(f==1)
                                    {

                                        Intent intent=new Intent(studentLogin.this, Home.class);
                                        intent.putExtra("usercat","Student");
                                        startActivity(intent);

                                    }
                                    else
                                    {
                                        Intent intent=new Intent(studentLogin.this, FacultyHome.class);
                                        intent.putExtra("usercat","Faculty");
                                        startActivity(intent);

                                    }
                                }

                                @Override
                                public void onCancelled( DatabaseError error) {

                                }
                            });

                        } else {
                            Toast.makeText(studentLogin.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}