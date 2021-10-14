package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import papaya.in.sendmail.SendMail;

public class CreateUsers extends AppCompatActivity {
    Button mregistration,mlogout;
    EditText memail,mname,mpassword,mroll;
    FirebaseAuth auth;
    RadioGroup mRadiogroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_users);
        auth=FirebaseAuth.getInstance();
        mRadiogroup=findViewById(R.id.radiogroup);
        memail=findViewById(R.id.remail);
        mname=findViewById(R.id.rname);
        mpassword=findViewById(R.id.rpassward);
        mroll=findViewById(R.id.rollid);
        mregistration=findViewById(R.id.register);
        mlogout=findViewById(R.id.adLogout);
        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    auth.signOut();
                    startActivity(new Intent(CreateUsers.this, AdminPanel.class));
                    finish();

            }
        });
        mregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= memail.getText().toString();
                String name=mname.getText().toString();
                String password=mpassword.getText().toString();
                String roll= mroll.getText().toString();


                int selectId= mRadiogroup.getCheckedRadioButtonId();

                final RadioButton radioButton=findViewById(selectId);
                if(selectId==-1)
                {
                    Toast.makeText(CreateUsers.this,"Registration Failed!! Please fill all the fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(email.isEmpty() || name.isEmpty()|| password.isEmpty()|| roll.isEmpty())
                {
                    Toast.makeText(CreateUsers.this,"Registration Failed!! Please fill all the fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(CreateUsers.this,"Registration Failed!! Please try after some time",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String userId=auth.getCurrentUser().getUid();
                            DatabaseReference currentuserDb= FirebaseDatabase.getInstance().getReference().child("user").child(radioButton.getText().toString()).child(userId);

                            Map userinfo=new HashMap();
                            userinfo.put("name",name);
                            userinfo.put("id",roll);
                            userinfo.put("profileImageUrl","default");
                            userinfo.put("email",email);
                            userinfo.put("status","Offline");
                            currentuserDb.updateChildren(userinfo);
                            Toast.makeText(CreateUsers.this,"Registration Successful !",Toast.LENGTH_SHORT).show();


                            SendMail mail = new SendMail("csjmuprojectkishori@gmail.com", "Kishori@123",
                                    memail.getText().toString(),
                                    "Login password to Kishori app",
                                    "Welcome to Kishori app .Now you can login into the app using valid email and given password :- "+mpassword.getText().toString()+ "." +System.getProperty("line.separator")+
                                            "You should not share your password with anyone, including any students, faculty or staff. " +
                                            " In situations where someone requires access to another individual’s protected resources, " +
                                            "delegation of permission options should be explored ."+
                                            " In case you lost your password contact our technical team "+System.getProperty("line.separator")+
                                            System.getProperty("line.separator")+
                                            System.getProperty("line.separator")+" Thank you"+System.getProperty("line.separator")+" Admin Kishori Team");
                            mail.execute();
                            memail.setText("");
                            mname.setText("");
                            mroll.setText("");
                            mpassword.setText("");
                        }

                    }
                });

            }
        });

    }
}